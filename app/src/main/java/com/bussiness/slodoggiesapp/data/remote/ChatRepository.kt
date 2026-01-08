package com.bussiness.slodoggiesapp.data.remote


import android.net.Uri
import android.util.Log
import com.bussiness.slodoggiesapp.data.model.common.ChatMessage
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChatRepository(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
){
//    fun sendMessage(chatId: String, message: ChatMessage): Task<Void> {
//        return firestore.collection("chats")
//            .document(chatId)
//            .collection("messages")
//            .document()
//            .set(message)
//    }

    fun sendMessage(chatId: String, message: ChatMessage) {
        firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .document()
            .set(message)
            .addOnSuccessListener {
                Log.d("ChatRepository", "Message sent successfully!")
            }
            .addOnFailureListener { e ->
                Log.e("ChatRepository", "Failed to send message: ${e.message}", e)

            }
    }


    fun observeMessages(chatId: String,currentUserId:String): Flow<List<ChatMessage>> = callbackFlow {

        val subscription = firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshots, _ ->
                val messages = mutableListOf<ChatMessage>()
                snapshots?.documents?.forEach { doc ->
                    val msg = doc.toObject(ChatMessage::class.java)
                    msg?.let {

                        if (!it.seen && it.receiverId == currentUserId) {
                            firestore.collection("chats")
                                .document(chatId)
                                .collection("messages")
                                .document(doc.id)
                                .update("seen", true)
                        }
                        messages.add(it)
                    }
                }
                trySend(messages)
            }
        awaitClose { subscription.remove() }
    }

    suspend fun uploadImage(uri: Uri): String {
        val ref = storage.reference.child("chat_images/${UUID.randomUUID()}.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }


}