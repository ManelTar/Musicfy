package com.pdsll.musicfy.pantallas

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pdsll.musicfy.R
import com.pdsll.musicfy.SmoothAnimationBottomBar
import com.pdsll.musicfy.ScreenNavigationConfiguration
import com.pdsll.musicfy.dataClass.BottomBarProperties
import com.pdsll.musicfy.dataClass.SmoothAnimationBottomBarScreens
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.Barra
import com.pdsll.musicfy.ui.theme.BarraItems
import com.pdsll.musicfy.ui.theme.DarkBlue
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFont
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Purple80
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    // Create a NavController to manage navigation within the app
    val navController = rememberNavController()

    // Define the items for the bottom navigation bar
    val bottomNavigationItems = listOf(
        SmoothAnimationBottomBarScreens(
            Pantalla.Inicio.route,
            stringResource(id = R.string.inicio),
            R.drawable.baseline_home_24
        ),
        SmoothAnimationBottomBarScreens(
            Pantalla.Busqueda.route,
            stringResource(id = R.string.busca),
            R.drawable.search_black_24dp
        ),
        SmoothAnimationBottomBarScreens(
            Pantalla.favoritos.route,
            stringResource(id = R.string.fav),
            R.drawable.baseline_favorite_24
        ),
        SmoothAnimationBottomBarScreens(
            Pantalla.Feed.route,
            stringResource(id = R.string.feed),
            R.drawable.baseline_feed_24
        ),
        SmoothAnimationBottomBarScreens(
            Pantalla.Comentarios.route,
            stringResource(id = R.string.comentarios),
            R.drawable.baseline_mode_comment_24
        ),
        SmoothAnimationBottomBarScreens(
            Pantalla.Perfil.route,
            stringResource(id = R.string.perfil),
            R.drawable.baseline_person_24
        )
    )

    // Create a state to remember the current index of the selected bottom navigation item
    val currentIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    // Define the properties for the bottom navigation bar
    Scaffold(
        bottomBar = {
        SmoothAnimationBottomBar(
            navController,
            bottomNavigationItems,
            initialIndex = currentIndex,
            bottomBarProperties = BottomBarProperties(
                backgroundColor = Barra,
                indicatorColor = Color.White.copy(alpha = 0.2F),
                iconTintColor = BarraItems,
                iconTintActiveColor = Color.Black,
                textActiveColor = Color.Black,
                cornerRadius = 18.dp,
                fontFamily = MonsFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            onSelectItem = {
                // Log the selected item when it changes
                Log.i("SELECTED_ITEM", "onCreate: Selected Item ${it.name}")
            }
        )
    }) { innerPadding ->
        // Apply inner padding to the screen content
        Modifier.padding(innerPadding)
        // Configure the navigation based on the current state
        ScreenNavigationConfiguration(navController, currentIndex)
    }
}
