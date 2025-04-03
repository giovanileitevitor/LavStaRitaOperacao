package br.lavstaritaoperacao.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Service2(
    val id: Int? = 0,
    val created_at: String? = "",
    val clientName: String? = "",
    val qtdItems: Int? = 0,
    val clientPhone: String? = ""
)