package br.lavstaritaoperacao.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemTB")
data class ItemEntity(

    @ColumnInfo(name = "itemId")
    @PrimaryKey(autoGenerate = true)
    val itemId: Int? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "qtd")
    val qtd: Int? = 0,

    @ColumnInfo(name = "serviceId")
    val serviceId: Int? = 0,

    @ColumnInfo(name = "obs")
    val obs: String? = ""
)