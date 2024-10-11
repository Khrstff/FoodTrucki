package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.PedidoEntity

@Dao
interface PedidoDAO {
    @Query("SELECT * FROM pedido")
    suspend fun obtenerPedidos(): PedidoEntity?

    @Query("SELECT * FROM pedido WHERE status = :status")
    fun obtenerPedidoPorEstado(status: String): Flow<List<PedidoEntity?>>

    @Query("SELECT * FROM pedido WHERE status = :status and idCliente = :correo")
    fun obtenerPedidoPorEstadoYCliente(status: String, correo: String): Flow<List<PedidoEntity?>>

    @Query("SELECT * FROM pedido WHERE idCliente = :correo")
    fun obtenerPedidoPorCliente(correo: String): Flow<List<PedidoEntity?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPedido(pedidoEntity: PedidoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPedidos(pedidoEntity: List<PedidoEntity>): List<Long>

    @Update
    suspend fun actualizarPedido(pedidoEntity: PedidoEntity) : Int

}