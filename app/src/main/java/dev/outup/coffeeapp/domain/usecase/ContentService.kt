package dev.outup.coffeeapp.domain.usecase

import android.content.ContentValues.TAG
import android.util.Log
import dev.outup.coffeeapp.domain.model.Content
import dev.outup.coffeeapp.domain.repository.CoffeeRepository
import dev.outup.coffeeapp.domain.repository.ContentRepository

class ContentService(private val contentRepository: ContentRepository, private val coffeeRepository: CoffeeRepository) {

    suspend fun getTimeline(userId: String): List<Content> {
        Log.d(TAG, "ContentService::getTimeline start.")
        val contents = contentRepository.loadAsTimeline(userId)
        val contentModels =
            contents.map { content ->
                if (content?.coffee != null) {
                    val coffee = coffeeRepository.loadById(content.coffee)
                    Content(
                        content.id,
                        content.title,
                        content.imageLocation,
                        content.userId,
                        coffee,
                        content.createdAt,
                        content.updatedAt
                    )
                } else {
                    null
                }
            }.filterNotNull()
        Log.d(TAG, "ContentService::Timeline loaded: ")
        Log.d(TAG, contentModels.toString())
        return contentModels
    }

    fun registerContent(userId: String, content: Content) {
        TODO("implement")
    }

    fun updateContent(userId: String, content: Content) {
        TODO("implement")
    }

    fun deleteContent(userId: String, documentId: String) {
        TODO("implement")
    }
}