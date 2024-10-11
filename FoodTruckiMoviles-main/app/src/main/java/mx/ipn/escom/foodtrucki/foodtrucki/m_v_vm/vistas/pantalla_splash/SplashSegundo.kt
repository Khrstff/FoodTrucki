package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.pantalla_splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import mx.ipn.escom.foodtrucki.foodtrucki.R
import mx.ipn.escom.foodtrucki.foodtrucki.navigation.PantallasApp

@Composable
fun PantallaSplashSegundo(navController: NavHostController) {
    LaunchedEffect(key1 = 1) {
        delay(1000)
        navController.popBackStack()
        navController.navigate(PantallasApp.PantallaSplash.route)
    }

    SplashDos()

}

@Composable
fun SplashDos() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.segunda_imagen_splash),
            contentDescription = "Logo Food Trucki",
            Modifier.size(150.dp, 150.dp))
    }
}