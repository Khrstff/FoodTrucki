package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.VendedorEntity

@Dao
interface VendedorDAO {

    @Query("SELECT * FROM vendedor WHERE correo = :correo AND contrasenia = :contrasenia LIMIT 1")
    suspend fun obtenerUltimoVendedor(correo: String, contrasenia: String): VendedorEntity?

    @Query("SELECT * FROM vendedor LIMIT 1")
    suspend fun obtenerVendedor(): VendedorEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVendedor(vendedorEntity: VendedorEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVendedores(listaVendedores: List<VendedorEntity>): List<Long>

    //Tambien existe update, nada más le mandas el entity y lo actualiza con los datos
    /*@Update
    suspend fun actualizarEjemplo(usuarioEntity: UsuarioEntity) : Int*/

}