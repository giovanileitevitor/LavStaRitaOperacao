package br.lavstaritaoperacao.data.db

import br.lavstaritaoperacao.data.db.dao.ItemDao
import br.lavstaritaoperacao.data.db.dao.ServiceDao
import br.lavstaritaoperacao.data.db.entities.ItemEntity
import br.lavstaritaoperacao.data.db.entities.ServiceEntity
import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service
import br.lavstaritaoperacao.domain.model.StatusPayment
import br.lavstaritaoperacao.domain.model.StatusService
import org.koin.core.component.getScopeId

class LocalRepositoryImpl(
    private val itemDao: ItemDao,
    private val serviceDao: ServiceDao
): LocalRepository {

    override suspend fun addItem(item: Item) {
        itemDao.addItem(itemEntity = convertItem(item = item))
    }

    override suspend fun getItemsByServiceId(serviceId: Int): List<Item> {
        return convertItemEntityIntoItem(items = itemDao.getAllItemsByServiceId(serviceId = serviceId))
    }

    override suspend fun deleteItem(item: Item) {
        item.id.let {
            itemDao.deleteItem(itemId = it ?: 0)
        }
    }

    override suspend fun deleteAllItems() {
        itemDao.deleteAllItems()
    }

    override suspend fun getAllItems(): List<Item> {
        return convertItemEntityIntoItem(items = itemDao.getAllItems())
    }

    override suspend fun addService(service: Service) {
        serviceDao.addService(serviceEntity = convertService(service = service))
    }

    override suspend fun getALLServices(): List<Service> {
        return convertServiceEntityIntoService(services = serviceDao.getAllServices())
    }

    override suspend fun deleteService(service: Service) {
        serviceDao.deleteService(serviceId = convertService(service = service).serviceId ?: 0)
    }

    override suspend fun deleteAllServices() {
        serviceDao.deleteAllServices()
    }

    override suspend fun getNextGroupId(): Int {
        return serviceDao.getNextServiceId() ?: 1
    }

    override suspend fun updateService(service: Service) {
        serviceDao.updateService(
            serviceId = service.serviceId ?: 0,
            qtdItems = service.qtdItems ?: 0,
            statusService = service.statusService ?: StatusService.EM_LAVAGEM,
            statusPayment = service.statusPayment ?: StatusPayment.NOT_PAID,
            obs = service.obs ?: "-",
            price = service.price
            )
    }

    private fun convertItem(item: Item) : ItemEntity{
        return ItemEntity(
            name = item.name,
            qtd = item.qtd,
            serviceId = item.serviceId,
            obs = item.obs
        )
    }

    private fun convertItemEntityIntoItem(items: List<ItemEntity>) : List<Item>{
        val itemList = mutableListOf<Item>()
        items.forEach {
            itemList.add(
                Item(
                    id = it.itemId,
                    name = it.name,
                    qtd = it.qtd ?: 0,
                    serviceId = it.serviceId,
                    obs = it.obs ?: ""
                )
            )
        }
        return itemList
    }

    private fun convertService(service: Service): ServiceEntity{
        return ServiceEntity(
            serviceId = service.serviceId,
            clientName = service.clientName,
            clientPhone = service.clientPhone,
            qtdItems = service.qtdItems,
            statusService = service.statusService ?: StatusService.EM_LAVAGEM,
            statusPayment = service.statusPayment ?: StatusPayment.NOT_PAID,
            obs = service.obs ?: "-",
            dataIn = service.dataIn,
            price = service.price
        )
    }

    private fun convertServiceEntityIntoService(services: List<ServiceEntity>) : List<Service>{
        val serviceList = mutableListOf<Service>()
        services.forEach {
            serviceList.add(
                Service(
                    serviceId = it.serviceId ?: 0,
                    clientName = it.clientName,
                    clientPhone = it.clientPhone,
                    qtdItems = it.qtdItems,
                    statusService = it.statusService,
                    obs = it.obs,
                    dataIn = it.dataIn,
                    price = it.price
                )
            )
        }
        return serviceList
    }

    private fun convertToStatusService(statusService: String): StatusService{
        return when(statusService){
            "Concluído" -> StatusService.DONE
            "Em Lavagem" -> StatusService.EM_LAVAGEM
            "Em Secagem" -> StatusService.EM_SECAGEM
            "Em Passagem" -> StatusService.EM_PASSAGEM
            else -> StatusService.OTHER
        }
    }
}
