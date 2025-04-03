package br.lavstaritaoperacao.domain.usecase

import br.lavstaritaoperacao.data.db.LocalRepository
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.LoginResult
import br.lavstaritaoperacao.domain.model.Pessoa
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.Service2
import br.lavstaritaoperacao.domain.model.UserType

class GlobalUseCaseImpl(
    private val localRepository: LocalRepository
): GlobalUseCase {
    
    override suspend fun startLogin(cpf: String): LoginResult {
        return if(cpf.isEmpty()){
            LoginResult(status = false, codeResponse = 0, userType = UserType.ADMIN)
        } else if(cpf == "555.555.555-55"){
            LoginResult(status = true, codeResponse = 1, userType = UserType.ADMIN)
        } else if(cpf == "111.111.111-11"){
            LoginResult(status = true, codeResponse = 1, userType = UserType.CLIENT)
        } else {
            LoginResult(status = false, codeResponse = 0, userType = UserType.ADMIN)
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

    override suspend fun getItemsByServiceId(serviceId: Int): List<Item> {
        return localRepository.getItemsByServiceId(serviceId = serviceId)
    }

    override suspend fun addItem(item: Item) {
        localRepository.addItem(item = item)
    }

    override suspend fun deleteItem(item: Item) {
        localRepository.deleteItem(item = item)
    }

    override suspend fun deleteItemByServiceId(serviceId: Int) {
        localRepository.deleteItemByServiceId(serviceId = serviceId)
    }

    override suspend fun deleteAllItems() {
        localRepository.deleteAllItems()
    }

    override suspend fun getAllItems(): List<Item> {
        return localRepository.getAllItems()
    }

    override suspend fun updateService(service: Service) {
        localRepository.updateService(service = service)
    }

    override suspend fun getAllServicesRemote(): List<Service2> {
        return emptyList<Service2>()
    }

    override suspend fun salvarPessoaUseCase(pessoa: Pessoa) {
        //firestoreRepository.salvarPessoa(pessoa = pessoa)
    }

    override suspend fun obterPessoasUseCase(): List<Pessoa> {
        //return firestoreRepository.obterPessoas()
        return emptyList()
    }
}