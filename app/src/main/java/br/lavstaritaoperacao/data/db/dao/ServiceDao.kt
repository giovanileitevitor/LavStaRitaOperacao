package br.lavstaritaoperacao.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.lavstaritaoperacao.data.db.entities.ServiceEntity
import br.lavstaritaoperacao.domain.model.StatusPayment
import br.lavstaritaoperacao.domain.model.StatusService

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addService(serviceEntity: ServiceEntity)

    @Query("SELECT * FROM serviceTB")
    suspend fun getAllServices(): List<ServiceEntity>

    @Query("DELETE FROM serviceTB WHERE serviceId = :serviceId")
    suspend fun deleteService(serviceId: Int)

    @Query("UPDATE serviceTB SET qtdItems = :qtdItems, statusService = :statusService, statusPayment = :statusPayment, obs = :obs , price = :price WHERE serviceId = :serviceId")
    suspend fun updateService(
        serviceId: Int,
        qtdItems: Int,
        statusService: StatusService,
        statusPayment: StatusPayment,
        obs: String,
        price: String
    )

    @Query("SELECT MAX(serviceId) + 1 FROM serviceTB")
    suspend fun getNextServiceId(): Int?

    @Query("DELETE FROM serviceTB")
    suspend fun deleteAllServices()

}