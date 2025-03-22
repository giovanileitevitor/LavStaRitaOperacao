package br.lavstaritaoperacao.data.remote

import br.lavstaritaoperacao.domain.model.Service

interface RemoteRepository {
    suspend fun getAllServicesRemote(): List<Service>
}