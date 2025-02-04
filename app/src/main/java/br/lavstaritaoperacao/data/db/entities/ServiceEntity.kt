package br.lavstaritaoperacao.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "serviceTB")
data class ServiceEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "clientName")
    val clientName: String,

    @ColumnInfo(name = "clientPhone")
    val clientPhone: String,

    @ColumnInfo(name = "totalItems")
    val totalItems: Int,

    @ColumnInfo(name = "idGroupItems")
    val idGroupItems: Int
)
