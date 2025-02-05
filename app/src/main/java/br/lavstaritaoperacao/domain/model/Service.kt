package br.lavstaritaoperacao.domain.model

import java.io.Serializable
import kotlin.random.Random

data class Service(
    var id: Int? = 0,
    var clientName: String,
    var clientPhone: String,
    var idItems: Int,
    var qtdItems: Int,
    var dataIn: String
) : Serializable

fun generateServices(qtd: Int): ArrayList<Service>{
    val services = ArrayList<Service>()

    for (i in 0..qtd){
        services.add(
            Service(
                id = i,
                clientName = "Giovani Leite",
                clientPhone = "11 975313142",
                idItems = 1,
                qtdItems = 10,
                dataIn = "10 / Jan / 2025"
            )
        )
    }

    return services

}

fun emptyService(): Service {
    return Service(
        id = 0,
        clientName = "invalid client",
        clientPhone = "00 00000-0000",
        dataIn = "01 / 01 / 0000",
        qtdItems = 0,
        idItems = 0
    )
}
