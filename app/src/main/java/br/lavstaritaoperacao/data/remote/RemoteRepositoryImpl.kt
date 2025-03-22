package br.lavstaritaoperacao.data.remote

import br.lavstaritaoperacao.data.remote.api.SupabaseApi
import br.lavstaritaoperacao.domain.model.Service

class RemoteRepositoryImpl(
    private val supabaseApi: SupabaseApi
) : RemoteRepository {

    override suspend fun getAllServicesRemote(): List<Service> {
        return supabaseApi.getServices()
    }

}