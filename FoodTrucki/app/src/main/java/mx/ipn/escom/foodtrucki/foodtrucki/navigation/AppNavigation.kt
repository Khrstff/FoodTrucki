package mx.ipn.escom.foodtrucki.foodtrucki.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import mx.ipn.escom.foodtrucki.foodtrucki.MainActivityViewModel
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.login.PantallaLogin
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal.OrdersScreen
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal.PantallaAgregar
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal.PantallaPrincipal
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal_cliente.OrdenesClientes
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal_cliente.PantallaCliente
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal_cliente.PantallaMaps
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_principal_cliente.PantallaPrincipalCliente
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_splash.PantallaSplash
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_splash.PantallaSplashPrimera
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_splash.PantallaSplashSegundo
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_splash.SplashUno
import mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.registro_cliente.RegistraCliente

@Composable
fun AppNavigation(mainActivityViewModel: MainActivityViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PantallasApp.SplashPrimero.route) {
        composable(PantallasApp.PantallaSplash.route) {
            PantallaSplash(navController = navController)
        }
        composable(PantallasApp.PantallaPrincipal.route) {
            PantallaPrincipal(mainActivityViewModel = mainActivityViewModel, navController = navController)
        }
        composable(PantallasApp.PantallaLogin.route) {
            PantallaLogin(navController = navController, mainActivityViewModel = mainActivityViewModel)
        }
        composable(PantallasApp.SplashPrimero.route) {
            PantallaSplashPrimera(navController = navController)
        }
        composable(PantallasApp.SplashSegundo.route) {
            PantallaSplashSegundo(navController = navController)
        }
        composable(PantallasApp.PantallaRegistroCliente.route) {
            RegistraCliente(navController = navController,  mainActivityViewModel = mainActivityViewModel)
        }
        composable(PantallasApp.PantallaPedidosVendedor.route){
            OrdersScreen(mainActivityViewModel = mainActivityViewModel, navController = navController)
        }
        composable(PantallasApp.PantallaAltaProductos.route){
            PantallaAgregar(mainActivityViewModel = mainActivityViewModel, navController = navController)
        }
        composable(PantallasApp.PantallaPrincipalCliente.route){
            PantallaPrincipalCliente(mainActivityViewModel = mainActivityViewModel, navController = navController)
        }
        composable(PantallasApp.PantallaOrdenesCliente.route){
            OrdenesClientes(mainActivityViewModel = mainActivityViewModel, navController = navController)
        }
        composable(PantallasApp.PantallaCliente.route){
            PantallaCliente(mainActivityViewModel = mainActivityViewModel, navController = navController)
        }
        composable(PantallasApp.PantallaMaps.route){
            PantallaMaps(mainActivityViewModel = mainActivityViewModel, navController = navController)
        }
    }
}