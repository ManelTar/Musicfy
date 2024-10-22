package com.pdsll.musicfy.pantallas

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.ItemContentPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pdsll.musicfy.getThemePreference
import com.pdsll.musicfy.saveThemePreference
import com.pdsll.musicfy.ui.theme.Black
import com.pdsll.musicfy.ui.theme.DarkBlue
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Purple80
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CambiarTema(navController: NavController, context: Context) {

    val list = listOf("Elige un tema", "Verde", "Azul", "Morado", "Rosa", "Gris")

    // Recupera el tema seleccionado de SharedPreferences
    var selectedTheme by remember { mutableStateOf(getThemePreference(context)) }

    var isExpanded by remember { mutableStateOf(false) }

    updateTheme(theme = selectedTheme)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RedTint),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .background(Red)){
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
                text = "Cambiar tema",
                fontSize = 32.sp,
                fontFamily = MonsFontBold,
                color = Letra,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 45.dp, bottom = 8.dp)
                    .wrapContentSize(Alignment.Center)
            )
        }
        Box(modifier = Modifier.padding(top= 50.dp)) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = !isExpanded })
            {
                TextField(
                    value = selectedTheme,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false })
                {
                    list.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text = text, color = Black) },
                            onClick = {
                                selectedTheme = list[index]
                                isExpanded = false
                                // Guarda el tema seleccionado en SharedPreferences
                                saveThemePreference(context, selectedTheme)
                            },
                            contentPadding = ItemContentPadding

                        )
                    }
                }
            }
        }
    }
}


fun updateTheme(theme: String) {
    when (theme) {
        "Azul" -> {
            Red = Color(0xFF245564)
            RedTint = Color(0xFF8BBDCC)
            Letra = Color(0xFFE0E3E7)
            Purple80 = Color(0xFF2888A5)
            DarkBlue = Color(0xFF142F38)
        }

        "Verde" -> {
            Red = Color(0xFF1B5749)
            RedTint = Color(0xFF56857A)
            Letra = Color(0xFFCBCCCE)
            Purple80 = Color(0xFF5D925E)
            DarkBlue = Color(0xFF0F3A30)
        }

        "Morado" -> {
            Red = Color(0xFF5E3C66)
            RedTint = Color(0xFF9D74A7)
            Letra = Color(0xFFCBCCCE)
            Purple80 = Color(0xFF5D6092)
            DarkBlue = Color(0xFF452A4B)
        }

        "Rosa" -> {
            Red = Color(0xFF8D5479)
            RedTint = Color(0xFFC48EB0)
            Letra = Color(0xFFE9E9E9)
            Purple80 = Color(0xFFAA62A6)
            DarkBlue = Color(0xFF663258)
        }

        "Gris" -> {
            Red = Color(0xFF797979)
            RedTint = Color(0xFFA5A5A5)
            Letra = Color(0xFFFAFAFA)
            Purple80 = Color(0xFF666666)
            DarkBlue = Color(0xFF424242)
        }
    }
}


