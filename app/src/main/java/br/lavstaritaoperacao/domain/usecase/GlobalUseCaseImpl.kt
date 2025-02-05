package br.lavstaritaoperacao.domain.usecase

import br.lavstaritaoperacao.data.db.LocalRepository
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import kotlin.random.Random

class GlobalUseCaseImpl(
    private val localRepository: LocalRepository
): GlobalUseCase {
    override suspend fun startLogin(cpf: String): Boolean {
        if(cpf.isEmpty()){
            return false
        } else{
            return Random.nextBoolean()
        }
    }

    override suspend fun getServices(): List<Service> {
        return localRepository.getServices()
    }

    override suspend fun addService(service: Service) {
        localRepository.addService(service = service)
    }

    override suspend fun deleteService(service: Service) {
        localRepository.deleteService(service = service)
    }

    override suspend fun getItems(): List<Item> {
        return localRepository.getItems()
    }

    override suspend fun addItem(item: Item) {
        localRepository.addItem(item = item)
    }
}