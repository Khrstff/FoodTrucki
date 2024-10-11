package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ClienteEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ProductoEntity

@Dao
interface ProductoDAO {
    @Query("SELECT * FROM cliente WHERE correo = :correo AND contrasenia = :contrasenia")
    suspend fun obtenerProductosPorId(correo: String, contrasenia: String): ClienteEntity?

    @Query("SELECT * FROM producto")
    fun obtenerProductos(): Flow<List<ProductoEntity?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(usuarioEntity: ProductoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProductos(listaProductos: List<ProductoEntity>): List<Long>

    //Tambien existe update, nada más le mandas el entity y lo actualiza con los datos
    /*@Update
    suspend fun actualizarEjemplo(usuarioEntity: UsuarioEntity) : Int*/

    @Delete
    suspend fun eliminarProducto(productoEntity: ProductoEntity)

}