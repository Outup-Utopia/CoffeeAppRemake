package dev.outup.coffeeapp.infrastructure.entity


data class Coffee(var id: String?, val itemName: String?, val size: String?, val options: HashMap<String, Any>?) {
    constructor() : this(null, null, null, null)
}
