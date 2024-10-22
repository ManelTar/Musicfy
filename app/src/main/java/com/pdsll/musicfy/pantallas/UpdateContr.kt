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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.RemoveRedEye
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.pdsll.musicfy.ViewModel.LoginScreenViewModel
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.Black
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Purple80
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint

@Composable
fun updateContr(
    navController: NavController,
    loginScreenViewModel: LoginScreenViewModel = viewModel()
) {
    var show by rememberSaveable { mutableStateOf(false) }
    var show2 by rememberSaveable { mutableStateOf(false) }
    val newPassword = remember { mutableStateOf("") }
    val newPassword2 = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var isNewPasswordVisible by remember { mutableStateOf(false) }

    var isNewPasswordVisible2 by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val check = remember { mutableStateOf(false) }
// Crea un contenedor con un fondo rojo
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
                .background(Red)
            ){
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
                    text = "Cambiar contraseña",
                    fontSize = 24.sp,
                    fontFamily = MonsFontBold,
                    color = Letra,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, end = 45.dp, bottom = 15.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(150.dp))

            TextField(
                label = { Text(text = "Contraseña antigua", fontFamily = MonsFontBold) },
                value = password.value,
                onValueChange = { password.value = it },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    ) {
                        val icon =
                            if (isPasswordVisible) Icons.Default.RemoveRedEye else Icons.Default.RemoveRedEye
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                },
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
                label = { Text(text = "Contraseña nueva", fontFamily = MonsFontBold) },
                value = newPassword.value,
                onValueChange = { newPassword.value = it },
                visualTransformation = if (isNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { isNewPasswordVisible = !isNewPasswordVisible }
                    ) {
                        val icon =
                            if (isNewPasswordVisible) Icons.Default.RemoveRedEye else Icons.Default.RemoveRedEye
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                },
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
                label = { Text(text = "Repite la contraseña", fontFamily = MonsFontBold) },
                value = newPassword2.value,
                onValueChange = { newPassword2.value = it },
                visualTransformation = if (isNewPasswordVisible2) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { isNewPasswordVisible2 = !isNewPasswordVisible2 }
                    ) {
                        val icon =
                            if (isNewPasswordVisible2) Icons.Default.RemoveRedEye else Icons.Default.RemoveRedEye
                        Icon(icon, contentDescription = "Toggle password visibility")
                    }
                },
                supportingText = {
                    Text(
                        text = "*Obligatorio",
                        color = Letra,
                        fontFamily = MonsFontBold
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        if (password.value.isNotEmpty() && newPassword.value.isNotEmpty() && newPassword2.value.isNotEmpty()) {
                            if(newPassword.value == newPassword2.value) {
                                loginScreenViewModel.changePassword(password.value, newPassword.value, context)
                                navController.navigate(Pantalla.Perfil.route)
                            } else {
                                show2 = true
                            }
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
                        color = Letra)
                }
            }


            MyDialogg(show = show, onDismiss = { show = false })
            MyDialog2(show = show2, onDismiss = { show2 = false })

            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}

@Composable
fun MyDialogg(show: Boolean, onDismiss: () -> Unit) {
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
            text = { Text(text = "Introduce todos los campos.", fontFamily = MonsFontBold) }
        )
    }
}

@Composable
fun MyDialog2(show: Boolean, onDismiss: () -> Unit) {
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
            text = { Text(text = "Las contraseñas no coinciden.", fontFamily = MonsFontBold) }
        )
    }
}
