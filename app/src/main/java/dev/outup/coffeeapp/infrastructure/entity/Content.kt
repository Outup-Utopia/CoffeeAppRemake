package dev.outup.coffeeapp.infrastructure.entity

import com.google.firebase.Timestamp

data class Content(
    var id: String?,
    val coffee: String?,
    val imageLocation: String?,
    val title: String?,
    val userId: String?,
    val createdAt: Timestamp?,
    val updatedAt: Timestamp?
) {
    constructor() : this(null, null, null, null, null, null, null)
}
