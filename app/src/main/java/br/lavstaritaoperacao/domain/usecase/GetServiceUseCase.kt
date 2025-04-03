package br.lavstaritaoperacao.domain.usecase

import br.lavstaritaoperacao.data.supabase.ServiceRepository
import br.lavstaritaoperacao.domain.model.Service2


class ServiceUseCase(
    private val serviceRepository: ServiceRepository
) {
    suspend fun getServices(): Result<List<Service2>> {
        return serviceRepository.getServices()
    }

    suspend fun insertService(service: Service2): Result<Service2> {
        return serviceRepository.insertService(service)
    }
}