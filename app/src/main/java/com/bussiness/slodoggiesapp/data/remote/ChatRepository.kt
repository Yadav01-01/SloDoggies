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

//nikunj sir code 21-01-2026
 /*   fun sendMessage(
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
    }*/



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

    //nikunj sir code
    /*fun observeMessages(
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
    }*/
/*    fun sendMessage(
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

        // 2️⃣ Chat document update with unseen count
        val updates = mutableMapOf<String, Any>(
            "lastMessage" to message.message,
            "lastMessageTime" to System.currentTimeMillis(),
            "lastMessageSenderId" to currentUserId
        )

        // 3️⃣ Increase unseen count for receiver
        // If current user is sender, increase receiver's unseen count
        if (message.receiverId.isNotBlank() && message.receiverId != currentUserId) {
            val unseenField = "unseenCount.${message.receiverId}"
           // updates[unseenField] = FieldValue.increment(1)
            chatRef.get().addOnSuccessListener { document ->
                val currentUnseenCount = document.getLong("unseenCount.${message.receiverId}") ?: 0L
                updates[unseenField] = currentUnseenCount + 1
            }.addOnFailureListener {
                // If field doesn't exist, initialize it with 1
                updates[unseenField] = 1
            }
        }

        batch.set(
            chatRef,
            updates,
            SetOptions.merge() // 🔥 creates doc if missing
        )

        batch.commit()
            .addOnSuccessListener {
                Log.d("ChatRepository", "Message sent successfully")
            }
            .addOnFailureListener {
                Log.e("ChatRepository", "Failed to send message", it)
            }
    }*/
/*    fun sendMessage(
        chatId: String,
        message: ChatMessage,
        currentUserId: String
    ) {
        if (chatId.isBlank()) return

        val chatRef = firestore.collection("chats").document(chatId)
        val msgRef = chatRef.collection("messages").document()

        // First, ensure the chat document exists with unseenCount field
        chatRef.get().addOnSuccessListener { document ->
            val batch = firestore.batch()

            // 1️⃣ Message save
            batch.set(msgRef, message)

            // 2️⃣ Prepare updates
            val updates = mutableMapOf<String, Any>(
                "lastMessage" to message.message,
                "lastMessageTime" to System.currentTimeMillis(),
                "lastMessageSenderId" to currentUserId
            )

            // 3️⃣ Increase unseen count for receiver
            if (message.receiverId.isNotBlank() && message.receiverId != currentUserId) {
                val receiverId = message.receiverId
                val unseenField = "unseenCount.$receiverId"

                // Get current unseen count
                val unseenCountMap = document.get("unseenCount") as? Map<String, Long> ?: emptyMap()
                val currentCount = unseenCountMap[receiverId] ?: 0L

                updates[unseenField] = currentCount + 1
            }

            batch.set(chatRef, updates, SetOptions.merge())

            batch.commit()
                .addOnSuccessListener {
                    Log.d("ChatRepository", "Message sent successfully")
                }
                .addOnFailureListener {
                    Log.e("ChatRepository", "Failed to send message", it)
                }
        }.addOnFailureListener {
            // If document doesn't exist, create it with initial unseenCount
            createChatDocumentWithUnseenCount(chatId, message, currentUserId)
        }
    }*/
    fun sendMessage(
        chatId: String,
        message: ChatMessage,
        currentUserId: String
    ) {
        if (chatId.isBlank()) return

        val chatRef = firestore.collection("chats").document(chatId)
        val msgRef = chatRef.collection("messages").document()

        // पहले chat document को fetch करें
        chatRef.get().addOnSuccessListener { document ->
            val batch = firestore.batch()

            // 1️⃣ Message save
            batch.set(msgRef, message)

            // 2️⃣ Prepare updates
            val updates = hashMapOf<String, Any>(
                "lastMessage" to message.message,
                "lastMessageTime" to System.currentTimeMillis(),
                "lastMessageSenderId" to currentUserId
            )

            // 3️⃣ Increase unseen count for receiver
            if (message.receiverId.isNotBlank() && message.receiverId != currentUserId) {
                val receiverId = message.receiverId

                // Get current unseen count map
                val unseenCountMap = if (document.exists()) {
                    document.get("unseenCount") as? MutableMap<String, Long> ?: mutableMapOf()
                } else {
                    mutableMapOf()
                }

                // Increment count for receiver
                val currentCount = unseenCountMap[receiverId] ?: 0L
                unseenCountMap[receiverId] = currentCount + 1

                // Add to updates
                updates["unseenCount"] = unseenCountMap

                // Debug log
                Log.d("ChatRepository",
                    "Updating unseenCount for receiver: $receiverId, " +
                            "Current count: $currentCount, " +
                            "New count: ${currentCount + 1}"
                )
            }

            // 4️⃣ Apply updates with merge
            batch.set(chatRef, updates, SetOptions.merge())

            // 5️⃣ Commit batch
            batch.commit()
                .addOnSuccessListener {
                    Log.d("ChatRepository",
                        "Message sent successfully. unseenCount updated for: ${message.receiverId}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.e("ChatRepository", "Failed to send message", e)
                }
        }.addOnFailureListener { e ->
            Log.e("ChatRepository", "Failed to fetch chat document", e)
            // Fallback: create new document
            sendMessageFallback(chatId, message, currentUserId)
        }
    }

    private fun sendMessageFallback(
        chatId: String,
        message: ChatMessage,
        currentUserId: String
    ) {
        val chatRef = firestore.collection("chats").document(chatId)
        val msgRef = chatRef.collection("messages").document()

        val batch = firestore.batch()

        // 1️⃣ Message save
        batch.set(msgRef, message)

        // 2️⃣ Create new chat document with unseenCount
        val updates = hashMapOf<String, Any>(
            "lastMessage" to message.message,
            "lastMessageTime" to System.currentTimeMillis(),
            "lastMessageSenderId" to currentUserId
        )

        // 3️⃣ Initialize unseenCount if receiver exists
        if (message.receiverId.isNotBlank() && message.receiverId != currentUserId) {
            val unseenCountMap = mutableMapOf<String, Long>()
            unseenCountMap[message.receiverId] = 1
            updates["unseenCount"] = unseenCountMap

            Log.d("ChatRepository",
                "Creating new chat with unseenCount: $unseenCountMap for receiver: ${message.receiverId}"
            )
        }

        // 4️⃣ Apply updates
        batch.set(chatRef, updates, SetOptions.merge())

        // 5️⃣ Commit batch
        batch.commit()
            .addOnSuccessListener {
                Log.d("ChatRepository", "Message sent successfully with new chat document")
            }
            .addOnFailureListener { e ->
                Log.e("ChatRepository", "Failed to send message", e)
            }
    }

    private fun createChatDocumentWithUnseenCount(
        chatId: String,
        message: ChatMessage,
        currentUserId: String
    ) {
        val chatRef = firestore.collection("chats").document(chatId)
        val msgRef = chatRef.collection("messages").document()

        val batch = firestore.batch()

        // 1️⃣ Message save
        batch.set(msgRef, message)

        // 2️⃣ Create chat document with initial unseenCount
        val initialUnseenCount = mutableMapOf<String, Any>()
        if (message.receiverId.isNotBlank() && message.receiverId != currentUserId) {
            initialUnseenCount["unseenCount.${message.receiverId}"] = 1
        }

        val updates = mutableMapOf<String, Any>(
            "lastMessage" to message.message,
            "lastMessageTime" to System.currentTimeMillis(),
            "lastMessageSenderId" to currentUserId
        )

        updates.putAll(initialUnseenCount)

        batch.set(chatRef, updates, SetOptions.merge())

        batch.commit()
            .addOnSuccessListener {
                Log.d("ChatRepository", "Message sent successfully with new chat document")
            }
            .addOnFailureListener {
                Log.e("ChatRepository", "Failed to send message", it)
            }
    }

/*    fun observeMessages(
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
                ?.mapNotNull { doc ->
                    val msg = doc.toObject(ChatMessage::class.java)
                    msg?.let {
                        // Mark as seen if current user is receiver
                        if (!it.seen && it.receiverId == currentUserId) {
                            // Update seen status in Firebase
                            messagesRef.document(doc.id).update("seen", true)

                            // Reset unseen count for this user
                            val unseenField = "unseenCount.$currentUserId"
                            chatRef.update(unseenField, 0)
                                .addOnFailureListener { e ->
                                    Log.e("ChatRepository", "Failed to reset unseen count", e)
                                }
                        }
                        it
                    }
                }
                ?.filter { it.timestamp > deleteTime }  // ⭐ safe per-user filter
                ?: emptyList()

            trySend(visibleMessages)
        }

        awaitClose { listener.remove() }
    }*/
/*fun observeMessages(
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
            ?.mapNotNull { doc ->
                val msg = doc.toObject(ChatMessage::class.java)
                msg?.let {
                    // Mark as seen if current user is receiver
                    if (!it.seen && it.receiverId == currentUserId) {
                        // Update seen status in Firebase - CORRECTED LINE
                        chatRef.collection("messages").document(doc.id).update("seen", true)

                        // Reset unseen count for this user
                        val unseenField = "unseenCount.$currentUserId"
                        chatRef.update(unseenField, 0)
                            .addOnFailureListener { e ->
                                Log.e("ChatRepository", "Failed to reset unseen count", e)
                            }
                    }
                    it
                }
            }
            ?.filter { it.timestamp > deleteTime }  // ⭐ safe per-user filter
            ?: emptyList()

        trySend(visibleMessages)
    }

    awaitClose { listener.remove() }
}*/
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
            ?.mapNotNull { doc ->
                val msg = doc.toObject(ChatMessage::class.java)
                msg?.let {
                    // Mark as seen if current user is receiver
                    // BUT DON'T RESET UNSEEN COUNT HERE
                    if (!it.seen && it.receiverId == currentUserId) {
                        // Only mark message as seen in Firebase
                        chatRef.collection("messages").document(doc.id).update("seen", true)

                        // ⚠️ DON'T reset unseenCount here
                        // unseenCount will be reset only when user opens the chat screen
                    }
                    it
                }
            }
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

    fun observeAllChats(currentUserId: String): Flow<List<ChatSummary>> = callbackFlow {
        if (currentUserId.isBlank()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        // Query all chats where current user is participant OR all chats in collection
        val listener = firestore.collection("chats")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Log.e("ChatRepository", "Error fetching chats", error)
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val chatSummaries = mutableListOf<ChatSummary>()

                snapshots?.documents?.forEach { chatDoc ->
                    val chatId = chatDoc.id

                    // Get basic chat info
                    val lastMessage = chatDoc.getString("lastMessage") ?: ""
                   /* val lastMessageTime = chatDoc.getLong("lastMessageTime") ?: 0L*/
                    val lastMessageTime = when (val value = chatDoc.get("lastMessageTime")) {
                        is Long -> value
                        is com.google.firebase.Timestamp -> value.toDate().time
                        is Number -> value.toLong()
                        else -> 0L
                    }
                    // Get last message sender ID
                    val lastMessageSenderId = chatDoc.getString("lastMessageSenderId") ?: ""

                    // Get unseen message count for current user
                    val unseenCountMap = chatDoc.get("unseenCount") as? Map<String, Long> ?: emptyMap()
                    val totalUnSeenMessageCount = unseenCountMap[currentUserId]?.toInt() ?: 0

                    // Debug log
                    Log.d("ChatRepositoryDebug",
                        "Chat: $chatId, " +
                                "UnseenCountMap: $unseenCountMap, " +
                                "CurrentUserId: $currentUserId, " +
                                "TotalUnseen: $totalUnSeenMessageCount"
                    )

                    // Get deletedAt timestamp for current user
                    val deletedAtMap = chatDoc.get("deletedAt") as? Map<String, Long> ?: emptyMap()
                    val deleteTime = deletedAtMap[currentUserId] ?: 0L

                    // Only show chat if not deleted by current user
                   /* if (lastMessageTime > deleteTime) {
                        // Get participants info from chat document
                        val participants = chatDoc.get("participants") as? List<String> ?: emptyList()

                        // Find other user ID
                        val otherUserId = participants.firstOrNull { it != currentUserId }

                        chatSummaries.add(
                            ChatSummary(
                                chatId = chatId,
                                otherUserId = otherUserId ?: "",
                                lastMessage = lastMessage,
                                lastMessageTime = lastMessageTime,
                                // We'll fetch user details separately
                            )
                        )
                    }*/
                    if (lastMessageTime > deleteTime) {
                        chatSummaries.add(
                            ChatSummary(
                                chatId = chatId,
                                lastMessage = lastMessage,
                                lastMessageTime = lastMessageTime,
                                lastMessageSenderId = lastMessageSenderId,
                                totalUnSeenMessageCount = totalUnSeenMessageCount
                            )
                        )
                    }

                }

                // Sort by last message time (newest first)
                chatSummaries.sortByDescending { it.lastMessageTime }
                trySend(chatSummaries)
            }

        awaitClose { listener.remove() }
    }


    // Function to get user details
    suspend fun getUserDetails(userId: String): UserDetails? {
        return try {
            val snapshot = firestore.collection("users").document(userId).get().await()
            snapshot.toObject(UserDetails::class.java)
        } catch (e: Exception) {
            Log.e("ChatRepository", "Error fetching user details", e)
            null
        }
    }

    // Data classes
    data class ChatSummary(
        val chatId: String,

        val lastMessage: String,
        val lastMessageTime: Long,
        val lastMessageSenderId: String = "",
        val totalUnSeenMessageCount: Int = 0  // यह field add करें
    )

    data class UserDetails(
        val id: String = "",
        val name: String = "",
        val profileImage: String = "",
        val email: String = ""
    )
}