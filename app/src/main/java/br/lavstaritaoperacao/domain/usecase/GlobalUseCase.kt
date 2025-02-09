package br.lavstaritaoperacao.domain.usecase

import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service

interface GlobalUseCase {
    suspend fun startLogin(cpf: String): Boolean
    suspend fun getNextGroupId(): Int
    suspend fun getALLServices(): List<Service>
    suspend fun addService(service: Service)
    suspend fun deleteService(service: Service)
    suspend fun deleteAllServices()
    suspend fun deleteAllItems()
    suspend fun getItemsByServiceId(id: Int): List<Item>
    suspend fun addItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun getAllItems(): List<Item>

}