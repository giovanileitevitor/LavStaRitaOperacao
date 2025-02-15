package br.lavstaritaoperacao.domain.model

import java.io.Serializable

data class Service(
    var serviceId: Int? = 0,
    var clientName: String,
    var clientPhone: String,
    var qtdItems: Int,
    var statusService: String,
    var obs: String? = "-",
    var dataIn: String,
    var price: String
) : Serializable

fun generateServices(qtd: Int): ArrayList<Service>{
    val services = ArrayList<Service>()

    for (i in 0..qtd){
        services.add(
            Service(
                serviceId = i,
                clientName = "Giovani Leite",
                clientPhone = "11 975313142",
                qtdItems = 10,
                dataIn = "10 / Jan / 2025",
                obs = "-",
                statusService = "",
                price = "R$ 10"
            )
        )
    }

    return services

}

fun emptyService(): Service {
    return Service(
        serviceId = 0,
        clientName = "invalid client",
        clientPhone = "00 00000-0000",
        dataIn = "01 / 01 / 0000",
        qtdItems = 0,
        obs = "sem observações",
        statusService = "Lavagem",
        price = "R$ 0,00"
    )
}

