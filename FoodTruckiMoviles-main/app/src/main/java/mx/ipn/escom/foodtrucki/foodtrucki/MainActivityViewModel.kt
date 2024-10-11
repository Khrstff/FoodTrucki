package mx.ipn.escom.foodtrucki.foodtrucki

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.BaseDeDatosFoodTrucki
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ClienteEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.PedidoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ProductoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.states.PedidosStates
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.states.ProductosStates

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainActivityRepository(application)

    val ultimoClienteIngresado: MutableLiveData<ClienteEntity> = MutableLiveData()
    private var ultimoCorreo: String = ""

    fun establecerUltimoClienteIngresado(cliente: String, contra: String) {
        ultimoCorreo = cliente
        CoroutineScope(Dispatchers.IO).launch {
            ultimoClienteIngresado.postValue(repository.obtenerCliente(cliente, contra))
        }
    }

    /*
        Todo para pantalla splash (Cuando carga la app)
     */

    fun insertarListaDeClientes() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.obtenerRegistros()
        }
    }

    fun insertarListaDeVendedores() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.obtenerRegistrosVendedores()
        }
    }

    fun insertarListaPedidos() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.obtenerRegistrosPedidos()
        }
    }

    fun insertarListaProductos() {
        CoroutineScope(Dispatchers.IO).launch {
            val vendedorEntity = repository.obtenerVendedor()
            repository.obtenerRegistrosProductos(vendedorEntity!!)
        }
    }


    /*
        Todo para login
     */

    private val _correo = MutableLiveData<String>()
    val correo: LiveData<String> = _correo

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _esUsuarioCorrecto = MutableLiveData<Boolean>()
    val esUsuarioCorrecto: LiveData<Boolean> = _esUsuarioCorrecto

    fun onLoginChanged(correo: String, contra: String) {
        _correo.value = correo
        _password.value = contra
    }

    fun validarUsuario (correo: String, contra: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val cliente = repository.obtenerCliente(correo, contra)
            val vendedor = repository.obtenerVendedor(correo, contra)
            _esUsuarioCorrecto.postValue(cliente != null || vendedor != null)
        }
    }


    /*
        Todo para pantalla registro cliente
     */

    private val _nombreRegistroCliente = MutableLiveData<String>()
    val nombreRegistroCliente: LiveData<String> = _nombreRegistroCliente

    private val _apellidoRegistroCliente = MutableLiveData<String>()
    val apellidoRegistroCliente: LiveData<String> = _apellidoRegistroCliente

    private val _correoRegistroCliente = MutableLiveData<String>()
    val correoRegistroCliente: LiveData<String> = _correoRegistroCliente

    private val _passwordRegistroCliente = MutableLiveData<String>()
    val passwordRegistroCliente: LiveData<String> = _passwordRegistroCliente

    private val _confirmarPasswordRegistroCliente = MutableLiveData<String>()
    val confirmarPasswordRegistroCliente: LiveData<String> = _confirmarPasswordRegistroCliente

    private val _telefonoRegistroCliente = MutableLiveData<String>()
    val telefonoRegistroCliente: LiveData<String> = _telefonoRegistroCliente

    fun onRegistroClienteChanged(nombre: String, apellido: String, correo: String, contra: String,
                                 confirmContra: String, telefono: String) {
        _nombreRegistroCliente.value = nombre
        _apellidoRegistroCliente.value = apellido
        _correoRegistroCliente.value = correo
        _passwordRegistroCliente.value = contra
        _confirmarPasswordRegistroCliente.value = confirmContra
        _telefonoRegistroCliente.value = telefono
    }

    fun insertarCliente (nombre: String, apellido: String, correo: String, contra: String,
                         telefono: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val clienteEntity = ClienteEntity(
                nombre = nombre,
                apellido = apellido,
                correo = correo,
                contrasenia = contra,
                telefono = telefono
            )
            repository.insertarCliente(clienteEntity)
            repository.enviarInfoClientes(clienteEntity)
        }
    }


    /*
        Todo para pantalla registro producto
     */

    private val _nombreProducto = MutableLiveData<String>()
    val nombreProducto: LiveData<String> = _nombreProducto

    private val _descripcionProducto = MutableLiveData<String>()
    val descripcionProducto: LiveData<String> = _descripcionProducto

    private val _costoProducto = MutableLiveData<String>()
    val costoProducto: LiveData<String> = _costoProducto

    private val _tipoProducto = MutableLiveData<String>()
    val tipoProducto: LiveData<String> = _tipoProducto

    fun onRegistroProductoChanged(nombre: String, descripcion: String, costo: String, tipo: String) {
        _nombreProducto.value = nombre
        _descripcionProducto.value = descripcion
        _costoProducto.value = costo
        _tipoProducto.value = tipo
    }

    fun insertarProducto(nombre: String, descripcion: String, costo: String, tipo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val obtenerVendedor = repository.obtenerVendedor()
            val productoEntity = ProductoEntity(
                idVendedor = obtenerVendedor!!.correo,
                nombreProducto = nombre,
                descripcion = descripcion,
                tipo = tipo,
                costo = costo
            )
            repository.insertarProducto(productoEntity)
            repository.enviarInfoProductos(productoEntity)
        }
    }

    /*
        Todo para pantalla principal
     */

    private var productoDAO = BaseDeDatosFoodTrucki.obtenerBase(application).productoDAO()

    var listaPro by mutableStateOf(ProductosStates())
        private set

    init {
        viewModelScope.launch {
            productoDAO.obtenerProductos().collectLatest {
                listaPro = listaPro.copy(listaProductos = it)
            }

        }
    }

    var listaPedido by mutableStateOf(PedidosStates())
        private set

    init {
        viewModelScope.launch {
            print(ultimoCorreo)
            if (ultimoCorreo != "") {
                pedidoDAO.obtenerPedidoPorCliente(ultimoCorreo).collectLatest {
                    listaPedido = listaPedido.copy(listaPedidos = it)
                }
            }

        }
    }

    fun eliminarProducto(productoEntity: ProductoEntity) = viewModelScope.launch {
        productoDAO.eliminarProducto(productoEntity)
        repository.eliminarInfoProductos(productoEntity)
    }

    fun agregarPedido(productoEntity: ProductoEntity) = viewModelScope.launch {
        val vendedorEntity = vendedorDAO.obtenerVendedor()
        val pedidoEntity = PedidoEntity(
            idVendedor = vendedorEntity!!.correo,
            idCliente = ultimoClienteIngresado.value!!.correo,
            idProducto = productoEntity.idProducto,
            ubicacionEntrega = "19.505, -99.14666667",
            costoTotal = productoEntity.costo,
            cantidadProductos = "1",
            indicaciones = "Entregar en el lugar exacto",
            fechaYHoraPedidoRealizado = System.currentTimeMillis().toString(),
            status = "PE",
            descripcion = productoEntity.descripcion,
        )
        pedidoDAO.insertarPedido(pedidoEntity)
        repository.enviarInfoPedidos(pedidoEntity)
    }

    /*
    *   Todo para pantalla listado pedidos
    * */

    private var pedidoDAO = BaseDeDatosFoodTrucki.obtenerBase(application).pedidoDAO()
    private var vendedorDAO = BaseDeDatosFoodTrucki.obtenerBase(application).vendedorDAO()
    var listaPedidosTerminados by mutableStateOf(PedidosStates())
        private set

    init {
        viewModelScope.launch {
            pedidoDAO.obtenerPedidoPorEstado("TE").collectLatest {
                listaPedidosTerminados = listaPedidosTerminados.copy(listaPedidos = it)
            }

        }
    }

    var listaPedidosPendientes by mutableStateOf(PedidosStates())
        private set

    init {
        viewModelScope.launch {
            pedidoDAO.obtenerPedidoPorEstado("PE").collectLatest {
                listaPedidosPendientes = listaPedidosPendientes.copy(listaPedidos = it)
            }

        }
    }

    var listaPedidosEnCamino by mutableStateOf(PedidosStates())
        private set

    init {
        viewModelScope.launch {
            pedidoDAO.obtenerPedidoPorEstado("EC").collectLatest {
                listaPedidosEnCamino = listaPedidosEnCamino.copy(listaPedidos = it)
            }

        }
    }

    fun actualizarPedido(pedidoEntity: PedidoEntity, status: String) = viewModelScope.launch {
        pedidoEntity.status = status
        pedidoDAO.actualizarPedido(pedidoEntity)
        repository.actualizarInfoPedido(pedidoEntity)
    }





    var listaPedidosTerminadosPorCliente by mutableStateOf(PedidosStates())
        private set

    fun cargarTerminadoCliente() {
        viewModelScope.launch {
            if (ultimoClienteIngresado.value != null) {
                pedidoDAO.obtenerPedidoPorEstadoYCliente(
                    "TE",
                    ultimoClienteIngresado.value!!.correo
                ).collectLatest {
                    listaPedidosTerminadosPorCliente =
                        listaPedidosTerminadosPorCliente.copy(listaPedidos = it)
                }
            }

        }
    }

    var listaPedidosEnCaminoPorCliente by mutableStateOf(PedidosStates())
        private set

    fun cargarEnCaminoCliente() {
        viewModelScope.launch {
            if (ultimoClienteIngresado.value != null) {
                pedidoDAO.obtenerPedidoPorEstadoYCliente(
                    "EC",
                    ultimoClienteIngresado.value!!.correo
                ).collectLatest {
                    listaPedidosEnCaminoPorCliente =
                        listaPedidosEnCaminoPorCliente.copy(listaPedidos = it)
                }
            }

        }
    }

}