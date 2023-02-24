package dev.outup.coffeeapp.domain.model.option

import dev.outup.coffeeapp.domain.model.DomainModel

interface Option : DomainModel {
    val itemName: String
}

enum class AppendableOption(itemName: String) : Option {
    SOURCE("ソース") {
        override val itemName: String = "ソース"
    },
    POWDER("パウダー") {
        override val itemName: String = "パウダー"
    },
    HONEY("はちみつ") {
        override val itemName: String = "はちみつ"
    };

    companion object {
        fun fromString(itemName: String): AppendableOption? {
            return when (itemName) {
                "ソース" -> SOURCE
                "パウダー" -> POWDER
                "はちみつ" -> HONEY
                else -> null
            }
        }
    }
}

interface AmountInterFace {
    val description: String
}

enum class Amount(override val description: String) : AmountInterFace {
    DOUBLE("2倍") {
        override val description = "2倍"
    },
    HALF("半分") {
        override val description = "半分"
    },
    NONE("抜き") {
        override val description = "抜き"
    };

    companion object {
        fun fromString(itemName: String): Amount? {
            return when (itemName) {
                "2倍" -> DOUBLE
                "半分" -> HALF
                "抜き" -> NONE
                else -> null
            }
        }
    }
}

interface AmountMutableInterface : Option {
    var amount: Amount?
}

enum class AmountMutableOption(itemName: String) : AmountMutableInterface {
    WHIP("ホイップ") {
        override var amount: Amount? = null
        override val itemName: String = "ホイップ"
    },
    CHOCOLATE_CHIP("チョコチップ") {
        override val itemName: String = "チョコチップ"
        override var amount: Amount? = null
    },
    FOAM_MILK("フォームミルク") {
        override val itemName: String = "フォームミルク"
        override var amount: Amount? = null
    },
    ICE("氷") {
        override val itemName: String = "氷"
        override var amount: Amount? = null
    };

    companion object {
        fun fromString(itemName: String): AmountMutableOption? {
            return when (itemName) {
                "ホイップ" -> WHIP
                "チョコチップ" -> CHOCOLATE_CHIP
                "フォームミルク" -> FOAM_MILK
                "氷" -> ICE
                else -> null
            }
        }
    }
}