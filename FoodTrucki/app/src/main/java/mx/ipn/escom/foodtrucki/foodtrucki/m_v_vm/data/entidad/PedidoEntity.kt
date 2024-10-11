package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pedido"
)
data class PedidoEntity(
    @ColumnInfo(name = "idCliente") val idCliente: String = "",
    @ColumnInfo(name = "idVendedor") val idVendedor: String = "",
    @ColumnInfo(name = "idProducto") val idProducto: String = "",
    @ColumnInfo(name = "ubicacionEntrega") val ubicacionEntrega: String = "",
    @ColumnInfo(name = "costoTotal") val costoTotal: String = "",
    @ColumnInfo(name = "cantidadProductos") val cantidadProductos: String = "",
    @ColumnInfo(name = "indicaciones") val indicaciones: String = "",
    @ColumnInfo(name = "tipoEnvio") var tipoEnvio: String = "",
    @ColumnInfo(name = "fechaYHoraPedidoRealizado") val fechaYHoraPedidoRealizado: String = "",
    @ColumnInfo(name = "status") var status: String = "",
    @ColumnInfo(name = "descripcion") val descripcion: String = "",
    @PrimaryKey val idPedido: String = idCliente + idProducto + fechaYHoraPedidoRealizado,
)