package br.lavstaritaoperacao.data.supabase

import br.lavstaritaoperacao.domain.model.Service2
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType


class NotesRepositoryImpl(private val client: HttpClient) : NotesRepository {

    override suspend fun getServices(): Result<List<Service2>> {
        return try {
            val response = client.get("https://jmsdxcvdtcvjebjbbkwt.supabase.co/rest/v1/TB_SERVICE?select=*") {
                header("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imptc2R4Y3ZkdGN2amViamJia3d0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIzNDY4NjUsImV4cCI6MjA1NzkyMjg2NX0.BZSee6owU57VNyiKVywCa7aPTxOnTexvL4Ut8tlmUv4")
            }
            val jsonString = response.bodyAsText()
            println("JSON Bruto: $jsonString")
            val services = response.body<List<Service2>>()
            Result.success(services)

        }catch (e: Exception){
            println("Erro ao buscar serviços: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun insertService(newNote: Service2): Result<Service2> {
        return try {
            val response = client.post("https://jmsdxcvdtcvjebjbbkwt.supabase.co/rest/v1/TB_SERVICE") {
                header("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imptc2R4Y3ZkdGN2amViamJia3d0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIzNDY4NjUsImV4cCI6MjA1NzkyMjg2NX0.BZSee6owU57VNyiKVywCa7aPTxOnTexvL4Ut8tlmUv4")
                contentType(ContentType.Application.Json)
                setBody(newNote)
            }
            if(response.status.value in 200..299){
                val insertedService = response.body<Service2>()
                Result.success(insertedService)
            }else{
                throw Exception("Erro ao inserir serviço: ${response.status.description}")
            }
        } catch (e: Exception) {
            println("Erro ao inserir nota: ${e.message}")
            Result.failure(e)
        }
    }
}