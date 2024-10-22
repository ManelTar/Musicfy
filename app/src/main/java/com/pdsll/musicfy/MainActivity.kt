package com.pdsll.musicfy

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pdsll.musicfy.navigation.DETAIL_ARGUMENT_KEY
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.pantallas.AlbumFavScreen
import com.pdsll.musicfy.pantallas.CambiarTema
import com.pdsll.musicfy.pantallas.CartaDetallada
import com.pdsll.musicfy.pantallas.MainScreen
import com.pdsll.musicfy.pantallas.RecuContr
import com.pdsll.musicfy.pantallas.feed
import com.pdsll.musicfy.pantallas.image.Perfil
import com.pdsll.musicfy.pantallas.updateContr
import com.pdsll.musicfy.pantallas.updateTheme
import com.pdsll.musicfy.pantallas.updateUser
import com.pdsll.musicfy.pantallas.verComentarios
import com.pdsll.musicfy.ui.theme.MusicfyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recupera el tema seleccionado de SharedPreferences
        val selectedTheme = getThemePreference(this)
        // Aplica el tema seleccionado
        updateTheme(selectedTheme)

        setContent {
            MusicfyTheme() {
                MainScreen()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenNavigationConfiguration(navController: NavHostController, currentIndex: MutableIntState) {

    NavHost(navController = navController, startDestination = Pantalla.LogIn.route) {

        composable(Pantalla.Inicio.route) {
            Inicio(navController = navController)
        }

        composable(Pantalla.Busqueda.route) {
            Busqueda(navController = navController)
        }

        composable(Pantalla.Ajustes.route) {
            Ajustes(navController = navController)
        }

        composable(Pantalla.LogIn.route) {
            LogIn(navController = navController)
        }

        composable(Pantalla.SignUp.route) {
            SignUp(navController = navController)
        }

        composable(Pantalla.Perfil.route) {
            Perfil(navController = navController)
        }
        composable(
            route = Pantalla.CartaDetallada.route,
            arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        )
        {
            val id = it.arguments?.getInt(DETAIL_ARGUMENT_KEY).toString()
            Log.d("Args", it.arguments?.getInt(DETAIL_ARGUMENT_KEY).toString())
            CartaDetallada(navController = navController, id)
        }
        composable(Pantalla.RecuContr.route) {
            RecuContr(navController = navController)
        }
        composable(Pantalla.favoritos.route) {
            AlbumFavScreen(navController = navController)
        }
        composable(Pantalla.Comentarios.route) {
            verComentarios(navController = navController)
        }
        composable("update_user") {
            updateUser(
                navController = navController,
                onDeleteSuccess = {
                    navController.navigate(Pantalla.LogIn.route) {
                        popUpTo(Pantalla.LogIn.route) { inclusive = true }
                    }
                }
            )
        }
        composable("tema") {
            val context = LocalContext.current
            CambiarTema(navController = navController, context)
        }
        composable("update_contr") {
            updateContr(navController = navController)
        }
        composable("feed") {
            feed(navController = navController)
        }
    }

}

