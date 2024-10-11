package mx.ipn.escom.foodtrucki.foodtrucki

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.BaseDeDatosFoodTrucki
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ClienteEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.PedidoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ProductoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.VendedorEntity

class MainActivityRepository (application: Application) {

    private var clienteDAO = BaseDeDatosFoodTrucki.obtenerBase(application).clienteDAO()
    private var vendedorDAO = BaseDeDatosFoodTrucki.obtenerBase(application).vendedorDAO()
    private var productoDAO = BaseDeDatosFoodTrucki.obtenerBase(application).productoDAO()
    private var pedidoDAO = BaseDeDatosFoodTrucki.obtenerBase(application).pedidoDAO()
    private var baseDeDatosFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun obtenerClientes(): List<ClienteEntity?> {
        return clienteDAO.obtenerClientes()
    }

    suspend fun obtenerCliente(correo: String, contra: String): ClienteEntity? {
        return clienteDAO.obtenerClientePorContrasenia(correo = correo, contrasenia = contra)
    }

    suspend fun obtenerProductos(): Flow<List<ProductoEntity?>> {
        return productoDAO.obtenerProductos()
    }

    suspend fun obtenerVendedor(correo: String, contra: String): VendedorEntity? {
        return vendedorDAO.obtenerUltimoVendedor(correo, contra)
    }

    suspend fun insertarCliente(cliente: ClienteEntity): Long {
        return clienteDAO.insertarCliente(cliente)
    }

    private suspend fun insertarClientes(listaClientes: List<ClienteEntity>): List<Long> {
        return clienteDAO.insertarClientes(listaClientes)
    }

    private suspend fun insertarVendedores(listaVendedores: List<VendedorEntity>): List<Long> {
        return vendedorDAO.insertarVendedores(listaVendedores)
    }

    private suspend fun insertarProductos(listaProductos: List<ProductoEntity>): List<Long> {
        return productoDAO.insertarProductos(listaProductos)
    }

    private suspend fun insertarPedidos(listaProductos: List<PedidoEntity>): List<Long> {
        return pedidoDAO.insertarPedidos(listaProductos)
    }

    fun obtenerRegistros() {
        val listaClientes = ArrayList<ClienteEntity>()

        //Para obtener todos los usuarios
        baseDeDatosFirestore.collection("Clientes")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val clientePorDocumento = ClienteEntity(
                        correo = document.get("correo") as String,
                        nombre = document.get("nombre") as String,
                        apellido = document.get("apellidos") as String,
                        telefono = document.get("telefono") as String,
                        contrasenia = document.get("contra") as String,
                    )
                    listaClientes.add(clientePorDocumento)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    insertarClientes(listaClientes)
                }
            }
    }

    fun obtenerRegistrosVendedores() {
        val listaVendedores = ArrayList<VendedorEntity>()

        //Para obtener todos los usuarios
        baseDeDatosFirestore.collection("Vendedores")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val vendedorPorDocumento = VendedorEntity(
                        correo = document.get("correo") as String,
                        nombre = document.get("nombre") as String,
                        coordenadas = document.get("coordenadas") as String,
                        telefono = document.get("telefono") as String,
                        contrasenia = document.get("password") as String,
                    )
                    listaVendedores.add(vendedorPorDocumento)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    insertarVendedores(listaVendedores)
                }
            }
    }

    fun obtenerRegistrosProductos(vendedorEntity: VendedorEntity) {
        val listaProductos = ArrayList<ProductoEntity>()

        //Para obtener todos los usuarios
        baseDeDatosFirestore.collection("Productos")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val productoPorDocumento = ProductoEntity(
                        nombreProducto = document.get("nombre") as String,
                        descripcion = document.get("descripcion") as String,
                        tipo = document.get("tipo") as String,
                        costo = document.get("costo") as String,
                        idVendedor = vendedorEntity.correo
                    )
                    listaProductos.add(productoPorDocumento)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    insertarProductos(listaProductos)
                }
            }
    }

    fun enviarInfoClientes(clienteEntity: ClienteEntity) {
        baseDeDatosFirestore.collection("Clientes")
            .document(clienteEntity.correo).set(
                hashMapOf(
                    "nombre" to clienteEntity.nombre,
                    "apellidos" to clienteEntity.apellido,
                    "correo" to clienteEntity.correo,
                    "contra" to clienteEntity.contrasenia,
                    "telefono" to clienteEntity.telefono
                )
            )
    }

    suspend fun insertarProducto(productoEntity: ProductoEntity): Long {
        return productoDAO.insertarProducto(productoEntity)
    }

    suspend fun obtenerVendedor(): VendedorEntity? {
        return vendedorDAO.obtenerVendedor()
    }

    fun enviarInfoProductos(productoEntity: ProductoEntity) {
        baseDeDatosFirestore.collection("Productos")
            .document(productoEntity.idProducto).set(
                hashMapOf(
                    "nombre" to productoEntity.nombreProducto,
                    "descripcion" to productoEntity.descripcion,
                    "tipo" to productoEntity.tipo,
                    "costo" to productoEntity.costo,
                    "idProducto" to productoEntity.idProducto
                )
            )
    }

    fun eliminarInfoProductos(productoEntity: ProductoEntity) {
        baseDeDatosFirestore.collection("Productos")
            .document(productoEntity.idProducto)
            .delete()
            .addOnSuccessListener {
                println("Documento eliminado correctamente.")
            }
            .addOnFailureListener { e ->
                println("Error al eliminar el documento: $e")
            }

    }

    fun enviarInfoPedidos(pedidoEntity: PedidoEntity) {
        baseDeDatosFirestore.collection("Pedidos")
            .document(pedidoEntity.idPedido).set(
                hashMapOf(
                    "idCliente" to pedidoEntity.idCliente,
                    "idVendedor" to pedidoEntity.idVendedor,
                    "idProducto" to pedidoEntity.idProducto,
                    "ubicacionEntrega" to pedidoEntity.ubicacionEntrega,
                    "costoTotal" to pedidoEntity.costoTotal,
                    "cantidadProductos" to pedidoEntity.cantidadProductos,
                    "indicaciones" to pedidoEntity.indicaciones,
                    "tipoEnvio" to pedidoEntity.tipoEnvio,
                    "fechaYHoraPedidoRealizado" to pedidoEntity.fechaYHoraPedidoRealizado,
                    "status" to pedidoEntity.status,
                    "descripcion" to pedidoEntity.descripcion,
                    "idPedido" to pedidoEntity.idPedido
                )
            )
    }

    fun obtenerRegistrosPedidos() {
        val listaPedidos = ArrayList<PedidoEntity>()

        //Para obtener todos los usuarios
        baseDeDatosFirestore.collection("Pedidos")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val productoPorDocumento = PedidoEntity(
                        idCliente = document.get("idCliente") as String,
                        idVendedor = document.get("idVendedor") as String,
                        idProducto = document.get("idProducto") as String,
                        ubicacionEntrega = document.get("ubicacionEntrega") as String,
                        costoTotal = document.get("costoTotal") as String,
                        cantidadProductos = document.get("cantidadProductos") as String,
                        descripcion = document.get("descripcion") as String,
                        indicaciones = document.get("indicaciones") as String,
                        tipoEnvio = document.get("tipoEnvio") as String,
                        fechaYHoraPedidoRealizado = document.get("fechaYHoraPedidoRealizado") as String,
                        status = document.get("status") as String,
                        idPedido = document.get("idPedido") as String
                    )
                    listaPedidos.add(productoPorDocumento)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    insertarPedidos(listaPedidos)
                }
            }
    }

    fun actualizarInfoPedido(pedidoEntity: PedidoEntity) {
        baseDeDatosFirestore.collection("Pedidos")
            .document(pedidoEntity.idPedido).set(
                hashMapOf(
                    "tipoEnvio" to pedidoEntity.tipoEnvio,
                    "status" to pedidoEntity.status
                ),
                SetOptions.merge()
            )
    }
}