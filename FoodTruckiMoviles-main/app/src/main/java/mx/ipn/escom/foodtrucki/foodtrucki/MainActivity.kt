package mx.ipn.escom.foodtrucki.foodtrucki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import mx.ipn.escom.foodtrucki.foodtrucki.navigation.AppNavigation
import mx.ipn.escom.foodtrucki.foodtrucki.ui.theme.FoodTruckiTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        establecerValores()
        setContent {
            FoodTruckiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel)
                }
            }
        }
    }

    private fun establecerValores() {
        inicializarVista()
        escucharObservables()
    }

    private fun inicializarVista() {
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.insertarListaDeClientes()
        viewModel.insertarListaDeVendedores()
        viewModel.insertarListaPedidos()
        viewModel.insertarListaProductos()
    }

    private fun escucharObservables() {

    }
}