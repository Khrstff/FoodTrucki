package mx.ipn.escom.foodtrucki.foodtrucki.m_v_vm.vistas.registro_cliente

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.ipn.escom.foodtrucki.foodtrucki.MainActivityViewModel
import mx.ipn.escom.foodtrucki.foodtrucki.R
import mx.ipn.escom.foodtrucki.foodtrucki.navigation.PantallasApp
import mx.ipn.escom.foodtrucki.foodtrucki.ui.theme.century

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistraCliente(navController: NavHostController, mainActivityViewModel: MainActivityViewModel) {

    val nombre: String by mainActivityViewModel.nombreRegistroCliente.observeAsState(initial = "")
    val apellidos: String by mainActivityViewModel.apellidoRegistroCliente.observeAsState(initial = "")
    val correo: String by mainActivityViewModel.correoRegistroCliente.observeAsState(initial = "")
    val password: String by mainActivityViewModel.passwordRegistroCliente.observeAsState(initial = "")
    val passwordRepetida: String by mainActivityViewModel.confirmarPasswordRegistroCliente.observeAsState(initial = "")
    val telefono: String by mainActivityViewModel.telefonoRegistroCliente.observeAsState(initial = "")
    var esBotonHabilitado by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold {it
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner_horizontal),
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            MySpacer(0, 17)

            Text(
                text = "Registro Cliente",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 25.sp,
                fontFamily = century,
                color = Color(0xFFd78730)
            )

            MySpacer(0, 17)

            OutlinedTextField(
                value = nombre,
                onValueChange = {nom ->
                    mainActivityViewModel.onRegistroClienteChanged(
                        nombre = nom,
                        apellido = apellidos,
                        correo = correo,
                        contra = password,
                        confirmContra = passwordRepetida,
                        telefono = telefono
                    )

                    esBotonHabilitado = habilitarRegistroClientes(
                        nombre = nom,
                        apellido = apellidos,
                        correo = correo,
                        contra = password,
                        confirmContra = passwordRepetida,
                        telefono = telefono
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Nombre", color = Color(0xFFFFC107)) },
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

            MySpacer(0, 17)

            OutlinedTextField(
                value = apellidos,
                onValueChange = {ape ->
                    mainActivityViewModel.onRegistroClienteChanged(
                        nombre = nombre,
                        apellido = ape,
                        correo = correo,
                        contra = password,
                        confirmContra = passwordRepetida,
                        telefono = telefono
                    )
                    esBotonHabilitado = habilitarRegistroClientes(
                        nombre = nombre,
                        apellido = ape,
                        correo = correo,
                        contra = password,
                        confirmContra = passwordRepetida,
                        telefono = telefono
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Apellido", color = Color(0xFFFFC107)) },
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

            MySpacer(0, 17)

            OutlinedTextField(
                value = correo,
                onValueChange = {cor ->
                    mainActivityViewModel.onRegistroClienteChanged(
                        nombre = nombre,
                        apellido = apellidos,
                        correo = cor,
                        contra = password,
                        confirmContra = passwordRepetida,
                        telefono = telefono
                    )
                    esBotonHabilitado = habilitarRegistroClientes(
                        nombre = nombre,
                        apellido = apellidos,
                        correo = cor,
                        contra = password,
                        confirmContra = passwordRepetida,
                        telefono = telefono
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Correo", color = Color(0xFFFFC107)) },
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

            MySpacer(0, 17)

            var passwordVisibility by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                onValueChange = {pas ->
                    mainActivityViewModel.onRegistroClienteChanged(
                        nombre = nombre,
                        apellido = apellidos,
                        correo = correo,
                        contra = pas,
                        confirmContra = passwordRepetida,
                        telefono = telefono
                    )
                    esBotonHabilitado = habilitarRegistroClientes(
                        nombre = nombre,
                        apellido = apellidos,
                        correo = correo,
                        contra = pas,
                        confirmContra = passwordRepetida,
                        telefono = telefono
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Contraseña", color = Color(0xFFFFC107)) },
                shape = RoundedCornerShape(10.dp),
                maxLines = 1,
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
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color(0xFFFFC107),
                    unfocusedTextColor = Color(0xFFFFC107),
                    focusedBorderColor = Color(0xFFFFC107),
                    unfocusedBorderColor = Color(0xFF7C721A)
                )
            )

            MySpacer(0, 17)

            var passwordVisibilityR by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = passwordRepetida,
                onValueChange = {pasR ->
                    mainActivityViewModel.onRegistroClienteChanged(
                        nombre = nombre,
                        apellido = apellidos,
                        correo = correo,
                        contra = password,
                        confirmContra = pasR,
                        telefono = telefono
                    )
                    esBotonHabilitado = habilitarRegistroClientes(
                        nombre = nombre,
                        apellido = apellidos,
                        correo = correo,
                        contra = password,
                        confirmContra = pasR,
                        telefono = telefono
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Confirmar Contraseña", color = Color(0xFFFFC107)) },
                shape = RoundedCornerShape(10.dp),
                maxLines = 1,
                singleLine = true,
                trailingIcon = {
                    val imagen = if (passwordVisibilityR) {
                        Icons.Filled.VisibilityOff
                    } else {
                        Icons.Filled.Visibility
                    }

                    IconButton(onClick = { passwordVisibilityR = !passwordVisibilityR }) {
                        Icon(imageVector = imagen, contentDescription = "Mostrar Contraseña")
                    }
                },
                visualTransformation = if (passwordVisibilityR) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color(0xFFFFC107),
                    unfocusedTextColor = Color(0xFFFFC107),
                    focusedBorderColor = Color(0xFFFFC107),
                    unfocusedBorderColor = Color(0xFF7C721A)
                )
            )

            MySpacer(0, 17)

            OutlinedTextField(
                value = telefono,
                onValueChange = {tel ->
                    mainActivityViewModel.onRegistroClienteChanged(
                        nombre = nombre,
                        apellido = apellidos,
                        correo = correo,
                        contra = password,
                        confirmContra = passwordRepetida,
                        telefono = tel
                    )
                    esBotonHabilitado = habilitarRegistroClientes(
                        nombre = nombre,
                        apellido = apellidos,
                        correo = correo,
                        contra = password,
                        confirmContra = passwordRepetida,
                        telefono = tel
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Telefono", color = Color(0xFFFFC107)) },
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

            MySpacer(0, 17)

            Button(
                onClick = {
                    scope.launch {
                        mainActivityViewModel.insertarCliente(
                            nombre = nombre,
                            apellido = apellidos,
                            correo = correo,
                            contra = password,
                            telefono = telefono
                        )
                        mainActivityViewModel.onRegistroClienteChanged("","", ""
                            ,"", "", "")
                        navController.navigate(PantallasApp.PantallaPrincipal.route)
                    }
                },
                enabled = esBotonHabilitado,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFd78730)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "Registrar Cliente", fontSize = 17.sp, fontFamily = century)
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

fun habilitarRegistroClientes(
    nombre: String,
    apellido: String,
    correo: String,
    contra: String,
    confirmContra: String,
    telefono: String
): Boolean {
    return (Patterns.EMAIL_ADDRESS.matcher(correo)
        .matches()) && (contra.length > 2) && (contra == confirmContra) &&
            (telefono.length == 10 && nombre.length > 2 && apellido.length > 2)//_esHabilitarBotonRegistroCliente.postValue(validacion)
}