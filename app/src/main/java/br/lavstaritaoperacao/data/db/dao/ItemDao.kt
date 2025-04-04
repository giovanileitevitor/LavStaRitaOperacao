package br.lavstaritaoperacao.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.lavstaritaoperacao.data.db.entities.ItemEntity

@Dao
interface ItemDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(itemEntity: ItemEntity)

    @Query("SELECT * FROM itemTB")
    suspend fun getAllItems(): List<ItemEntity>

    @Query("SELECT * FROM itemTB WHERE  serviceId = :serviceId")
    suspend fun getAllItemsByServiceId(serviceId: Int): List<ItemEntity>

    @Query("SELECT * FROM itemTB WHERE itemId = :id")
    suspend fun getItemsfromDB(id: Int): List<ItemEntity>

    @Query("DELETE FROM itemTB WHERE itemId = :itemId")
    suspend fun deleteItem(itemId: Int)

    @Query("DELETE FROM itemTB WHERE serviceId = :serviceId")
    suspend fun deleteItemByServiceId(serviceId: Int)

    @Query("UPDATE itemTB SET name = :name, qtd = :qtd WHERE itemId = :itemId")
    suspend fun updateItems(itemId : Int, name: String, qtd: Int)

    @Query("DELETE FROM itemTB ")
    suspend fun deleteAllItems()

//    @Query("DELETE FROM itemTB WHERE isTemporary = :isTemporary")
//    fun deleteAllTemporarySteps(isTemporary: Boolean)
//
//
//    @Query("UPDATE stepTB SET isTemporary = :removeTemporaryFlag WHERE idReceita = :idReceita")
//    fun removeTemporaryFlagStep(idReceita: Int, removeTemporaryFlag: Boolean)
//
//    @Query("DELETE FROM stepTB WHERE idReceita = :idReceita")
//    suspend fun deleteAllStepsFromIdReceita(idReceita: Int)

}