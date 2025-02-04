package br.lavstaritaoperacao.domain.usecase

import br.lavstaritaoperacao.data.db.LocalRepository
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.generateServices
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

    override suspend fun createService(service: Service): Boolean {
        return Random.nextBoolean()
    }

    override suspend fun addItem(item: Item) {
        localRepository.insertItem(item = item)
    }

    override suspend fun getItems(): List<Item> {
        return localRepository.getItems()
    }
}