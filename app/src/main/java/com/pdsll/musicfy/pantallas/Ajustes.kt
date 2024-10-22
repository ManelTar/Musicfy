package com.pdsll.musicfy

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pdsll.musicfy.ViewModel.UserViewModel
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.Black
import com.pdsll.musicfy.ui.theme.DarkBlue
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFont
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Purple80
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.Red80
import com.pdsll.musicfy.ui.theme.RedTint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Ajustes(
    navController: NavController,
) {

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
                    navController.navigate("perfil")
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
                text = "Ajustes",
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
            modifier = Modifier
                .fillMaxSize()
                .background(RedTint),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            androidx.compose.material3.Button(
                onClick = { navController.navigate(Pantalla.Tema.route) },
                modifier = Modifier
                    .width(220.dp)
                    .height(65.dp)
                    .padding(bottom = 15.dp),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(Purple80)
            ) {
                androidx.compose.material.Text(
                    text = "Cambiar tema",
                    color = Letra,
                    style = MaterialTheme.typography.button,
                    fontFamily = MonsFontBold
                )
            }
            androidx.compose.material3.Button(
                onClick = { navController.navigate(Pantalla.UpdateUser.route) },
                modifier = Modifier
                    .width(220.dp)
                    .height(65.dp)
                    .padding(bottom = 15.dp),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(Purple80)
            ) {
                androidx.compose.material.Text(
                    text = "Cambiar datos",
                    color = Letra,
                    style = MaterialTheme.typography.button,
                    fontFamily = MonsFontBold
                )
            }
            androidx.compose.material3.Button(
                onClick = { navController.navigate(Pantalla.UpdateContr.route) },
                modifier = Modifier
                    .width(220.dp)
                    .height(65.dp)
                    .padding(bottom = 15.dp),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(Purple80)
            ) {
                androidx.compose.material.Text(
                    text = "Cambiar contraseña",
                    color = Letra,
                    style = MaterialTheme.typography.button,
                    fontFamily = MonsFontBold
                )
            }
        }

    }
}
