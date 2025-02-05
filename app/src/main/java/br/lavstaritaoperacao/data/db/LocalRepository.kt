package br.lavstaritaoperacao.data.db

import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service

interface LocalRepository {
    suspend fun addItem(item: Item)
    suspend fun getItems(): List<Item>
    suspend fun addService(service: Service)
    suspend fun getServices(): List<Service>
    suspend fun deleteService(service: Service)
}