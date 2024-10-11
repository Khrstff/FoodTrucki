package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.login

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.ipn.escom.foodtrucki.foodtrucki.MainActivityViewModel
import mx.ipn.escom.foodtrucki.foodtrucki.navigation.PantallasApp
import mx.ipn.escom.foodtrucki.foodtrucki.ui.theme.century

@Composable
fun PantallaLogin(navController: NavHostController, mainActivityViewModel: MainActivityViewModel) {
    MostrarLogin(navController, mainActivityViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MostrarLogin(navController: NavHostController, mainActivityViewModel: MainActivityViewModel) {
    val correo: String by mainActivityViewModel.correo.observeAsState(initial = "")
    val contrasenia: String by mainActivityViewModel.password.observeAsState(initial = "")
    val esBotonHabilitado: Boolean by mainActivityViewModel.esUsuarioCorrecto.observeAsState(initial = false)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) {it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            MySpacer(0, 15)
            Image(
                painter = painterResource(id = mx.ipn.escom.foodtrucki.foodtrucki.R.drawable.logo_food_trucki),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(
                        height = 250.dp,
                        width = 250.dp
                    )
                    .align(Alignment.CenterHorizontally)
                    .scale(0.5F)
            )
            MySpacer(0, 25)

            OutlinedTextField(
                value = correo,
                onValueChange = {
                    mainActivityViewModel.onLoginChanged(it, contrasenia)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Ingrese su correo", color = Color(0xFFFFC107)) },
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

            MySpacer(0, 10)

            var passwordVisibility by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = contrasenia,
                onValueChange = {
                    mainActivityViewModel.onLoginChanged(correo, it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Ingrese su contraseña", color = Color(0xFFFFC107)) },
                shape = RoundedCornerShape(10.dp),
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color(0xFFFFC107),
                    unfocusedTextColor = Color(0xFFFFC107),
                    focusedBorderColor = Color(0xFFFFC107),
                    unfocusedBorderColor = Color(0xFF7C721A)
                ),
                trailingIcon = {
                    val imagen = if (passwordVisibility) {
                        Icons.Filled.VisibilityOff
                    } else {
                        Icons.Filled.Visibility
                    }

                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = imagen, contentDescription = "Mostrar Contraseña")
                    }
                },
                visualTransformation = if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )
            MySpacer(0, 30)
            Button(
                onClick = {
                    scope.launch {
                        mainActivityViewModel.validarUsuario(correo, contrasenia)
                        delay(500)
                        if (esBotonHabilitado) {
                            if (correo == "movilesproyecto2431@gmail.com" && contrasenia == "ADMIN") {
                                mainActivityViewModel.onLoginChanged("", "")
                                navController.navigate(PantallasApp.PantallaPrincipal.route)
                            } else {
                                mainActivityViewModel.establecerUltimoClienteIngresado(correo, contrasenia)
                                mainActivityViewModel.onLoginChanged("", "")
                                navController.navigate(PantallasApp.PantallaPrincipalCliente.route)
                            }
                        } else {
                            snackbarHostState.showSnackbar("El usuario no esta registrado")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFd78730)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "INGRESAR", fontSize = 17.sp, fontFamily = century)
            }
            MySpacer(0, 35)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                color = Color(0xFF696968)
            )
            MySpacer(0, 35)
            Text(
                text = "¿Aun no tienes cuenta?",
                fontFamily = century,
                fontSize = 18.sp,
                color = Color.LightGray,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            MySpacer(0, 15)
            TextButton(
                onClick = {navController.navigate(PantallasApp.PantallaRegistroCliente.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Registrate Aquí",
                    fontSize = 17.sp,
                    fontFamily = century,
                    color = Color(0xFFd78730)
                )
            }
        }
    }
}

@Composable
fun MySpacer(width: Int, height: Int) {
    Spacer(modifier = Modifier
        .height(height.dp)
        .width(width.dp))
}