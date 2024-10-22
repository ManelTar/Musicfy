package com.pdsll.musicfy.pantallas

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pdsll.musicfy.ViewModel.LoginScreenViewModel
import com.pdsll.musicfy.models.Result
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.Black
import com.pdsll.musicfy.ui.theme.ButtonBorrar
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Purple80
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint

@Composable
fun updateUser(
    navController: NavController,
    loginScreenViewModel: LoginScreenViewModel = viewModel(),
    onDeleteSuccess: () -> Unit
) {
    val deleteStatus by loginScreenViewModel.deleteStatus.observeAsState()

    var show by rememberSaveable { mutableStateOf(false) }
    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // Contenedor con fondo rojo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RedTint)
    ) {
        // Columna que organiza los elementos verticalmente y centrados horizontalmente
        Column(
            modifier = Modifier.background(RedTint),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier
                .background(Red)){
                IconButton(
                    onClick = {
                        navController.navigate("ajustes")
                    },
                    modifier = Modifier
                        .padding(top = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "Favorito",
                        tint = Black,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 8.dp, start = 5.dp)
                    )
                }
                Text(
                    text = "Cambiar datos",
                    fontSize = 32.sp,
                    fontFamily = MonsFontBold,
                    color = Letra,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 45.dp, bottom = 8.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(150.dp))

            TextField(
                label = { Text(text = "Nombre", fontFamily = MonsFontBold) },
                value = username.value,
                onValueChange = { username.value = it },
                supportingText = {
                    Text(
                        text = "*Obligatorio",
                        color = Letra,
                        fontFamily = MonsFontBold
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                label = { Text(text = "Teléfono", fontFamily = MonsFontBold) },
                value = password.value,
                onValueChange = { password.value = it },
                supportingText = {
                    Text(
                        text = "*Obligatorio",
                        color = Letra,
                        fontFamily = MonsFontBold
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        if (username.value.isNotEmpty() || password.value.isNotEmpty()) {
                            loginScreenViewModel.updateUser(username.value, password.value)
                            navController.navigate(Pantalla.Perfil.route)
                        } else {
                            show = true
                        }
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(Purple80),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Guardar cámbios",
                        fontFamily = MonsFontBold,
                        color = Letra
                    )
                }
                Button(
                    onClick = {
                        showConfirmDialog = true
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(ButtonBorrar),
                    modifier = Modifier
                        .width(200.dp)
                        .height(80.dp)
                        .padding(top = 30.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Borrar cuenta",
                        fontFamily = MonsFontBold,
                        color = Letra,
                        textAlign = TextAlign.Center
                    )
                }
                deleteStatus?.let { result ->
                    when (result) {
                        is Result.Success -> {
                            onDeleteSuccess()
                        }

                        is Result.Failure -> {
                            Text(
                                text = "Failed to delete account: ${result.exception.message}",
                                color = Color.Red,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }

            MyDialogo(show = show, onDismiss = { show = false })

            if (showConfirmDialog) {
                AlertDialog(
                    onDismissRequest = { showConfirmDialog = false },
                    title = { Text(text = "Confirmar eliminación", fontFamily = MonsFontBold) },
                    text = {
                        Text(
                            text = "¿Estás seguro de que deseas borrar tu cuenta?",
                            fontFamily = MonsFontBold
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showConfirmDialog = false
                                loginScreenViewModel.deleteUserAccount()
                            }
                        ) {
                            Text(text = "Sí", fontFamily = MonsFontBold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showConfirmDialog = false }) {
                            Text(text = "No", fontFamily = MonsFontBold)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}


@Composable
fun MyDialogo(show: Boolean, onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(
                        text = "Aceptar",
                        fontFamily = MonsFontBold
                    )
                }
            },
            title = { Text(text = "Error", fontFamily = MonsFontBold) },
            text = { Text(text = "Introduce nombre o teléfono.", fontFamily = MonsFontBold) }
        )
    }
}

