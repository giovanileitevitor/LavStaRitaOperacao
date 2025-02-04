package br.lavstaritaoperacao.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.lavstaritaoperacao.data.db.entities.ServiceEntity

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(serviceEntity: ServiceEntity)

    @Query("SELECT * FROM serviceTB")
    suspend fun getAllServices(): List<ServiceEntity>

}