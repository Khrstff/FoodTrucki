package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.dao.ClienteDAO
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.dao.PedidoDAO
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.dao.ProductoDAO
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.dao.VendedorDAO
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ClienteEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.PedidoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ProductoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.VendedorEntity

@Database(
    entities = [ClienteEntity::class,PedidoEntity::class,ProductoEntity::class,VendedorEntity::class],
    version = 5, exportSchema = false
    /*Para agregar mas entity
    * entities = [EjemploEntity::class,EjemploEntity2::class... EjemploEntityx::class], version = 1, exportSchema = false
    * Si se cambia algún campo en la base y no corre la app, se soluciona desinstalando o cambiando la versión de aquí
    * a algún número superior o inferior
    * */
)

abstract class BaseDeDatosFoodTrucki : RoomDatabase() {
    abstract fun clienteDAO(): ClienteDAO
    abstract fun pedidoDAO(): PedidoDAO
    abstract fun productoDAO(): ProductoDAO
    abstract fun vendedorDAO(): VendedorDAO


    /* Para conectar los entity con la base
    *
    * abstract fun ejemploDAO2(): EjemploDAO2
    *
    * abstract fun ejemploDAOx(): EjemploDAOx
    *
    * No es necesario que tenga el mismo nombre el metodo que el DAO
    * */


    companion object {
        @Volatile
        private var inictancia: BaseDeDatosFoodTrucki? = null

        fun obtenerBase(context: Context): BaseDeDatosFoodTrucki {
            return inictancia ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDeDatosFoodTrucki::class.java,
                    "EjemploDB"
                ).fallbackToDestructiveMigration()
                    .build()
                inictancia = instance
                instance
            }
        }
    }
}