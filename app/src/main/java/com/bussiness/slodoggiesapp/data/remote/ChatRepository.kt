package com.bussiness.slodoggiesapp.data.remote


import android.net.Uri
import android.util.Log
import com.bussiness.slodoggiesapp.data.model.common.ChatMessage
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChatRepository(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {


//  old code for the chat ...
//    fun sendMessage(chatId: String, message: ChatMessage) {
//
//    if (chatId.isBlank()) {
//        Log.e("ChatRepository", "chatId is empty. Message not sent.")
//        return
//    }
//
//    firestore
//        .collection("chats")
//        .document(chatId)
//        .collection("messages")
//        .add(message) // 🔥 auto document id
//        .addOnSuccessListener {
//            Log.d("ChatRepository", "Message sent successfully")
//        }
//        .addOnFailureListener { e ->
//            Log.e("ChatRepository", "Failed to send message", e)
//        }
//}
//
//    fun observeMessages(chatId: String,currentUserId:String): Flow<List<ChatMessage>> = callbackFlow {
//
//        if (chatId.isBlank()) {
//            trySend(emptyList())
//            close()
//            return@callbackFlow
//        }
//
//        val subscription = firestore.collection("chats")
//            .document(chatId)
//            .collection("messages")
//            .orderBy("timestamp")
//            .addSnapshotListener { snapshots, _ ->
//                val messages = mutableListOf<ChatMessage>()
//                snapshots?.documents?.forEach { doc ->
//                    val msg = doc.toObject(ChatMessage::class.java)
//                    msg?.let {
//
//                        if (!it.seen && it.receiverId == currentUserId) {
//                            firestore.collection("chats")
//                                .document(chatId)
//                                .collection("messages")
//                                .document(doc.id)
//                                .update("seen", true)
//                        }
//                        messages.add(it)
//                    }
//                }
//                trySend(messages)
//            }
//        awaitClose { subscription.remove() }
//    }


    fun sendMessage(
        chatId: String,
        message: ChatMessage,
        currentUserId: String
    ) {
        if (chatId.isBlank()) {
            return
        }

        val chatRef = firestore.collection("chats").document(chatId)

        // 1️⃣ Message add karo
        chatRef
            .collection("messages")
            .add(message)
            .addOnSuccessListener {

                // 2️⃣ Chat ko sender ke liye restore karo
                chatRef.update(
                    mapOf(
                        "lastMessage" to message.message,
                        "lastMessageTime" to System.currentTimeMillis(),
                        "deletedAt.$currentUserId" to FieldValue.delete() // ⭐ KEY
                    )
                )
            }
            .addOnFailureListener { e ->
                Log.e("ChatRepository", "Failed to send message", e)
            }
    }

    // old chat message delete code with
//    fun observeMessages(
//        chatId: String,
//        currentUserId: String
//    ): Flow<List<ChatMessage>> = callbackFlow {
//
//        if (chatId.isBlank()) {
//            trySend(emptyList())
//            close()
//            return@callbackFlow
//        }
//
//        val chatRef = firestore.collection("chats").document(chatId)
//
//        // 1️⃣ Get deleteTime in coroutine-friendly way
//        val chatSnapshot = chatRef.get().await()
//        val deletedAtMap = chatSnapshot.get("deletedAt") as? Map<String, Long> ?: emptyMap()
//        val deleteTime = deletedAtMap[currentUserId] ?: 0L
//
//        // 2️⃣ Observe only messages after deleteTime
//        val messagesRef = chatRef.collection("messages")
//            .orderBy("timestamp")
//            .startAfter(deleteTime)  // 🔥 Start observing after deleted messages
//
//        val listener: ListenerRegistration = messagesRef.addSnapshotListener { snapshots, _ ->
//            val visibleMessages = snapshots?.documents
//                ?.mapNotNull { it.toObject(ChatMessage::class.java) }
//                ?: emptyList()
//
//            trySend(visibleMessages)
//        }
//
//        // 3️⃣ Properly close listener when flow collector is cancelled
//        awaitClose { listener.remove() }
//    }

    fun observeMessages(
        chatId: String,
        currentUserId: String
    ): Flow<List<ChatMessage>> = callbackFlow {

        if (chatId.isBlank()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val chatRef = firestore.collection("chats").document(chatId)
        val messagesRef = chatRef.collection("messages").orderBy("timestamp")

        // Get deleteTime per user
        val chatSnapshot = chatRef.get().await()
        val deletedAtMap = chatSnapshot.get("deletedAt") as? Map<String, Long> ?: emptyMap()
        val deleteTime = deletedAtMap[currentUserId] ?: 0L

        val listener = messagesRef.addSnapshotListener { snapshots, _ ->
            val visibleMessages = snapshots?.documents
                ?.mapNotNull { it.toObject(ChatMessage::class.java) }
                ?.filter { it.timestamp > deleteTime }  // ⭐ safe per-user filter
                ?: emptyList()

            trySend(visibleMessages)
        }

        awaitClose { listener.remove() }
    }



    fun deleteChatForMe(
        chatId: String,
        currentUserId: String,
        onResult: (success: Boolean) -> Unit
    ) {
        firestore.collection("chats")
            .document(chatId)
            .set(
                mapOf("deletedAt" to mapOf(currentUserId to System.currentTimeMillis())),
                SetOptions.merge() // 🔥 merge prevents failure if document/field missing
            )
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onResult(false)
            }
    }



    suspend fun uploadImage(uri: Uri): String {
        val ref = storage.reference.child("chat_images/${UUID.randomUUID()}.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }


}