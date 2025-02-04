package br.lavstaritaoperacao.data.db

import br.lavstaritaoperacao.data.db.dao.ItemDao
import br.lavstaritaoperacao.data.db.dao.ServiceDao
import br.lavstaritaoperacao.data.db.entities.ItemEntity
import br.lavstaritaoperacao.data.db.entities.ServiceEntity
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service

class LocalRepositoryImpl(
    private val itemDao: ItemDao,
    private val serviceDao: ServiceDao
): LocalRepository {

    override suspend fun insertItem(item: Item) {
        itemDao.insertItem(itemEntity = convertItem(item = item))
    }

    override suspend fun getItems(): List<Item> {
        return convertItemEntityIntoItem(items = itemDao.getAllItems())
    }

    override suspend fun getServices(): List<Service> {
        return convertServiceEntityIntoService(services = serviceDao.getAllServices())
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

    private fun convertServiceEntityIntoService(services: List<ServiceEntity>) : List<Service>{
        val serviceList = mutableListOf<Service>()
        services.forEach {
            serviceList.add(
                Service(
                    id = it.id ?: 0,
                    qtd = it.totalItems ?: 0,
                    client = it.clientName,
                    dataIn = "10 / Jan / 2025"
                )
            )
        }
        return serviceList
    }
}
