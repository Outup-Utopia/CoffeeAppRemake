package dev.outup.coffeeapp.domain.model

class User(var id: String?, val userName: String?) : DomainModel {
    constructor() : this(null, null)
}