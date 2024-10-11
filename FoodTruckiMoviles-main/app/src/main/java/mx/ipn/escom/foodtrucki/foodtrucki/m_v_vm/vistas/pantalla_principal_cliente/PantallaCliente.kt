package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal_cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.ipn.escom.foodtrucki.foodtrucki.MainActivityViewModel
import mx.ipn.escom.foodtrucki.foodtrucki.R
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ClienteEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.PedidoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ProductoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.ui.theme.century

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCliente(mainActivityViewModel: MainActivityViewModel, navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = {
            Text("Pantalla Cliente", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier) }) },
        bottomBar = { MyBottomNavigationCliente(navController = navController) }
    ) { it
        mainActivityViewModel.cargarTerminadoCliente()
        ContenidoPantallaCliente(mainActivityViewModel = mainActivityViewModel)
    }
}

@Composable
fun ContenidoPantallaCliente(mainActivityViewModel: MainActivityViewModel) {
    val cliente = mainActivityViewModel.ultimoClienteIngresado
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            CircularImageExample()
            Text(
                text = cliente.value!!.correo,
                modifier = Modifier,
                fontSize = 25.sp,
                fontFamily = century,
                color = Color(0xFFd78730)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TarjetaInformacion(cliente.value!!)
            Spacer(modifier = Modifier.height(16.dp))
            TarjetaHistorial(mainActivityViewModel = mainActivityViewModel)
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun CircularImageExample() {
    val backgroundModifier = Modifier
        .size(120.dp)
        .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        .padding(8.dp)

    val imageModifier = Modifier
        .size(100.dp)
        .clip(CircleShape)
        .background(Color.Gray, CircleShape)

    Box(
        modifier = backgroundModifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = imageModifier
        )
    }
}

@Composable
fun TarjetaInformacion(clienteEntity: ClienteEntity) {
    val scrollState = rememberScrollState()

    Card(
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(state = scrollState)
                .fillMaxWidth()
        ) {
            Text(
                text = "Información",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 15.sp,
                fontFamily = century,
                color = Color(0xFFd78730)
            )

            Text(
                text = "Nombre: ${clienteEntity.nombre}",
                fontSize = 15.sp,
                fontFamily = century,
                color = Color(0xFFd78730)
            )

            Text(
                text = "Apellido: ${clienteEntity.apellido}",
                fontSize = 15.sp,
                fontFamily = century,
                color = Color(0xFFd78730)
            )

            Text(
                text = "Telefono: ${clienteEntity.telefono}",
                fontSize = 15.sp,
                fontFamily = century,
                color = Color(0xFFd78730)
            )
        }
    }
}


@Composable
fun TarjetaHistorial(mainActivityViewModel: MainActivityViewModel) {
    val listaPedidosTerminados = mainActivityViewModel.listaPedidosTerminadosPorCliente

    Card(
        modifier = Modifier
            .padding(16.dp).height(350.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Historial",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 20.sp,
                fontFamily = century,
                color = Color(0xFFd78730)
            )
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(listaPedidosTerminados.listaPedidos) {
                     (it!!)
                }
            }
        }
    }
}

@Composable
fun ProductItemCliente(productName: PedidoEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0xFFFFA726), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Foto", color = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Nombre del producto: ${productName.idPedido}", fontWeight = FontWeight.Medium)
                Text(text = "Descripción: ${productName.descripcion}", color = Color.Gray)
            }
        }
    }
}