package dev.outup.coffeeapp.domain.enums

interface CoffeeSizeInterface {
    val displayValue: String
}

enum class CoffeeSize(displayValue: String) : CoffeeSizeInterface {
    SHORT("Short") {
        override val displayValue = "Short"
    },
    TALL("Tall") {
        override val displayValue = "Tall"
    },
    GRANDE("Gradle") {
        override val displayValue = "Grande"
    },
    VENTI("Venti") {
        override val displayValue = "Venti"
    };

    companion object {
        fun fromString(displayValue: String): CoffeeSize? {
            return when (displayValue) {
                "Short" -> SHORT
                "Tall" -> TALL
                "Grande" -> GRANDE
                "Venti" -> VENTI
                else -> null
            }
        }

    }
}