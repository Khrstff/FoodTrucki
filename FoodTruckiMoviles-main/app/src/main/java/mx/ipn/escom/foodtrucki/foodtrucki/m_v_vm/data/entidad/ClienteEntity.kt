package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "cliente"
)
data class ClienteEntity(
    //Su nombre de campo esta dado por el name y en caso de la llave primaria esta dado por el val usuario
    //Clave primaria
    @PrimaryKey val correo: String,
    //columnas de tabla
    @ColumnInfo(name = "nombre") val nombre: String = "",
    @ColumnInfo(name = "apellido") val apellido: String = "",
    @ColumnInfo(name = "telefono") val telefono: String = "",
    @ColumnInfo(name = "contrasenia") val contrasenia: String = ""
)