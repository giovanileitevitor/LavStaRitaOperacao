package br.lavstaritaoperacao.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.lavstaritaoperacao.domain.model.StatusPayment
import br.lavstaritaoperacao.domain.model.StatusService

@Entity(tableName = "serviceTB")
data class ServiceEntity(

    @ColumnInfo(name = "serviceId")
    @PrimaryKey(autoGenerate = true)
    val serviceId: Int? = null,

    @ColumnInfo(name = "clientName")
    val clientName: String,

    @ColumnInfo(name = "clientPhone")
    val clientPhone: String,

    @ColumnInfo(name = "qtdItems")
    val qtdItems: Int,

    @ColumnInfo(name = "statusService")
    val statusService: StatusService? = StatusService.EM_LAVAGEM,

    @ColumnInfo(name = "statusPayment")
    val statusPayment: StatusPayment? = StatusPayment.NOT_PAID,

    @ColumnInfo(name = "obs")
    val obs: String,

    @ColumnInfo(name = "price")
    val price: String,

    @ColumnInfo(name = "dataIn")
    val dataIn: String

)
