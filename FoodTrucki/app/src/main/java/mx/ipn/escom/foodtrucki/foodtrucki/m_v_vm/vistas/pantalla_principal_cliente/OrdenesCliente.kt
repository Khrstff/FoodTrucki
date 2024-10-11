package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal_cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.ipn.escom.foodtrucki.foodtrucki.MainActivityViewModel
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.PedidoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ProductoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal.OrderItem
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal.PedidoItem
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal.PedidoItemEnCamino
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal.PedidoItemTerminados
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.registro_cliente.MySpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdenesClientes(mainActivityViewModel: MainActivityViewModel, navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = {
            Text("Pantalla Ordenes", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier) }) },
        bottomBar = { MyBottomNavigationCliente(navController = navController) }
    ) { it
        mainActivityViewModel.cargarTerminadoCliente()
        mainActivityViewModel.cargarEnCaminoCliente()
        ContenidoOrdenesClientes(mainActivityViewModel = mainActivityViewModel, modifier = Modifier.padding(1.dp))
    }
}

@Composable
fun ContenidoOrdenesClientes(mainActivityViewModel: MainActivityViewModel, modifier: Modifier) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "En camino",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val listaPedidosEnCamino = mainActivityViewModel.listaPedidosEnCaminoPorCliente
        LazyColumn(modifier = modifier
            .padding(10.dp)
            .height(300.dp)) {
            items(listaPedidosEnCamino.listaPedidos) {
                TarjetaOrdenCliente(pedidoEntity = it!!)
                MySpacer(width = 0, height = 5)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Entregados",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val listaPedidosTerminados = mainActivityViewModel.listaPedidosTerminadosPorCliente
        LazyColumn(modifier = modifier
            .padding(10.dp)
            .height(300.dp)) {
            items(listaPedidosTerminados.listaPedidos) {
                TarjetaOrdenCliente(pedidoEntity = it!!)
                MySpacer(width = 0, height = 5)
            }
        }
    }
}

@Composable
fun TarjetaOrdenCliente(pedidoEntity: PedidoEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
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
                    Text(text = pedidoEntity.idPedido, fontWeight = FontWeight.Bold)
                    Text(text = "Descripción: ${pedidoEntity.descripcion}", color = Color.Gray)
                    Text(text = "Cantidad: ${pedidoEntity.cantidadProductos}", color = Color.Gray)
                    Text(text = "Precio: ${pedidoEntity.costoTotal}", color = Color.Gray)
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Estado: ${pedidoEntity.status}",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800))
        }
    }
}