package br.lavstaritaoperacao.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.lavstaritaoperacao.data.db.entities.ServiceEntity
import br.lavstaritaoperacao.domain.model.Service

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addService(serviceEntity: ServiceEntity)

    @Query("SELECT * FROM serviceTB")
    suspend fun getAllServices(): List<ServiceEntity>

    @Query("DELETE FROM serviceTB WHERE id = :serviceId")
    suspend fun deleteService(serviceId: Int)

}