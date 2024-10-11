package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import mx.ipn.escom.foodtrucki.foodtrucki.MainActivityViewModel
import mx.ipn.escom.foodtrucki.foodtrucki.R
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.PedidoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.data.entidad.ProductoEntity
import mx.ipn.escom.foodtrucki.foodtrucki.navigation.PantallasApp
import mx.ipn.escom.foodtrucki.foodtrucki.ui.theme.century

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(mainActivityViewModel: MainActivityViewModel, navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = {
            Text("Menú", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier) }) },
        bottomBar = { MyBottomNavigation(navController = navController) },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        MenuList(mainActivityViewModel = mainActivityViewModel, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun MyBottomNavigation(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.height(50.dp),
        content = {
            // Assuming you want a menu icon at the start
            Row(  modifier = Modifier
                .fillMaxSize()
                ,horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { navController.navigate(PantallasApp.PantallaPrincipal.route) }) {
                    Icon(Icons.Filled.Home, contentDescription = "Menu")
                }
                IconButton(onClick = { navController.navigate(PantallasApp.PantallaPedidosVendedor.route) }) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Pedidos")
                }
                IconButton(onClick = { navController.navigate(PantallasApp.PantallaAltaProductos.route) }) {
                    Icon(Icons.Filled.Add, contentDescription = "RegProd")
                }
            }

        }
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MenuList(mainActivityViewModel: MainActivityViewModel, modifier: Modifier = Modifier) {
    val listaProductos = mainActivityViewModel.listaPro
    LazyColumn(modifier = modifier.padding(10.dp)) {
        items(listaProductos.listaProductos) {
            ProductItem(mainActivityViewModel = mainActivityViewModel, productName = it!!)
        }
    }
}

@Composable
fun ProductItem(mainActivityViewModel: MainActivityViewModel, productName: ProductoEntity) {
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
            IamgenBorrar(
                mainActivityViewModel = mainActivityViewModel,
                productoEntity = productName
            )
        }
    }
}

@Composable
fun IamgenBorrar(mainActivityViewModel: MainActivityViewModel, productoEntity: ProductoEntity) {
    val backgroundModifier = Modifier
        .size(50.dp)
        .background(Color(0xFFFFFFFF), shape = CircleShape)
        .padding(8.dp)

    val imageModifier = Modifier
        .size(50.dp)
        .clip(CircleShape)
        .clickable { mainActivityViewModel.eliminarProducto(productoEntity) }

    Box(
        modifier = backgroundModifier,
        contentAlignment = Alignment.TopEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.eliminar),
            contentDescription = null,
            modifier = imageModifier
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(mainActivityViewModel: MainActivityViewModel, navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Pedidos", fontSize = 20.sp, fontWeight = FontWeight.Bold) }) },
        bottomBar = { MyBottomNavigation(navController = navController) },
        content = { innerPadding ->
            OrdersList(mainActivityViewModel = mainActivityViewModel, modifier = Modifier.padding(1.dp))
        }
    )
}

@Composable
fun OrdersList(mainActivityViewModel: MainActivityViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Pendientes",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp
        )

        val listaPedidosPendientes = mainActivityViewModel.listaPedidosPendientes
        LazyColumn(modifier = modifier.height(200.dp)) {
            items(listaPedidosPendientes.listaPedidos) {
                PedidoItem(mainActivityViewModel = mainActivityViewModel, pedidoEntity = it!!)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "En camino",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp
        )

        val listaPedidosEnCamino = mainActivityViewModel.listaPedidosEnCamino
        LazyColumn(modifier = modifier.height(200.dp)) {
            items(listaPedidosEnCamino.listaPedidos) {
                PedidoItemEnCamino(mainActivityViewModel = mainActivityViewModel, pedidoEntity = it!!)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Entregados",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp
        )

        val listaPedidosTerminados = mainActivityViewModel.listaPedidosTerminados
        LazyColumn(modifier = modifier.height(200.dp)) {
            items(listaPedidosTerminados.listaPedidos) {
                PedidoItemTerminados(pedidoEntity = it!!)
            }
        }
    }
}

@Composable
fun OrderItem(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = title, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Text(text = description, color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@Composable
fun PedidoItem(mainActivityViewModel: MainActivityViewModel, pedidoEntity: PedidoEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
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
                        text = "Nombre del producto: ${pedidoEntity.idPedido}",
                        fontWeight = FontWeight.Medium
                    )
                    Text(text = "Descripción: ${pedidoEntity.descripcion}", color = Color.Gray)
                }
            }
        }
        Text(text = "Estato: ${pedidoEntity.status}", color = Color.Gray)
        Spacer(modifier = Modifier.height(10.dp))
        ImagenEmpezarRuta(mainActivityViewModel = mainActivityViewModel, pedidoEntity = pedidoEntity )
    }
}

@Composable
fun ImagenEmpezarRuta(mainActivityViewModel: MainActivityViewModel, pedidoEntity: PedidoEntity) {
    Row (modifier = Modifier.fillMaxWidth()) {
        val backgroundModifierBici = Modifier
            .size(50.dp)
            .background(Color(0xFFFFFFFF), shape = CircleShape)
            .padding(8.dp)

        val imageModifierBici = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .clickable {
                pedidoEntity.tipoEnvio = "Bicicleta"
                mainActivityViewModel.actualizarPedido(pedidoEntity, "EC")
            }

        Box(
            modifier = backgroundModifierBici,
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.bicicleta),
                contentDescription = null,
                modifier = imageModifierBici
            )
        }


        val backgroundModifierMoto = Modifier
            .size(50.dp)
            .background(Color(0xFFFFFFFF), shape = CircleShape)
            .padding(8.dp)

        val imageModifierMoto = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .clickable {
                pedidoEntity.tipoEnvio = "Motocicleta"
                mainActivityViewModel.actualizarPedido(pedidoEntity, "EC")
            }

        Box(
            modifier = backgroundModifierMoto,
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.moto),
                contentDescription = null,
                modifier = imageModifierMoto
            )
        }
    }
}



@Composable
fun PedidoItemEnCamino(mainActivityViewModel: MainActivityViewModel, pedidoEntity: PedidoEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
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
                        text = "Nombre del producto: ${pedidoEntity.idPedido}",
                        fontWeight = FontWeight.Medium
                    )
                    Text(text = "Descripción: ${pedidoEntity.descripcion}", color = Color.Gray)
                }
            }
        }
        Text(text = "Estato: ${pedidoEntity.status}", color = Color.Gray)
        Spacer(modifier = Modifier.height(15.dp))
        ImagenTerminarRuta(mainActivityViewModel = mainActivityViewModel, pedidoEntity = pedidoEntity )
    }
}

@Composable
fun ImagenTerminarRuta(mainActivityViewModel: MainActivityViewModel, pedidoEntity: PedidoEntity) {
    val backgroundModifier = Modifier
        .size(50.dp)
        .background(Color(0xFFFFFFFF), shape = CircleShape)
        .padding(8.dp)

    val imageModifier = Modifier
        .size(50.dp)
        .clip(CircleShape)
        .clickable { mainActivityViewModel.actualizarPedido(pedidoEntity, "TE") }

    Box(
        modifier = backgroundModifier,
        contentAlignment = Alignment.TopEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.iniciar),
            contentDescription = null,
            modifier = imageModifier
        )
    }
}


@Composable
fun PedidoItemTerminados(pedidoEntity: PedidoEntity) {
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
                        text = "Nombre del producto: ${pedidoEntity.idPedido}",
                        fontWeight = FontWeight.Medium
                    )
                    Text(text = "Descripción: ${pedidoEntity.descripcion}", color = Color.Gray)
                }
            }
        }
        Text(text = "Estato: ${pedidoEntity.status}", color = Color.Gray)
        Spacer(modifier = Modifier.height(15.dp))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregar(mainActivityViewModel: MainActivityViewModel, navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro de Producto", fontSize = 20.sp, fontWeight = FontWeight.Bold) }) },
        bottomBar = { MyBottomNavigation(navController = navController) },
        content = { it
            Agrega(mainActivityViewModel = mainActivityViewModel)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Agrega(mainActivityViewModel: MainActivityViewModel){
    val nombreProducto: String by mainActivityViewModel.nombreProducto.observeAsState(initial = "")
    val descripcionProducto: String by mainActivityViewModel.descripcionProducto.observeAsState(initial = "")
    val costoProducto: String by mainActivityViewModel.costoProducto.observeAsState(initial = "")
    val tipoProducto: String by mainActivityViewModel.tipoProducto.observeAsState(initial = "")
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(70.dp))

        // Placeholder for the image input
        Box(
            modifier = Modifier
                .size(144.dp)
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFCCAB47)),
                onClick = { /* TODO: Implement image picker */ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Mostrar Contraseña")
            }
        }
        Text(text = "Agregar Foto", color = Color(0xFFFFC107))

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombreProducto,
            onValueChange = {
                mainActivityViewModel.onRegistroProductoChanged(
                    nombre = it,
                    descripcion = descripcionProducto,
                    tipo = tipoProducto,
                    costo = costoProducto
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterHorizontally),
            label = { Text(text = "Ingrese el nombre del producto", color = Color(0xFFFFC107)) },
            shape = RoundedCornerShape(10.dp),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color(0xFFFFC107),
                unfocusedTextColor = Color(0xFFFFC107),
                focusedBorderColor = Color(0xFFFFC107),
                unfocusedBorderColor = Color(0xFF7C721A)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = descripcionProducto,
            onValueChange = {
                mainActivityViewModel.onRegistroProductoChanged(
                    nombre = nombreProducto,
                    descripcion = it,
                    tipo = tipoProducto,
                    costo = costoProducto
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterHorizontally),
            label = { Text(text = "Ingrese la descripción del producto", color = Color(0xFFFFC107)) },
            shape = RoundedCornerShape(10.dp),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color(0xFFFFC107),
                unfocusedTextColor = Color(0xFFFFC107),
                focusedBorderColor = Color(0xFFFFC107),
                unfocusedBorderColor = Color(0xFF7C721A)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = tipoProducto,
            onValueChange = {
                mainActivityViewModel.onRegistroProductoChanged(
                    nombre = nombreProducto,
                    descripcion = descripcionProducto,
                    tipo = it,
                    costo = costoProducto
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterHorizontally),
            label = { Text(text = "Ingrese el tipo de producto (Se tiene que cambiar a spinner)", color = Color(0xFFFFC107)) },
            shape = RoundedCornerShape(10.dp),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color(0xFFFFC107),
                unfocusedTextColor = Color(0xFFFFC107),
                focusedBorderColor = Color(0xFFFFC107),
                unfocusedBorderColor = Color(0xFF7C721A)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = costoProducto,
            onValueChange = {
                mainActivityViewModel.onRegistroProductoChanged(
                    nombre = nombreProducto,
                    descripcion = descripcionProducto,
                    tipo = tipoProducto,
                    costo = it
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterHorizontally),
            label = { Text(text = "Ingrese el costo del producto", color = Color(0xFFFFC107)) },
            shape = RoundedCornerShape(10.dp),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color(0xFFFFC107),
                unfocusedTextColor = Color(0xFFFFC107),
                focusedBorderColor = Color(0xFFFFC107),
                unfocusedBorderColor = Color(0xFF7C721A)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    mainActivityViewModel.insertarProducto(
                        nombre = nombreProducto,
                        descripcion = descripcionProducto,
                        tipo = tipoProducto,
                        costo = costoProducto
                    )
                    mainActivityViewModel.onRegistroProductoChanged("","", "" ,"")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFd78730)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(text = "REGISTRAR PRODUCTO", fontSize = 17.sp, fontFamily = century)
        }
    }


    // Repeat for Description, Type, and Cost
    // ...

    Spacer(modifier = Modifier.height(16.dp))
}