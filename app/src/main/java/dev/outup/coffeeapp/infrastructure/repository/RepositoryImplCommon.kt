package dev.outup.coffeeapp.infrastructure.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface RepositoryImplCommon<T> {
    val db: FirebaseFirestore
        get() = Firebase.firestore

    val collection: CollectionReference

    fun beforeSave(data: T): HashMap<String, Any>
    fun afterLoad(documentId: String, document: DocumentSnapshot?): T?
}