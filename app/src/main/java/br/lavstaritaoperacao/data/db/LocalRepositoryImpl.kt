package br.lavstaritaoperacao.data.db

import br.lavstaritaoperacao.data.db.dao.ItemDao
import br.lavstaritaoperacao.data.db.dao.ServiceDao
import br.lavstaritaoperacao.data.db.entities.ItemEntity
import br.lavstaritaoperacao.data.db.entities.ServiceEntity
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import org.koin.core.component.getScopeId

class LocalRepositoryImpl(
    private val itemDao: ItemDao,
    private val serviceDao: ServiceDao
): LocalRepository {

    override suspend fun addItem(item: Item) {
        itemDao.addItem(itemEntity = convertItem(item = item))
    }

    override suspend fun getItems(): List<Item> {
        return convertItemEntityIntoItem(items = itemDao.getAllItems())
    }

    override suspend fun addService(service: Service) {
        serviceDao.addService(serviceEntity = convertService(service = service))
    }

    override suspend fun getServices(): List<Service> {
        return convertServiceEntityIntoService(services = serviceDao.getAllServices())
    }

    override suspend fun deleteService(service: Service) {
        serviceDao.deleteService(serviceId = convertService(service = service).id ?: 0)
    }

    private fun convertItem(item: Item) : ItemEntity{
        return ItemEntity(
            name = item.name,
            qtd = item.qtd
        )
    }

    private fun convertItemEntityIntoItem(items: List<ItemEntity>) : List<Item>{
        val itemList = mutableListOf<Item>()
        items.forEach {
            itemList.add(
                Item(
                    id = it.id ?: 0,
                    name = it.name,
                    qtd = it.qtd ?: 0
                )
            )
        }
        return itemList
    }

    private fun convertService(service: Service): ServiceEntity{
        return ServiceEntity(
            id = service.id ?: 0,
            clientName = service.clientName,
            clientPhone = service.clientPhone,
            idItems = service.idItems,
            qtdItems = service.qtdItems,
            dataIn = service.dataIn
        )
    }

    private fun convertServiceEntityIntoService(services: List<ServiceEntity>) : List<Service>{
        val serviceList = mutableListOf<Service>()
        services.forEach {
            serviceList.add(
                Service(
                    id = it.id ?: 0,
                    clientName = it.clientName,
                    clientPhone = it.clientPhone,
                    qtdItems = it.qtdItems,
                    idItems = it.idItems,
                    dataIn = it.dataIn
                )
            )
        }
        return serviceList
    }
}
