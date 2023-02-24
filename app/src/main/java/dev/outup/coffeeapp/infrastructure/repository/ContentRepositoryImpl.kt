package dev.outup.coffeeapp.infrastructure.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import dev.outup.coffeeapp.infrastructure.entity.Content
import dev.outup.coffeeapp.domain.repository.ContentRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

object ContentRepositoryImpl : RepositoryImplCommon<Content>, ContentRepository {
    override val collection = db.collection("contents")

    override fun beforeSave(data: Content): HashMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun afterLoad(documentId: String, document: DocumentSnapshot?): Content? {
        val data = document?.toObject(Content::class.java)
        if (data != null) {
            data.id = documentId
        }
        return data
    }

    override fun save(content: Content) {
        TODO("Not yet implemented")
    }

    override fun delete(documentId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun confirmExist(documentId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun loadById(documentId: String): Content {
        TODO("Not yet implemented")
    }

    override suspend fun loadAsTimeline(userId: String): List<Content?> {
        Log.d(TAG, "ContentRepositoryImpl::loadAsTimeline start.")
        val contents = collection.orderBy("createdAt").limit(10).get().await().map { snapshot ->
            afterLoad(snapshot.id, snapshot)
        }
        return contents
    }
}
