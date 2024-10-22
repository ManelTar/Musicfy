package com.pdsll.musicfy.pantallas

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.pdsll.musicfy.R
import com.pdsll.musicfy.ViewModel.CommentViewModel
import com.pdsll.musicfy.ViewModel.FavsViewModel
import com.pdsll.musicfy.models.DataViewModel
import com.pdsll.musicfy.navigation.DETAIL_ARGUMENT_KEY
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.Black
import com.pdsll.musicfy.ui.theme.Blue
import com.pdsll.musicfy.ui.theme.JostFont
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFont
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Purple80
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint


@Composable
fun CartaDetallada(
    navController: NavController,
    id: String,
    dataViewModel: DataViewModel = viewModel(),
    albumsViewModel: FavsViewModel = viewModel(),
    commentViewModel: CommentViewModel = viewModel()
) {

    val context = LocalContext.current
    val comment = remember { mutableStateOf("") }

    val getItems = dataViewModel.stateM.value

    Log.d("DETAIL_ARGUMENT_KEY", "DETAIL_ARGUMENT_KEY ")

    val icon = R.drawable.apple_music_app_icon_green

    val selectedItem = getItems.find { it.Id == id }
    val selectedLink = selectedItem?.Link
    if (selectedLink != null && selectedLink.isNotBlank()) {
        val lintent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(selectedLink)) }
        Log.d("Link", "Link seleccionado: $selectedLink")


        if (selectedItem != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RedTint)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .background(Red)
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("inicio")
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
                        text = "MUSICFY",
                        fontSize = 36.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 45.dp, bottom = 8.dp)
                            .wrapContentSize(Alignment.Center)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RedTint)
                        .padding(top = 10.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedItem.Img),
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .wrapContentSize(Alignment.TopStart)
                            .padding(start = 10.dp, top = 13.dp)
                    )

                    Column(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .background(RedTint)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp)
                        ) {
                            Text(
                                text = selectedItem.Nombre,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = MonsFontBold,
                                color = Letra
                            )

                            Text(
                                text = selectedItem.Artista,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light,
                                fontFamily = MonsFont,
                                color = Letra,
                                textDecoration = TextDecoration.Underline
                            )

                            Text(
                                text = selectedItem.Genero,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Light,
                                fontFamily = MonsFont,
                                color = Letra,
                                fontStyle = FontStyle.Italic
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 10.dp, start = 8.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(
                                text = "${selectedItem.Duracion} | ${selectedItem.Año} | ${selectedItem.Canciones.toString() + " canciones"}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                fontFamily = MonsFontBold,
                                color = Letra
                            )
                        }
                    }

                }


                OutlinedTextField(
                    value = comment.value,
                    onValueChange = { comment.value = it },
                    placeholder = {
                        Text(
                            "¿Qué opinas de este álbum?",
                            fontFamily = MonsFontBold,
                            fontSize = 15.sp
                        )
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(start = 15.dp, top = 30.dp, end = 15.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(10.dp)
                        ),
                    colors = OutlinedTextFieldDefaults.colors(Black)// Add some padding to the left and right of the text field
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.End// Agrega un poco de padding alrededor de la fila si es necesario
                ) {
                    val isFavorite by remember { derivedStateOf { albumsViewModel.isFavorite(id) } }
                    IconButton(
                        onClick = {
                            albumsViewModel.toggleFavorite(id)
                            val message = if (isFavorite) "Añadido a favoritos" else "Eliminado de favoritos"
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            if (!isFavorite) {
                                navController.navigate("favoritos")
                            }
                        },
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.HeartBroken else Icons.Default.Favorite,
                            contentDescription = if (isFavorite) "Añadir a favoritos" else "Eliminar de favoritos",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Button(
                    onClick = {
                        // Validación de campos vacíos
                        // Iniciar sesión con Firebase (asumiendo implementación)
                        commentViewModel.addComment(selectedItem.Id, comment.value)
                        // Navegar a la pantalla de inicio
                        navController.navigate(Pantalla.Inicio.route)
                        // Mostrar el diálogo de error
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(Purple80),
                    modifier = Modifier
                        .width(300.dp)
                        .height(70.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = "Añadir comentario",
                        fontFamily = MonsFontBold,
                        color = Letra
                    )
                }

                Button(
                    onClick = {
                        context.startActivity(lintent)
                    },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(Purple80),
                    modifier = Modifier
                        .width(200.dp)
                        .height(70.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = "Ver en Spotify",
                        fontFamily = MonsFontBold,
                        color = Letra
                    )
                }
            }


        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RedTint)
            ) {
                Row(
                    modifier = Modifier
                        .background(Red)
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("inicio")
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
                        text = "MUSICFY",
                        fontSize = 36.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 45.dp, bottom = 8.dp)
                            .wrapContentSize(Alignment.Center)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Fallo al cargar la obra",
                        fontSize = 24.sp,
                        fontFamily = MonsFontBold,
                        textAlign = TextAlign.Center,
                        color = Letra
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(96.dp))
    } else {
        Log.e("Link", "El link seleccionado es nulo o no válido")
    }
}


