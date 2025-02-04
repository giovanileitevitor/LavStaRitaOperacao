package br.lavstaritaoperacao.domain.model

data class StartApp(
    val id: Int,
    val email: String,
    val device: String,
    val isSuccess: Boolean,
    val profileType: String,
    val obs: String
)

fun mockStartApp(): StartApp {
    return StartApp(
        id = 1,
        email = "email@gmail.com",
        device = "android",
        isSuccess = true,
        profileType = "ADMIN",
        obs = ""
    )
}