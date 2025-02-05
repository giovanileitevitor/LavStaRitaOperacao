package br.lavstaritaoperacao.domain.usecase

import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service

interface GlobalUseCase {
    suspend fun startLogin(cpf: String): Boolean
    suspend fun getServices(): List<Service>
    suspend fun addService(service: Service)
    suspend fun deleteService(service: Service)
    suspend fun getItems(): List<Item>
    suspend fun addItem(item: Item)

}