package dev.outup.coffeeapp.domain.model

import dev.outup.coffeeapp.domain.enums.CoffeeSize
import dev.outup.coffeeapp.domain.model.option.Amount
import dev.outup.coffeeapp.domain.model.option.AmountMutableOption
import dev.outup.coffeeapp.domain.model.option.AppendableOption
import dev.outup.coffeeapp.domain.model.option.Option

class Coffee(var id: String?, val itemName: String?, val size: CoffeeSize?, val options: List<Option>?) : DomainModel {
    companion object {
        fun fromEntity(coffeeEntity: dev.outup.coffeeapp.infrastructure.entity.Coffee): Coffee {
            val options = coffeeEntity.options?.map { (optionKey, optionValue) ->
                if (optionValue == "追加") {
                    AppendableOption.fromString(optionKey) as Option
                } else {
                    val option = AmountMutableOption.fromString(optionKey)
                    option?.let { option.amount = Amount.fromString(optionValue as String) }
                    option as Option
                }
            }
            val size = coffeeEntity.size?.let { CoffeeSize.fromString(it) }
            return Coffee(coffeeEntity.id, coffeeEntity.itemName, size, options)
        }
    }

    fun description(): String {
        val description = arrayListOf<String>()
        description.add("商品名 : $itemName")
        description.add("サイズ : ${size?.displayValue}")
        options?.forEach { option ->
            if (option is AmountMutableOption) {
                description.add("${option.itemName} - ${option.amount?.description}")
            } else {
                description.add("${option.itemName} - 追加")
            }
        }
        return java.lang.String.join("\n", description)
    }
}