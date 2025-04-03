package br.lavstaritaoperacao.data.supabase

import br.lavstaritaoperacao.domain.model.Service2
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess


class ServiceRepositoryImpl(private val client: HttpClient) : ServiceRepository {

    private val baseUrl = "https://jmsdxcvdtcvjebjbbkwt.supabase.co/rest/v1/TB_SERVICE"
    private val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imptc2R4Y3ZkdGN2amViamJia3d0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIzNDY4NjUsImV4cCI6MjA1NzkyMjg2NX0.BZSee6owU57VNyiKVywCa7aPTxOnTexvL4Ut8tlmUv4"

    override suspend fun getServices(): Result<List<Service2>> = safeApiCall {
        val response = client.get("$baseUrl?select=*") {
            header("apikey", apiKey)
            }
        response.body()
    }


    override suspend fun insertService(newNote: Service2): Result<Service2> = safeApiCall {
        val response = client.post(baseUrl) {
            header("apikey", apiKey)
            contentType(ContentType.Application.Json)
            setBody(newNote)
        }
        if (response.status.isSuccess()) {
            response.body()
        } else {
            throw Exception("Erro ao inserir serviço: ${response.status.description}")
        }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = try {
        Result.success(apiCall())
    } catch (e: Exception) {
        println("Erro na chamada da API: ${e.message}")
        Result.failure(e)
    }
}