package dev.outup.coffeeapp.infrastructure.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import dev.outup.coffeeapp.domain.model.User
import dev.outup.coffeeapp.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await

object UserRepositoryImpl : RepositoryImplCommon<User>, UserRepository {
    override val collection = db.collection("users")

    override fun beforeSave(data: User): HashMap<String, Any> {
        return hashMapOf(
            "userName" to data.userName as Any
        )
    }

    override fun afterLoad(documentId: String, document: DocumentSnapshot?): User? {
        val data = document?.toObject(User::class.java)
        if (data != null) {
            data.id = documentId
        }
        return data
    }

    override suspend fun loadById(documentId: String): User? {
        Log.d(TAG, "UserRepositoryImpl::loadById start.")
        val documentReference: DocumentReference = collection.document(documentId)
        val data = documentReference.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(TAG, "No such document... ID : $documentId")
            }
        }.addOnFailureListener { error ->
            Log.d(TAG, "get failed with ", error)
        }.await()
        return afterLoad(documentId, data)
    }

    override fun save(user: User) {
        val document = beforeSave(user)
        if (user.id == null) {
            collection.add(document)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    Log.d(TAG, "DocumentSnapshot data: $document")
                }
                .addOnFailureListener { error -> Log.w(TAG, "Error writing document", error) }
        } else {
            collection.document(user.id!!).set(document)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                    Log.d(TAG, "DocumentSnapshot data: $document")
                }
                .addOnFailureListener { error -> Log.w(TAG, "Error writing document", error) }
        }

    }
}