package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "producto"
)
data class ProductoEntity(

    @ColumnInfo(name = "idVendedor") val idVendedor: String = "",
    @ColumnInfo(name = "nombreProducto") val nombreProducto: String = "",
    @ColumnInfo(name = "descripcion") val descripcion: String = "",
    @ColumnInfo(name = "tipo") val tipo: String = "",
    @ColumnInfo(name = "costo") val costo: String = "",
    @PrimaryKey val idProducto: String = idVendedor + nombreProducto
)