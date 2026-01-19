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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

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

//purana chalta hua sendMessage
//    fun sendMessage(
//        chatId: String, message: ChatMessage, currentUserId: String
//    ) {
//
//        if (chatId.isBlank()) {
//            return
//        }
//
//        val chatRef = firestore.collection("chats").document(chatId)
//
//
//        chatRef.collection("messages").add(message)
//            .addOnSuccessListener {
//
//
//                chatRef.update(
//                    mapOf(
//                        "lastMessage" to message.message,
//                        "lastMessageTime" to System.currentTimeMillis(),
//                        "deletedAt.$currentUserId" to FieldValue.delete() // ⭐ KEY
//                    )
//                )
//            }
//            .addOnFailureListener { e ->
//                Log.e("ChatRepository", "Failed to send message", e)
//            }
//    }

    // ***** working send message with delete functionality fully working ****/

//    fun sendMessage(
//        chatId: String,
//        message: ChatMessage,
//        currentUserId: String
//    ) {
//        if (chatId.isBlank()) return
//
//        val chatRef = firestore.collection("chats").document(chatId)
//
//        val batch = firestore.batch()
//
//        val msgRef = chatRef.collection("messages").document()
//        batch.set(msgRef, message)
//
//        batch.update(
//            chatRef,
//            mapOf(
//                "lastMessage" to message.message,
//                "lastMessageTime" to System.currentTimeMillis()
//                // ❌ deletedAt ko MAT chhedo
//            )
//        )
//
//        batch.commit().addOnSuccessListener {
//            Log.e("ChatRepository", "Success to send message", )
//
//        }
//            .addOnFailureListener {
//                Log.e("ChatRepository", "Failed to send message", it)
//            }
//    }


    fun sendMessage(
        chatId: String,
        message: ChatMessage,
        currentUserId: String
    ) {
        if (chatId.isBlank()) return

        val chatRef = firestore.collection("chats").document(chatId)
        val msgRef = chatRef.collection("messages").document()

        val batch = firestore.batch()

        // 1️⃣ Message save
        batch.set(msgRef, message)

        // 2️⃣ Chat document create/update safely
        batch.set(
            chatRef,
            mapOf(
                "lastMessage" to message.message,
                "lastMessageTime" to System.currentTimeMillis()
            ),
            SetOptions.merge() // 🔥 creates doc if missing
        )

        batch.commit()
            .addOnSuccessListener {
                Log.d("ChatRepository", "Message sent successfully")
            }
            .addOnFailureListener {
                Log.e("ChatRepository", "Failed to send message", it)
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

    // second solution

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
        Log.d("TESTING_DELETE","fetching_time"+ deleteTime)
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
        onResult: (Boolean) -> Unit
    ) {
        val chatRef = firestore.collection("chats").document(chatId)

        chatRef.update("deletedAt.$currentUserId", System.currentTimeMillis())
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { e ->
                // document/field not exist → create map safely
                val data = mapOf("deletedAt" to mapOf(currentUserId to System.currentTimeMillis()))
                chatRef.set(data, SetOptions.merge())
                    .addOnSuccessListener { onResult(true) }
                    .addOnFailureListener { e2 ->
                        e2.printStackTrace()
                        onResult(false)
                    }
            }
    }



//    fun deleteChatForMe(
//        chatId: String,
//        currentUserId: String,
//        onResult: (success: Boolean) -> Unit
//    ) {
//        firestore.collection("chats")
//            .document(chatId)
//            .set(
//                mapOf("deletedAt" to mapOf(currentUserId to System.currentTimeMillis())),
//                SetOptions.merge() // 🔥 merge prevents failure if document/field missing
//            )
//            .addOnSuccessListener {
//                onResult(true)
//            }
//            .addOnFailureListener { e ->
//                e.printStackTrace()
//                onResult(false)
//            }
//    }



    suspend fun uploadImage(uri: Uri): String {
        val ref = storage.reference.child("chat_images/${UUID.randomUUID()}.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }


}