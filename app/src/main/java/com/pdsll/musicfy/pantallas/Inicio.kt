package com.pdsll.musicfy

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pdsll.musicfy.ViewModel.UserViewModel
import com.pdsll.musicfy.models.Album
import com.pdsll.musicfy.models.DataViewModel
import com.pdsll.musicfy.pantallas.BasicItemCard
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Inicio(navController: NavController,
           dataViewModel: DataViewModel = viewModel(),
           userViewModel : UserViewModel = viewModel()
) {

    // Variables para acceder a los datos de Album y Usuarios
    val getItems = dataViewModel.stateM.value
    val getUser = userViewModel.user

    // Variables para hacer filtros
    val getItemsHipHop = getItems.filter { it.Genero == "Hip Hop" }
    val getItemsPop = getItems.filter { it.Genero == "Pop" }
    val getItemsPostpunk = getItems.filter { it.Genero == "Post-punk" }
    val getItemsMetal = getItems.filter { it.Genero == "Metal" }
    val getItemsJazz = getItems.filter { it.Genero == "Jazz" }
    val getItemsIndieRock = getItems.filter { it.Genero == "Indierock" }
    val getItemsHyperpop = getItems.filter { it.Genero == "Hyperpop" }
    val getItemsOST = getItems.filter { it.Genero == "OST" }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Red)
            .padding(top = 10.dp), // Background color
        verticalArrangement = Arrangement.Top, // Align elements to the top
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "MUSICFY",
            fontSize = 36.sp,
            fontFamily = MonsFontBold,
            color = Letra,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .wrapContentSize(Alignment.Center)
        )

        // LazyColumn para poder deslizar
        LazyColumn(modifier = Modifier.background(RedTint)) {


            // Sección de álbumes Hip Hop
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material.Text(
                        text = "Hip Hop",
                        fontSize = 16.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        getItemsHipHop.forEach { getItems ->
                            BasicItemCard(
                                getItems.Img,
                                getItems.Artista,
                                getItems.Nombre,
                                getItems.Id,
                                getItems.Link,
                                navController = navController
                            )
                        }
                    }
                }
            }

            // Sección de álbumes Pop
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material.Text(
                        text = "Pop",
                        fontSize = 16.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        getItemsPop.forEach { getItems ->
                            BasicItemCard(
                                getItems.Img,
                                getItems.Artista,
                                getItems.Nombre,
                                getItems.Id,
                                getItems.Link,
                                navController = navController
                            )
                        }
                    }
                }
            }


            // Sección de álbumes Punk
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material.Text(
                        text = "Post-punk",
                        fontSize = 16.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        getItemsPostpunk.forEach { getItems ->
                            BasicItemCard(
                                getItems.Img,
                                getItems.Artista,
                                getItems.Nombre,
                                getItems.Id,
                                getItems.Link,
                                navController = navController
                            )
                        }
                    }
                }
            }


            // Sección de álbumes Metal
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material.Text(
                        text = "Metal",
                        fontSize = 16.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        getItemsMetal.forEach { getItems ->
                            BasicItemCard(
                                getItems.Img,
                                getItems.Artista,
                                getItems.Nombre,
                                getItems.Id,
                                getItems.Link,
                                navController = navController
                            )
                        }
                    }
                }
            }


            // Sección de álbumes Jazz
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material.Text(
                        text = "Jazz",
                        fontSize = 16.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        getItemsJazz.forEach { getItems ->
                            BasicItemCard(
                                getItems.Img,
                                getItems.Artista,
                                getItems.Nombre,
                                getItems.Id,
                                getItems.Link,
                                navController = navController
                            )
                        }
                    }
                }
            }

            // Sección de álbumes Indie Rock
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material.Text(
                        text = "Indie Rock",
                        fontSize = 16.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        getItemsIndieRock.forEach { getItems ->

                            BasicItemCard(
                                getItems.Img,
                                getItems.Artista,
                                getItems.Nombre,
                                getItems.Id,
                                getItems.Link,
                                navController = navController
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material.Text(
                        text = "Hyperpop",
                        fontSize = 16.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        getItemsHyperpop.forEach { getItems ->
                            BasicItemCard(
                                getItems.Img,
                                getItems.Artista,
                                getItems.Nombre,
                                getItems.Id,
                                getItems.Link,
                                navController = navController
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material.Text(
                        text = "Bandas sonoras",
                        fontSize = 16.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        getItemsOST.forEach { getItems ->
                            BasicItemCard(
                                getItems.Img,
                                getItems.Artista,
                                getItems.Nombre,
                                getItems.Id,
                                getItems.Link,
                                navController = navController
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(96.dp))
            }
        }
    }
}

fun saveThemePreference(context: Context, theme: String) {
    val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("selected_theme", theme)
    editor.apply()
}

fun getThemePreference(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("selected_theme", "Elige un tema") ?: "Elige un tema"
}

