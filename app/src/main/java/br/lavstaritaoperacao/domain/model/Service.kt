package br.lavstaritaoperacao.domain.model

import kotlin.random.Random

data class Service(
    var id: Int,
    var qtd: Int,
    var client: String,
    var dataIn: String
)

fun generateServices(qtd: Int): ArrayList<Service>{
    val services = ArrayList<Service>()

    for (i in 0..qtd){
        services.add(
            Service(
                id = i,
                qtd = 10 + i,
                client = "Giovani Leite",
                dataIn = "10 / Jan / 2025"
            )
        )
    }

    return services

}

fun emptyService(): Service {
    return Service(
        id = 0,
        qtd = 0,
        client = "invalid client",
        dataIn = "01 / 01 / 0000"
    )
}
