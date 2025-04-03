package br.lavstaritaoperacao.data.supabase

import br.lavstaritaoperacao.domain.model.Service2

interface NotesRepository {
    suspend fun getServices(): Result<List<Service2>>
    suspend fun insertService(service: Service2): Result<Service2>
}