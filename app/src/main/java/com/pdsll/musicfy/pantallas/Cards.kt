package com.pdsll.musicfy.pantallas

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.pdsll.musicfy.R
import com.pdsll.musicfy.ViewModel.CommentViewModel
import com.pdsll.musicfy.ViewModel.FavsViewModel
import com.pdsll.musicfy.ViewModel.UserViewModel
import com.pdsll.musicfy.models.Album
import com.pdsll.musicfy.models.DataViewModel
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.Black
import com.pdsll.musicfy.ui.theme.DarkBlue
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFont
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Red


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasicItemCard(
    img: String, // Use lowercase for consistency
    album: String,
    artista: String,
    Id: String,
    Link: String,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    // Dimensiones de la tarjeta
    val cardHeight = 210.dp
    val cardWidth = 160.dp

    // Altura dinámica de la imagen
    val imageHeight = 140.dp // Calculate dynamic image height

    // Tarjeta con borde redondeado, sombra y acción al hacer clic
    Card(
        modifier = modifier
            .fillMaxWidth() // Ocupa el ancho máximo disponible
            .padding(4.dp) // Ajuste el padding según sea necesario
            .width(cardWidth) // Ancho fijo
            .height(cardHeight), // Altura fija
        shape = RoundedCornerShape(10.dp), // Bordes redondeados
        elevation = 6.dp, // Sombra
        onClick = { // Acción al hacer clic
            navController.navigate(route = Pantalla.CartaDetallada.passId(Id))

            //userViewModel.addToFavList(id) // Añade el elemento a favoritos
            Log.d("BasicItemCard", "Clicked card with ID: ${Id}") // Registra el ID en la consola
        }
    ) {
        // Contenedor que rellena la tarjeta y la oscurece
        Box(
            modifier = Modifier
                .height(cardHeight)
                .background(Black)
        ) {
            // Imagen con recorte y altura dinámica
            Image(
                painter = rememberAsyncImagePainter(model = img), // Carga la imagen de forma asincrónica
                contentDescription = "$album album cover", // Descripción accesible
                contentScale = ContentScale.Crop, // Recorta la imagen para que se ajuste
                modifier = Modifier
                    .fillMaxWidth() // Mantiene la relación de aspecto (opcional)
                    .height(imageHeight) // Altura calculada
            )

            // Columna que alinea el texto en la parte inferior izquierda
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart) // Alineación inferior izquierda
                    .padding(8.dp)
                // Ajuste el padding según sea necesario
            ) {
                // Artista del álbum
                Text(
                    text = artista,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = MonsFontBold,
                        color = Letra
                    )
                )
                // Título del álbum
                Text(
                    text = album,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = MonsFont,
                        color = Letra
                    ),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HorizontalItemCard(
    Artista: String,
    Nombre: String,
    Img: String,
    Genero: String,
    Id: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    albumViewModel: FavsViewModel = viewModel()
) {
    // Dimensiones de la tarjeta
    val cardHeight = 70
    val cardWidth = 150

    // Dimensiones de la imagen
    val albumHeight = 60
    val albumWidth = 85

    // Tarjeta con borde redondeado y sombra
    Card(
        modifier = modifier
            .fillMaxWidth() // Ocupa el ancho máximo disponible
            .padding(2.dp) // Ajuste el padding según sea necesario
            .width(cardWidth.dp) // Ancho fijo
            .height(cardHeight.dp), // Altura fija
        shape = RoundedCornerShape(16.dp), // Bordes redondeados
        elevation = 6.dp, // Sombra
        onClick = { // Acción al hacer clic
            navController.navigate(route = Pantalla.CartaDetallada.passId(Id))
            //userViewModel.addToFavList(id) // Añade el elemento a favoritos
            Log.d(
                "BasicItemCard",
                "Clicked card with ID: ${Id}"
            ) // Registra el ID en la consola
        }
    ) {
        // Fila que organiza la imagen y el texto horizontalmente
        Row(
            modifier = Modifier
                .height(200.dp)
                .background(Black)
        ) {
            // Imagen del elemento
            Image(
                painter = rememberAsyncImagePainter(model = Img),
                contentDescription = null,
                contentScale = ContentScale.Inside, // Ajusta la imagen al tamaño del contenedor
                modifier = Modifier
                    .width(albumHeight.dp) // Ancho de la imagen
                    .height(albumWidth.dp)
                    .padding(start = 10.dp)// Altura de la imagen
            )
            // Columna que contiene el título, artista y género
            Column(
                modifier = Modifier
                    .fillMaxSize() // Ocupa todo el espacio disponible
                    .padding(12.dp), // Ajuste el padding según sea necesario
                Arrangement.Bottom // Alinea el texto en la parte inferior
            ) {
                // Título del elemento
                Text(
                    text = Nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MonsFontBold,
                    color = Letra
                )

                // Artista y género del elemento
                Text(
                    text = "$Artista | $Genero",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = MonsFontBold,
                    color = Letra
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentItem(
    Comentario: String,
    id: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    dataViewModel: DataViewModel = viewModel(),
    commentViewModel: CommentViewModel = viewModel()
) {

    // Dimensiones de la tarjeta
    val cardHeight = 190.dp
    val cardWidth = 158.dp

    val card2Height = 190.dp
    val card2Width = 188f.dp

    val getItems = dataViewModel.stateM.value
    val getComment = commentViewModel.state.value

    val selectedItem = getItems.find { it.Id == id }

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }
    val comment = getComment.idComment

    val img = selectedItem?.Img
    // Altura dinámica de la imagen
    val imageHeight = 140.dp
    val imageWidth = 140.dp// Calculate dynamic image height

    Row {
        // Tarjeta con borde redondeado, sombra y acción al hacer clic
        Card(
            modifier = modifier
                .fillMaxWidth() // Ocupa el ancho máximo disponible
                .padding(
                    start = 8.dp,
                    top = 4.dp,
                    end = 5.dp,
                    bottom = 4.dp
                ) // Ajuste el padding según sea necesario
                .width(cardWidth) // Ancho fijo
                .height(cardHeight), // Altura fija
            shape = RoundedCornerShape(10.dp), // Bordes redondeados
            elevation = 6.dp, // Sombra
            onClick = {
                navController.navigate(route = Pantalla.CartaDetallada.passId(id))
            }
        ) {
            // Contenedor que rellena la tarjeta y la oscurece
            Box(
                modifier = Modifier
                    .height(cardHeight)
                    .background(Black)
            ) {
                Row() {
                    // Imagen con recorte y altura dinámica
                    Image(
                        painter = rememberAsyncImagePainter(model = img), // Carga la imagen de forma asincrónica
                        contentDescription = "", // Descripción accesible
                        contentScale = ContentScale.Crop, // Recorta la imagen para que se ajuste
                        modifier = Modifier // Mantiene la relación de aspecto (opcional)
                            .height(imageHeight) // Altura calculada
                            .width(imageWidth)
                            .padding(start = 15.dp, top = 15.dp), // Anchura calculada
                    )
                }
                // Columna que alinea el texto en la parte inferior izquierda
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart) // Alineación inferior izquierda
                        .padding(8.dp) // Ajuste el padding según sea necesario
                ) {
                    // Título del álbum
                    if (selectedItem != null) {
                        androidx.compose.material.Text(
                            text = selectedItem.Nombre,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = MonsFontBold,
                                color = Letra
                            ),
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier
                                .padding(bottom = 7.dp, start = 5.dp)
                        )
                    }
                }
            }
        }

        Card(
            modifier = modifier
                .fillMaxWidth() // Ocupa el ancho máximo disponible
                .padding(4.dp) // Ajuste el padding según sea necesario
                .width(card2Width) // Ancho fijo
                .height(card2Height), // Altura fija
            shape = RoundedCornerShape(10.dp), // Bordes redondeados
            elevation = 6.dp, // Sombra
            onClick = {
                showConfirmDialog = true
                Log.d("Comment", "El comentario tiene el id")
            }
        ) {
            // Contenedor que rellena la tarjeta y la oscurece
            Box(
                modifier = Modifier
                    .height(cardHeight)
                    .background(DarkBlue)
            ) {
                Row() {
                    // Imagen con recorte y altura dinámica
                    Text(
                        text = Comentario,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = MonsFontBold,
                            color = Letra
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp)
                    )
                }
                // Columna que alinea el texto en la parte inferior izquierda
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart) // Alineación inferior izquierda
                        .padding(8.dp) // Ajuste el padding según sea necesario
                ) {
                }
            }
            if (showConfirmDialog) {
                AlertDialog(
                    onDismissRequest = { showConfirmDialog = false },
                    title = {
                        androidx.compose.material3.Text(
                            text = "Confirmar eliminación",
                            fontFamily = MonsFontBold
                        )
                    },
                    text = {
                        androidx.compose.material3.Text(
                            text = "¿Estás seguro de que deseas borrar el comentario?",
                            fontFamily = MonsFontBold
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showConfirmDialog = false
                                commentViewModel.deleteComment(comment)
                                navController.navigate(Pantalla.Comentarios.route)
                            }
                        ) {
                            androidx.compose.material3.Text(text = "Sí", fontFamily = MonsFontBold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showConfirmDialog = false }) {
                            androidx.compose.material3.Text(text = "No", fontFamily = MonsFontBold)
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedItem(
    Comentario: String,
    id: String,
    user: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    dataViewModel: DataViewModel = viewModel(),
    commentViewModel: CommentViewModel = viewModel()
) {

    // Dimensiones de la tarjeta
    val cardHeight = 190.dp
    val cardWidth = 165.dp

    val card2Height = 190.dp
    val card2Width = 200.dp

    val getItems = dataViewModel.stateM.value
    val getComment = commentViewModel.state.value

    val selectedItem = getItems.find { it.Id == id }

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }
    val comment = getComment.idComment

    val img = selectedItem?.Img
    // Altura dinámica de la imagen
    val imageHeight = 140.dp
    val imageWidth = 140.dp// Calculate dynamic image height

    Row {
        // Tarjeta con borde redondeado, sombra y acción al hacer clic
        Card(
            modifier = modifier
                .fillMaxWidth() // Ocupa el ancho máximo disponible
                .padding(
                    start = 8.dp,
                    top = 4.dp,
                    end = 5.dp,
                    bottom = 4.dp
                ) // Ajuste el padding según sea necesario
                .width(cardWidth) // Ancho fijo
                .height(cardHeight), // Altura fija
            shape = RoundedCornerShape(10.dp), // Bordes redondeados
            elevation = 6.dp, // Sombra
            onClick = {
                navController.navigate(route = Pantalla.CartaDetallada.passId(id))
            }
        ) {
            // Contenedor que rellena la tarjeta y la oscurece
            Box(
                modifier = Modifier
                    .height(cardHeight)
                    .background(Black)
            ) {
                Row() {
                    // Imagen con recorte y altura dinámica
                    Image(
                        painter = rememberAsyncImagePainter(model = img), // Carga la imagen de forma asincrónica
                        contentDescription = "", // Descripción accesible
                        contentScale = ContentScale.Crop, // Recorta la imagen para que se ajuste
                        modifier = Modifier // Mantiene la relación de aspecto (opcional)
                            .height(imageHeight) // Altura calculada
                            .width(imageWidth)
                            .padding(start = 15.dp, top = 15.dp), // Anchura calculada
                    )
                }
                // Columna que alinea el texto en la parte inferior izquierda
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart) // Alineación inferior izquierda
                        .padding(8.dp) // Ajuste el padding según sea necesario
                ) {
                    // Título del álbum
                    if (selectedItem != null) {
                        androidx.compose.material.Text(
                            text = selectedItem.Nombre,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = MonsFontBold,
                                color = Letra
                            ),
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier
                                .padding(bottom = 7.dp, start = 5.dp)
                        )
                    }
                }
            }
        }

        Card(
            modifier = modifier
                .fillMaxWidth() // Ocupa el ancho máximo disponible
                .padding(4.dp) // Ajuste el padding según sea necesario
                .width(card2Width) // Ancho fijo
                .height(card2Height), // Altura fija
            shape = RoundedCornerShape(10.dp), // Bordes redondeados
            elevation = 6.dp, // Sombra
            onClick = {
                showConfirmDialog = true
                Log.d("Comment", "El comentario tiene el id")
            }
        ) {
            // Contenedor que rellena la tarjeta y la oscurece
            Box(
                modifier = Modifier
                    .height(cardHeight)
                    .background(DarkBlue)
            ) {
                Column() {
                    Text(
                        text = user + ": ",
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontFamily = MonsFontBold,
                            color = Letra
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp)
                    )
                    // Imagen con recorte y altura dinámica
                    Text(
                        text = Comentario,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = MonsFontBold,
                            color = Letra
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp)
                    )
                }
                // Columna que alinea el texto en la parte inferior izquierda
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart) // Alineación inferior izquierda
                        .padding(8.dp) // Ajuste el padding según sea necesario
                ) {
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedItem2(
    Comentario: String,
    id: String,
    user: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    dataViewModel: DataViewModel = viewModel(),
    commentViewModel: CommentViewModel = viewModel()
) {
    val getItems = dataViewModel.stateM.value

    val card2Height = 130.dp
    val card2Width = 353.dp

    val selectedItem = getItems.find { it.Id == id }

    val img = selectedItem?.Img

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth() // Ocupa el ancho máximo disponible
            .padding(4.dp) // Ajuste el padding según sea necesario
            .width(card2Width) // Ancho fijo
            .height(card2Height), // Altura fija
        shape = RoundedCornerShape(10.dp), // Bordes redondeados
        elevation = 6.dp, // Sombra
        onClick = {showConfirmDialog = true}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black)
                .padding(start = 10.dp, top = 5.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = user,
                    style = TextStyle(fontFamily = MonsFont, fontSize = 16.sp),
                    color = Letra
                )
            }

            Spacer(modifier = Modifier.height(4.dp))


            Text(
                text = Comentario,
                style = TextStyle(fontFamily = MonsFontBold, fontSize = 14.sp),
                color = Letra
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(bottom = 7.dp, start = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = img),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (selectedItem != null) {
                    Text(
                        text = selectedItem.Nombre,
                        style = TextStyle(fontFamily = MonsFontBold, fontSize = 14.sp),
                        color = Letra
                    )
                }
            }
            if (showConfirmDialog) {
                AlertDialog(
                    onDismissRequest = { showConfirmDialog = false },
                    title = {
                        androidx.compose.material3.Text(
                            text = "Confirmar cambio de pantalla",
                            fontFamily = MonsFontBold
                        )
                    },
                    text = {
                        androidx.compose.material3.Text(
                            text = "¿Estás seguro de que deseas ver este álbum?",
                            fontFamily = MonsFontBold
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showConfirmDialog = false
                                navController.navigate(route = Pantalla.CartaDetallada.passId(id))
                            }
                        ) {
                            androidx.compose.material3.Text(text = "Sí", fontFamily = MonsFontBold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showConfirmDialog = false }) {
                            androidx.compose.material3.Text(text = "No", fontFamily = MonsFontBold)
                        }
                    }
                )
            }
        }

    }
}




