package dev.outup.coffeeapp.infrastructure.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import dev.outup.coffeeapp.domain.model.Coffee
import dev.outup.coffeeapp.domain.repository.CoffeeRepository
import kotlinx.coroutines.tasks.await

object CoffeeRepositoryImpl : RepositoryImplCommon<Coffee>, CoffeeRepository {
    override val collection: CollectionReference = db.collection("coffees")

    override fun beforeSave(data: Coffee): HashMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun afterLoad(documentId: String, document: DocumentSnapshot?): Coffee? {
        val data = document?.toObject(dev.outup.coffeeapp.infrastructure.entity.Coffee::class.java)
        if (data != null) {
            data.id = documentId
        }
        return if (data == null) {
            null
        } else {
            Coffee.fromEntity(data)
        }
    }

    override suspend fun loadById(documentId: String): Coffee? {
        Log.d(ContentValues.TAG, "UserRepositoryImpl::loadById start.")
        val documentReference: DocumentReference = collection.document(documentId)
        val data = documentReference.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(ContentValues.TAG, "No such document... ID : $documentId")
            }
        }.addOnFailureListener { error ->
            Log.d(ContentValues.TAG, "get failed with ", error)
        }.await()
        return afterLoad(documentId, data)
    }
}