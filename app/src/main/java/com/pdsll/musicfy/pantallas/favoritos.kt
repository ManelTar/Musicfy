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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CropLandscape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.pdsll.musicfy.ViewModel.FavsViewModel
import com.pdsll.musicfy.models.Album
import com.pdsll.musicfy.models.DataViewModel
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.Black
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint

@Composable
fun AlbumFavScreen(
    navController: NavHostController,
    dataViewModel: DataViewModel = viewModel(),
    FavsViewModel: FavsViewModel = viewModel()

) {
    val favs = FavsViewModel.state.value
    val albumesFavs: List<String> = favs.albumes
    Log.d("Album", "Albumes favs ${albumesFavs}")

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Red)
    ) {
        Text(
            text = "MUSICFY",
            fontSize = 36.sp,
            fontFamily = MonsFontBold,
            color = Letra,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 12.dp)
                .wrapContentSize(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(30f) // Expandir a la altura máxima
                .background(RedTint) // Fondo blanco
                //padding( = 16.dp) // Margen superior
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {

                items(albumesFavs) { albumId ->
                    Log.d("Album", "Album id ${albumId}")
                    val album = dataViewModel.stateM.value.find { it.Id == albumId }
                    Log.d("Album", "Album ${album}")
                    if (album != null) {
                        AlbumItem(album = album,
                            navController = navController)
                    } else {
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(70.dp))
    }

}



// Composable para mostrar un elemento de producto
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumItem(album: Album,
              modifier: Modifier = Modifier,
              navController: NavController,) {

    // Dimensiones de la tarjeta
    val cardHeight = 200.dp
    val cardWidth = 140.dp

    // Altura dinámica de la imagen
    val imageHeight = 120.dp // Calculate dynamic image height

    // Tarjeta con borde redondeado, sombra y acción al hacer clic
    androidx.compose.material.Card(
        modifier = modifier
            .fillMaxWidth() // Ocupa el ancho máximo disponible
            .padding(4.dp) // Ajuste el padding según sea necesario
            .width(cardWidth) // Ancho fijo
            .height(cardHeight), // Altura fija
        shape = RoundedCornerShape(10.dp), // Bordes redondeados
        elevation = 6.dp, // Sombra
        onClick = { // Acción al hacer clic
            navController.navigate(route = Pantalla.CartaDetallada.passId(album.Id))
            //userViewModel.addToFavList(id) // Añade el elemento a favoritos
            Log.d("BasicItemCard", "Clicked card with ID: ${album.Id}") // Registra el ID en la consola
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
                painter = rememberAsyncImagePainter(model = album.Img), // Carga la imagen de forma asincrónica
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
                    .padding(8.dp) // Ajuste el padding según sea necesario
            ) {
                // Título del álbum
                androidx.compose.material.Text(
                    text = album.Nombre,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = MonsFontBold,
                        color = Letra
                    ),
                )
                // Artista del álbum
                androidx.compose.material.Text(
                    text = album.Artista,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = MonsFontBold,
                        color = Letra
                    )
                )
            }
        }
    }
}