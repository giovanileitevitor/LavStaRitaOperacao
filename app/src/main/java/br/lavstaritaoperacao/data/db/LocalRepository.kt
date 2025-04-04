package br.lavstaritaoperacao.data.db

import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service

interface LocalRepository {
    suspend fun addItem(item: Item)
    suspend fun getItemsByServiceId(serviceId: Int): List<Item>
    suspend fun deleteItem(item: Item)
    suspend fun deleteItemByServiceId(serviceId: Int)
    suspend fun deleteAllItems()
    suspend fun getAllItems(): List<Item>
    suspend fun addService(service: Service)
    suspend fun getALLServices(): List<Service>
    suspend fun deleteService(service: Service)
    suspend fun deleteAllServices()
    suspend fun getNextGroupId(): Int
    suspend fun updateService(service: Service)
}