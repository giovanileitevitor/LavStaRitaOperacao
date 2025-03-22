package br.lavstaritaoperacao.domain.model

data class LoginResult(
    val status: Boolean,
    val codeResponse: Int,
    val userType: UserType
)

enum class UserType {
    ADMIN,
    CLIENT
}


