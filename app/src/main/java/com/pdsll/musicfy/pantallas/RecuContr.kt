package com.pdsll.musicfy.pantallas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pdsll.musicfy.ViewModel.LoginScreenViewModel
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Purple80
import com.pdsll.musicfy.ui.theme.RedTint

@Composable
fun RecuContr(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RedTint)
    ) {
        Column(
            modifier = Modifier
                .background(RedTint),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var show by rememberSaveable { mutableStateOf(false) }

            // Variables de estado para almacenar el nombre de usuario y la contraseña
            val email = remember { mutableStateOf("") }

            val context = LocalContext.current

            // Título "Login"
            Text(
                text = "Recuperar contraseña", fontSize = 26.sp,
                fontFamily = MonsFontBold,
                color = Letra,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 45.dp)
                    .wrapContentSize(Alignment.Center)
            )

            // Espaciado vertical
            Spacer(modifier = Modifier.height(20.dp))

            // Campo de texto para el nombre de usuario
            TextField(
                label = { Text(text = "Correo electrónico", fontFamily = MonsFontBold) },
                value = email.value,
                onValueChange = { email.value = it },
                supportingText = {
                    Text(
                        text = "*Obligatorio",
                        color = Letra,
                        fontFamily = MonsFontBold
                    )
                })

            // Espaciado vertical
            Spacer(modifier = Modifier.height(20.dp))


            // Espaciado vertical
            Spacer(modifier = Modifier.height(20.dp))

            // Contenedor con margen para el botón
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                // Botón para iniciar sesión
                Button(
                    onClick = {
                        // Validación de campos vacíos
                        if (email.value.isNotEmpty()) {
                            // Iniciar sesión con Firebase (asumiendo implementación)
                            viewModel.sendPasswordReset(email.value) {
                                // Navegar a la pantalla de inicio
                                navController.navigate(Pantalla.Inicio.route)
                            }
                            Toast.makeText(context, "Email enviado", Toast.LENGTH_LONG).show()
                        } else {
                            // Mostrar el diálogo de error
                            show = true;
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(Purple80),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Recuperar contraseña",
                        fontFamily = MonsFontBold,
                        color = Letra)
                }

                // Diálogo de error
                MyDialogoContr(show = show, onDismiss = { show = false })
            }
        }
    }
}

@Composable
fun MyDialogoContr(
    show: Boolean,
    onDismiss: () -> Unit
) {
    if (show) {
        AlertDialog(onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "Aceptar", fontFamily = MonsFontBold)
                }
            },
            title = { Text(text = "Error", fontFamily = MonsFontBold) },
            text = {
                Text(
                    text = "Introduce el correo electrónico.",
                    fontFamily = MonsFontBold
                )
            })
    }

}