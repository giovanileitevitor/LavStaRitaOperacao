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

    override suspend fun getNextGroupId(): Int {
        return localRepository.getNextGroupId()
    }

    override suspend fun getALLServices(): List<Service> {
        return localRepository.getALLServices()
    }

    override suspend fun addService(service: Service) {
        localRepository.addService(service = service)
    }

    override suspend fun deleteService(service: Service) {
        localRepository.deleteService(service = service)
    }

    override suspend fun deleteAllServices() {
        localRepository.deleteAllServices()
    }

    override suspend fun getItemsByServiceId(id: Int): List<Item> {
        return localRepository.getItemsByServiceId(id = id)
    }

    override suspend fun addItem(item: Item) {
        localRepository.addItem(item = item)
    }

    override suspend fun deleteItem(item: Item) {
        localRepository.deleteItem(item = item)
    }

    override suspend fun deleteAllItems() {
        localRepository.deleteAllItems()
    }

    override suspend fun getAllItems(): List<Item> {
        return localRepository.getAllItems()
    }
}