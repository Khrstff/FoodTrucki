package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal_cliente

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import mx.ipn.escom.foodtrucki.foodtrucki.MainActivityViewModel
import mx.ipn.escom.foodtrucki.foodtrucki.R
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.PedidoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ProductoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMaps(mainActivityViewModel: MainActivityViewModel, navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = {
            Text("Pantalla Mapa", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier) }) },
        bottomBar = { MyBottomNavigationCliente(navController = navController) }
    ) { it
        ContenidoMaps(mainActivityViewModel)
    }
}

@Composable
fun ContenidoMaps(mainActivityViewModel: MainActivityViewModel) {
    val foodTrucki = LatLng(19.505, -99.14666667)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(foodTrucki, 16f)
    }
    Column (
        modifier = Modifier
            .padding(top = 40.dp).fillMaxSize().padding(horizontal = 16.dp)
    ) {
        InfoWindowContent(mainActivityViewModel)
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp),
            cameraPositionState = cameraPositionState
        ) {
            MarkerInfoWindow(
                state = MarkerState(position = foodTrucki),
                title = "FoodTrucki",
                snippet = "Marcador FoodTrucki"
            ) {

            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InfoWindowContent(mainActivityViewModel: MainActivityViewModel) {
    val listaProductos = mainActivityViewModel.listaPro

    Card(
        modifier = Modifier
            .padding(16.dp)
            .height(200.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp)) {
                items(listaProductos.listaProductos) {
                    ProductItemMaps(mainActivityViewModel = mainActivityViewModel, productName = it!!)
                }
            }
        }
    }
}

@Composable
fun ProductItemMaps(mainActivityViewModel: MainActivityViewModel, productName: ProductoEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    Text(
                        text = "Nombre del producto: ${productName.nombreProducto}",
                        fontWeight = FontWeight.Medium
                    )
                    Text(text = "Descripción: ${productName.descripcion}", color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.width(90.dp))
            ImagenAgregar(
                mainActivityViewModel = mainActivityViewModel,
                productoEntity = productName
            )
        }
    }
}

@Composable
fun ImagenAgregar(mainActivityViewModel: MainActivityViewModel, productoEntity: ProductoEntity) {
    val backgroundModifier = Modifier
        .size(50.dp)
        .background(Color(0xFFFFFFFF), shape = CircleShape)
        .padding(8.dp)

    val imageModifier = Modifier
        .size(50.dp)
        .clip(CircleShape)
        .clickable { mainActivityViewModel.agregarPedido(productoEntity) }

    Box(
        modifier = backgroundModifier,
        contentAlignment = Alignment.TopEnd
    ) {
        Button(onClick = { mainActivityViewModel.agregarPedido(productoEntity) }) {
            
        }
            Image(
                painter = painterResource(id = R.drawable.agregar),
                contentDescription = null,
                modifier = imageModifier
            )
    }
}
