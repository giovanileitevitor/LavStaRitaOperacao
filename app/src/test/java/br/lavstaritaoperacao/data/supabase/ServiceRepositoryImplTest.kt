package br.lavstaritaoperacao.data.supabase

import br.lavstaritaoperacao.domain.model.Service2
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class ServiceRepositoryImplTest {

    @Test
    fun `getServices should return a list of services on success`() = runBlocking {
        val mockEngine = MockEngine { request ->
            respond(
                content = Json.encodeToString(listOf(Service2(1, "Service 1"), Service2(2, "Service 2"))),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val client = HttpClient(mockEngine) {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json)
            }
        }
        val repository = ServiceRepositoryImpl(client)
        val result = repository.getServices()
        assertEquals(Result.success(listOf(Service2(1, "Service 1"), Service2(2, "Service 2"))), result)
    }

    @Test
    fun `getServices should return failure on error`() = runBlocking {
        val mockEngine = MockEngine { request ->
            respond(
                content = "Erro",
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val client = HttpClient(mockEngine) {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json)
            }
        }
        val repository = ServiceRepositoryImpl(client)
        val result = repository.getServices()
        assert(result.isFailure)
    }

    @Test
    fun `insertService should return the inserted service on success`() = runBlocking {
        val newService = Service2(3, "New Service")
        val mockEngine = MockEngine { request ->
            respond(
                content = Json.encodeToString(newService),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val client = HttpClient(mockEngine) {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json)
            }
        }
        val repository = ServiceRepositoryImpl(client)
        val result = repository.insertService(newService)
        assertEquals(Result.success(newService), result)
    }

    @Test
    fun `insertService should return failure on error`() = runBlocking {
        val newService = Service2(3, "New Service")
        val mockEngine = MockEngine { request ->
            respond(
                content = "Erro",
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val client = HttpClient(mockEngine) {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json)
            }
        }
        val repository = ServiceRepositoryImpl(client)
        val result = repository.insertService(newService)
        assert(result.isFailure)
    }
}