package dev.outup.coffeeapp.domain.repository

import dev.outup.coffeeapp.domain.model.Coffee

interface CoffeeRepository {
    suspend fun loadById(documentId: String): Coffee?
}