package com.pdsll.musicfy

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pdsll.musicfy.models.DataViewModel
import com.pdsll.musicfy.pantallas.HorizontalItemCard
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Busqueda(
    navController: NavController,
    dataViewModel: DataViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Red)
            .padding(bottom = 60.dp)
    ) {
        // Header
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

        // Variables para la búsqueda y los elementos a mostrar
        var searchTerm by remember { mutableStateOf("") } // Almacena el término de búsqueda actual
        val items = dataViewModel.stateM.value // Obtiene los elementos del ViewModel
        val filteredItems = if (searchTerm.isNotBlank()) {
            items.filter {
                it.Nombre.contains(searchTerm, ignoreCase = true) ||
                        it.Artista.contains(searchTerm, ignoreCase = true) ||
                        it.Genero.contains(searchTerm, ignoreCase = true)
            } // Filtra los elementos según el término de búsqueda
        } else {
            items // Si no hay búsqueda, muestra todos los elementos
        }

        Log.d("PantallaLista", "Número de elementos antes de LazyColumn: ${items.size}")

        // Estructura de la pantalla
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(RedTint) // Azul suavecito (comentado)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {

                // Barra de búsqueda
                OutlinedTextField(
                    value = searchTerm,
                    onValueChange = { searchTerm = it },
                    placeholder = { Text("¿Qué álbum vas a mirar hoy?",
                        fontFamily = MonsFontBold,
                        fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Búsqueda") },
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .background(Color.White, RoundedCornerShape(22.dp)), // Add some padding to the left and right of the text field
                )

                // Muestra los elementos filtrados o un mensaje si no hay resultados
                if (filteredItems.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(2.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items(filteredItems) { item ->
                            HorizontalItemCard(
                                Artista = item.Artista,
                                Img = item.Img,
                                Nombre = item.Nombre,
                                Genero = item.Genero,
                                Id = item.Id,
                                navController = navController
                            ) // Muestra cada elemento en una tarjeta horizontal
                        }
                    }

                } else {
                    // Mensaje cuando no hay resultados de búsqueda
                    if (searchTerm.isNotBlank()) {
                        Text("No hay resultados para la búsqueda: '$searchTerm'")
                    } else {
                        // Mostrar todos los albumes si no hay término de búsqueda
                        Text("Mostrando todos los albumes disponibles.")
                    }
                }

            }
        }
    }
}


