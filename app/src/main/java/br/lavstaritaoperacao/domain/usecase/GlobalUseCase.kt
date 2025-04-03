package br.lavstaritaoperacao.domain.usecase

import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.LoginResult
import br.lavstaritaoperacao.domain.model.Pessoa
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.Service2

interface GlobalUseCase {
    suspend fun startLogin(cpf: String): LoginResult
    suspend fun getNextGroupId(): Int
    suspend fun getALLServices(): List<Service>
    suspend fun addService(service: Service)
    suspend fun deleteService(service: Service)
    suspend fun deleteAllServices()
    suspend fun deleteAllItems()
    suspend fun getItemsByServiceId(serviceId: Int): List<Item>
    suspend fun addItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun deleteItemByServiceId(serviceId: Int)
    suspend fun getAllItems(): List<Item>
    suspend fun updateService(service: Service)

    suspend fun getAllServicesRemote(): List<Service2>
    suspend fun salvarPessoaUseCase(pessoa: Pessoa)
    suspend fun obterPessoasUseCase(): List<Pessoa>

}