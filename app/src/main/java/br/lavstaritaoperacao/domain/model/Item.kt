package br.lavstaritaoperacao.domain.model

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Item(
    val id: Int? = 0,
    val name: String,
    val qtd: Int
)

fun genericItem(): Item{
    return Item(
        id = Random.nextInt(),
        name = "Camiseta",
        qtd = Random.nextInt(1,100)
    )
}


fun emptyItemList(): ArrayList<Item>{
    return ArrayList<Item>()
}

fun insertItem(newItem: Item): ArrayList<Item> {
    val newList = arrayListOf<Item>()
    newList.add(
        Item(
            id = newItem.id,
            name = newItem.name,
            qtd = newItem.qtd
        )
    )
    return newList
}