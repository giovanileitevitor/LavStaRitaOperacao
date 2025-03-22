package br.lavstaritaoperacao.domain.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

data class Service(
    var serviceId: Int? = 0,
    var clientName: String,
    var clientPhone: String,
    var qtdItems: Int,
    var statusService: StatusService? = StatusService.EM_LAVAGEM,
    var statusPayment: StatusPayment? = StatusPayment.NOT_PAID,
    var obs: String? = "-",
    var dataIn: String,
    var price: String
) : Serializable

enum class StatusService {
    EM_LAVAGEM,
    CONCLUIDO,
    OTHER
}

enum class StatusPayment{
    PAID,
    NOT_PAID
}

enum class ButtonStatus{
    ALL,
    PENDING,
    COMPLETED
}

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
                statusService = StatusService.EM_LAVAGEM,
                statusPayment = StatusPayment.NOT_PAID,
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
        statusService = StatusService.EM_LAVAGEM,
        statusPayment = StatusPayment.NOT_PAID,
        price = "R$ 0,00"
    )
}

fun orderByData(services: List<Service>, decreaseOrder: Boolean = false): List<Service> {
    val formatoData = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    return if (decreaseOrder) {
        services.sortedByDescending {
            formatoData.parse(it.dataIn) // Converte a string para Date
        }
    } else {
        services.sortedBy {
            formatoData.parse(it.dataIn) // Converte a string para Date
        }
    }
}

