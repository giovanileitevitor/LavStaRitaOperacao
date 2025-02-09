package br.lavstaritaoperacao.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.lavstaritaoperacao.data.db.entities.ServiceEntity

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addService(serviceEntity: ServiceEntity)

    @Query("SELECT * FROM serviceTB")
    suspend fun getAllServices(): List<ServiceEntity>

    @Query("DELETE FROM serviceTB WHERE serviceId = :serviceId")
    suspend fun deleteService(serviceId: Int)

    @Query("UPDATE serviceTB SET qtdItems = :qtdItems WHERE serviceId = :serviceId")
    suspend fun updateService(serviceId: Int, qtdItems: Int)

    @Query("SELECT MAX(serviceId) + 1 FROM serviceTB")
    suspend fun getNextServiceId(): Int?

    @Query("DELETE FROM serviceTB")
    suspend fun deleteAllServices()

}