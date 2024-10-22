package com.pdsll.musicfy

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // State variables for UI elements
    var show by rememberSaveable { mutableStateOf(false) }
    val nombre = remember { mutableStateOf("") }
    val last = remember { mutableStateOf(TextFieldValue()) }
    val mail = remember { mutableStateOf("") }
    val userName = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val tlf = remember { mutableStateOf("") }
    val check = remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var context = LocalContext.current

    // Outer Box with background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RedTint)
    ) {
        // Scrollable Column to arrange UI elements vertically
        LazyColumn(
            modifier = Modifier
                .background(RedTint)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Title Text
                Text(
                    text = "Crea tu cuenta",
                    style = TextStyle(fontSize = 40.sp),
                    fontFamily = MonsFontBold,
                    color = Letra,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 45.dp, bottom = 30.dp)
                        .wrapContentSize(Alignment.Center)
                )

                // TextFields for user input
                TextField(
                    label = { Text(text = "Nombre", fontFamily = MonsFontBold) },
                    value = nombre.value,
                    onValueChange = { nombre.value = it },
                    supportingText = {
                        Text(
                            text = "*Obligatorio",
                            color = Letra,
                            fontFamily = MonsFontBold
                        )
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    label = { Text(text = "Apellido", fontFamily = MonsFontBold) },
                    value = last.value,
                    onValueChange = { last.value = it }
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    label = { Text(text = "Correo", fontFamily = MonsFontBold) },
                    value = mail.value,
                    onValueChange = { mail.value = it },
                    supportingText = {
                        Text(
                            text = "*Obligatorio",
                            color = Letra,
                            fontFamily = MonsFontBold
                        )
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    label = { Text(text = "Nombre de usuario", fontFamily = MonsFontBold) },
                    value = userName.value,
                    onValueChange = { userName.value = it },
                    supportingText = {
                        Text(
                            text = "*Obligatorio",
                            color = Letra,
                            fontFamily = MonsFontBold
                        )
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                // Password TextField with visibility toggle
                TextField(
                    label = { Text(text = "Contraseña", fontFamily = MonsFontBold) },
                    value = password.value,
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

                TextField(
                    label = { Text(text = "Telefono", fontFamily = MonsFontBold) },
                    value = tlf.value,
                    onValueChange = { tlf.value = it },
                    leadingIcon = { Text("+34") },
                    supportingText = {
                        Text(
                            text = "*Obligatorio",
                            color = Letra,
                            fontFamily = MonsFontBold
                        )
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Checkbox for terms and conditions
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = check.value,
                        onCheckedChange = { check.value = it },
                        colors = CheckboxDefaults.colors(Letra),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Acepto los términos y condiciones",
                        fontFamily = MonsFontBold,
                        fontSize = 12.sp,
                        color = BlueButton
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Button to create account
                Button(
                    onClick = {
                        if (nombre.value.isNotEmpty() && mail.value.isNotEmpty() && password.value.isNotEmpty() && check.value && tlf.value.isNotEmpty() && userName.value.isNotEmpty()) {
                            viewModel.createUserWithEmailAndPassword(
                                mail.value,
                                password.value,
                                nombre.value,
                                userName.value,
                                tlf.value,
                                context
                            ) {
                                navController.navigate(Pantalla.LogIn.route)
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
                        .padding(horizontal = 40.dp)
                ) {
                    Text(
                        text = "Crear",
                        fontFamily = MonsFontBold,
                        color = Letra)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Display dialog for errors
                MyDialogoSignup(show = show, onDismiss = { show = false })

                // ClickableText to navigate to login screen
                ClickableText(
                    text = AnnotatedString("¿Ya tienes cuenta? ¡Inicia sesión!"),
                    modifier = Modifier.align(alignment = Center),
                    onClick = {
                        navController.navigate(Pantalla.LogIn.route)
                    },
                    style = TextStyle(
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Letra,
                        fontFamily = MonsFontBold
                    )
                )

                Spacer(modifier = Modifier.height(69.dp))
            }
        }
    }
}

@Composable
fun MyDialogoSignup(
    show: Boolean,
    onDismiss: () -> Unit
) {
    // Display AlertDialog if show is true
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "Aceptar", fontFamily = MonsFontBold)
                }
            },
            title = { Text(text = "Error", fontFamily = MonsFontBold) },
            text = {
                Text(
                    text = "Introduce todos los campos, incluidos los términos y condiciones.",
                    fontFamily = MonsFontBold
                )
            }
        )
    }
}



