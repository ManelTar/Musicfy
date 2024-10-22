package com.pdsll.musicfy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pdsll.musicfy.ViewModel.LoginScreenViewModel
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.BlueButton
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Purple80
import com.pdsll.musicfy.ui.theme.RedTint


@Composable
fun LogIn(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val currentUser by viewModel.currentUser.observeAsState()

    val context = LocalContext.current

    // Si hay un usuario autenticado, redirigir a la pantalla de inicio
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate(Pantalla.Inicio.route) {
                popUpTo(Pantalla.LogIn.route) { inclusive = true }
            }
        }
    }

    // Si no hay usuario autenticado, mostrar la pantalla de login
    if (currentUser == null) {
        // Crea un contenedor con un fondo rojo
        Box(modifier = Modifier.fillMaxSize().background(RedTint)) {
            // Columna que organiza los elementos verticalmente y centrados horizontalmente
            Column(
                modifier = Modifier.background(RedTint),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var show by rememberSaveable { mutableStateOf(false) }
                val username = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                var isPasswordVisible by remember { mutableStateOf(false) }

                Text(
                    text = "Inicia sesión",
                    fontSize = 36.sp,
                    fontFamily = MonsFontBold,
                    color = Letra,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 45.dp)
                        .wrapContentSize(Alignment.Center)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.musicfy_logo),
                    contentDescription = "Descripción de la imagen",
                    modifier = Modifier.size(200.dp)
                )

                TextField(
                    label = { Text(text = "Email", fontFamily = MonsFontBold) },
                    value = username.value,
                    onValueChange = { username.value = it },
                    supportingText = { Text(
                        text = "*Obligatorio",
                        color = Letra,
                        fontFamily = MonsFontBold) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    label = { Text(text = "Contraseña", fontFamily = MonsFontBold) },
                    value = password.value,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            val icon = if (isPasswordVisible) Icons.Default.RemoveRedEye else Icons.Default.RemoveRedEye
                            Icon(icon, contentDescription = "Toggle password visibility")
                        }
                    },
                    onValueChange = { password.value = it },
                    supportingText = { Text(text = "*Obligatorio", color = Letra, fontFamily = MonsFontBold) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            if (username.value.isNotEmpty() && password.value.isNotEmpty()) {
                                viewModel.signInWithEmailAndPassword(username.value, password.value, context) {
                                    navController.navigate(Pantalla.Inicio.route) {
                                        popUpTo(Pantalla.LogIn.route) { inclusive = true }
                                    }
                                }
                            } else {
                                show = true
                            }
                        },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(Purple80),
                        modifier = Modifier.width(200.dp).height(50.dp)
                    ) {
                        Text(
                            text = "Entrar",
                            fontFamily = MonsFontBold,
                            color = Letra)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                ClickableText(
                    text = AnnotatedString("¿Has olvidado la contraseña?"),
                    onClick = { navController.navigate(Pantalla.RecuContr.route) },
                    style = TextStyle(fontSize = 14.sp, fontFamily = MonsFontBold, color = BlueButton)
                )

                MyDialogo(show = show, onDismiss = { show = false })

                ClickableText(
                    text = AnnotatedString("¡Crea una cuenta!"),
                    modifier = Modifier.align(CenterHorizontally).padding(20.dp),
                    onClick = { navController.navigate(Pantalla.SignUp.route) },
                    style = TextStyle(fontSize = 14.sp, textDecoration = TextDecoration.Underline, color = Letra, fontFamily = MonsFontBold)
                )
            }
        }
    }
}

@Composable
fun MyDialogo(show: Boolean, onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = { TextButton(onClick = { onDismiss() }) { Text(text = "Aceptar", fontFamily = MonsFontBold) } },
            title = { Text(text = "Error", fontFamily = MonsFontBold) },
            text = { Text(text = "Introduce usuario y contraseña.", fontFamily = MonsFontBold) }
        )
    }
}
