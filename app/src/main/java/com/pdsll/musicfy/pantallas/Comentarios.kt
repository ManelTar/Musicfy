package com.pdsll.musicfy.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.pdsll.musicfy.ViewModel.CommentViewModel
import com.pdsll.musicfy.models.DataViewModel
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint

@Composable
fun verComentarios(
    commentViewModel: CommentViewModel = viewModel(),
    navController: NavController
) {

    // Variables para acceder a los datos de Album y Usuarios
    val getComents = commentViewModel.stateC.value
    val email = FirebaseAuth.getInstance().currentUser?.email


    // Variables para hacer filtros
    //val getAlbumsId = getAlbums.filter { it.Id == getComents.id_ }
    val getComments = getComents.filter { it.email == email }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Red)
            .padding(vertical = 10.dp)
    ) {
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

        LazyColumn(modifier = Modifier
            .background(RedTint)
            .fillMaxSize()) {
            // Sección de álbumes Hip Hop
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RedTint)
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Comentarios",
                        fontSize = 18.sp,
                        fontFamily = MonsFontBold,
                        color = Letra,
                        modifier = Modifier
                            .padding(start = 3.dp, bottom = 5.dp, end = 55.dp)
                            .align(Alignment.End)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .background(RedTint)
                    ) {
                        getComments.forEach { getComents ->
                            CommentItem(
                                getComents.comment,
                                getComents.id_,
                                navController = navController
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(69.dp))
            }
        }
    }
}