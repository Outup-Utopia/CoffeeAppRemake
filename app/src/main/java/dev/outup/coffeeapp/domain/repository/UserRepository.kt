package dev.outup.coffeeapp.domain.repository

import dev.outup.coffeeapp.domain.model.User

interface UserRepository {
    fun save(user: User)
    suspend fun loadById(documentId: String): User?
}