package com.pdsll.musicfy.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.pdsll.musicfy.ViewModel.CommentViewModel
import com.pdsll.musicfy.ViewModel.UserViewModel
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint

@Composable
fun feed(
    commentViewModel: CommentViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    navController: NavController
) {
    val getComents = commentViewModel.stateC.value
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

    // Filtra los comentarios que no son del usuario autenticado
    val getComments = getComents.filter { it.email != currentUserEmail }

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
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RedTint)
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .background(RedTint)
                    ) {
                        // Usamos LaunchedEffect para obtener el nombre de usuario de cada comentario
                        getComments.forEach { comment ->
                            var username by remember { mutableStateOf("Loading...") }

                            LaunchedEffect(comment.email) {
                                username = userViewModel.getUsernameByEmail(comment.email.toString())
                            }

                            FeedItem2(
                                comment.comment,
                                comment.id_,
                                username,  // Pasamos el nombre de usuario en vez del email
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
