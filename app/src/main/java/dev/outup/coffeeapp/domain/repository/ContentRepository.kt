package dev.outup.coffeeapp.domain.repository

import dev.outup.coffeeapp.infrastructure.entity.Content

interface ContentRepository {
    fun save(content: Content)
    fun delete(documentId: String)
    suspend fun confirmExist(documentId: String): Boolean
    suspend fun loadById(documentId: String): Content
    suspend fun loadAsTimeline(userId: String): List<Content?>
}