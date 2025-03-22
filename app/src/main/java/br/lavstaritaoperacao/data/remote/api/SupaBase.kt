package br.lavstaritaoperacao.data.remote.api

import br.lavstaritaoperacao.domain.model.Service
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface SupabaseApi {
    @GET("services")
    suspend fun getServices(): List<Service>

    @POST("services")
    suspend fun createService(@Body service: Service): Service
}