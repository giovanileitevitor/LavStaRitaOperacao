package br.lavstaritaoperacao.domain.usecase

import br.lavstaritaoperacao.data.supabase.NotesRepository
import br.lavstaritaoperacao.domain.model.Service2

class GetServiceUseCase(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(): Result<List<Service2>> {
        return notesRepository.getServices()
    }
}

class InsertServiceUseCase(
    private val notesRepository: NotesRepository
){
    suspend operator fun invoke(service: Service2): Result<Service2> {
        return notesRepository.insertService(service)
    }

}