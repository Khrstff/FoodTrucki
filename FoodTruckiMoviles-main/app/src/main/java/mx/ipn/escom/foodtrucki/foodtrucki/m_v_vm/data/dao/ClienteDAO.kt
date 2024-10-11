package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ClienteEntity

@Dao
interface ClienteDAO {
    @Query("SELECT * FROM cliente WHERE correo = :correo AND contrasenia = :contrasenia")
    suspend fun obtenerClientePorContrasenia(correo: String, contrasenia: String): ClienteEntity?

    @Query("SELECT * FROM cliente")
    suspend fun obtenerClientes(): List<ClienteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCliente(usuarioEntity: ClienteEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarClientes(listaClientes: List<ClienteEntity>): List<Long>

    //Tambien existe update, nada más le mandas el entity y lo actualiza con los datos
    /*@Update
    suspend fun actualizarEjemplo(usuarioEntity: UsuarioEntity) : Int*/

}