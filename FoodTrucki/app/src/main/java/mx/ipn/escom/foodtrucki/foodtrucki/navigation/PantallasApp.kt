package mx.ipn.escom.foodtrucki.foodtrucki.navigation

sealed class PantallasApp(val route: String) {
    data object PantallaPrincipal: PantallasApp("PantallaPrincipal")
    data object PantallaSplash: PantallasApp("PantallaSplash")
    data object PantallaLogin: PantallasApp("PantallaLogin")
    data object SplashPrimero: PantallasApp("PantallaSplashPrimero")
    data object SplashSegundo: PantallasApp("PantallaSplashSegundo")
    data object PantallaRegistroCliente: PantallasApp("PantallaRegistroCliente")
    data object PantallaPedidosVendedor: PantallasApp("PantallaPedidosVendedor")
    data object PantallaAltaProductos: PantallasApp("PantallaAltaProductos")
    data object PantallaPrincipalCliente: PantallasApp("PantallaPrincipalCliente")
    data object PantallaMaps: PantallasApp("PantallaMaps")
    data object PantallaCliente: PantallasApp("PantallaCliente")
    data object PantallaOrdenesCliente: PantallasApp("PantallaOrdenesCliente")
}