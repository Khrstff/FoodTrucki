package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal_cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.ipn.escom.foodtrucki.foodtrucki.MainActivityViewModel
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal.MenuList
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal.MyBottomNavigation
import mx.ipn.escom.foodtrucki.foodtrucki.navigation.PantallasApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipalCliente(mainActivityViewModel: MainActivityViewModel, navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = {
            Text("Pantalla Mapa", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier) }) },
        bottomBar = { MyBottomNavigationCliente(navController = navController) }
    ) { it
        PantallaMaps(mainActivityViewModel = mainActivityViewModel, navController)
    }
}

@Composable
fun MyBottomNavigationCliente(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.height(50.dp),
        content = {
            // Assuming you want a menu icon at the start
            Row(  modifier = Modifier
                .fillMaxSize()
                ,horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { navController.navigate(PantallasApp.PantallaMaps.route) }) {
                    Icon(Icons.Filled.AddLocation, contentDescription = "Menu")
                }
                IconButton(onClick = { navController.navigate(PantallasApp.PantallaOrdenesCliente.route) }) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Pedidos")
                }
                IconButton(onClick = { navController.navigate(PantallasApp.PantallaCliente.route) }) {
                    Icon(Icons.Filled.SupervisedUserCircle, contentDescription = "RegProd")
                }
            }

        }
    )
}