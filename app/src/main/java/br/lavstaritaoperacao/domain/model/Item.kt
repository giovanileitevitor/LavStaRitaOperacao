package br.lavstaritaoperacao.domain.model

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Item(
    val id: Int? = 0,
    val name: String,
    val qtd: Int,
    val serviceId: Int? = 0,
    val obs: String? = ""
)

fun genericItem(): Item{
    return Item(
        id = Random.nextInt(),
        name = gerarNomeDeRoupaAleatorio(),
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

fun gerarNomeDeRoupaAleatorio(): String {
    val roupas = listOf(
        "Camiseta",
        "Calça",
        "Camisa",
        "Cueca",
        "Pijama",
        "Blusa",
        "Casaco",
        "Vestido",
        "Saia",
        "Shorts",
        "Meias",
        "Sutiã",
        "Calcinha",
        "Boné",
        "Chapéu",
        "Luvas",
        "Cachecol"
    )


    val indiceAleatorio = Random.nextInt(roupas.size)
    return roupas[indiceAleatorio]
}

fun roupas() = listOf(
    "Camiseta",
    "Calça",
    "Camisa",
    "Cueca",
    "Pijama",
    "Blusa",
    "Casaco",
    "Vestido",
    "Saia",
    "Shorts",
    "Meias",
    "Sutiã",
    "Calcinha",
    "Boné",
    "Chapéu",
    "Luvas",
    "Cachecol"
)

fun gerarListaDeNomesDeRoupasAleatorios(quantidade: Int): List<String> {
    val lista = mutableListOf<String>()
    for (i in 0 until quantidade) {
        lista.add(gerarNomeDeRoupaAleatorio())
    }
    return lista
}