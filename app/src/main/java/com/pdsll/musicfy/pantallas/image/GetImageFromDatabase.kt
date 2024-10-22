package com.pdsll.musicfy.pantallas.image

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdsll.musicfy.components.ProgressBar
import com.pdsll.musicfy.models.UserScreenViewModel
import com.pdsll.musicfy.models.Response.Loading
import com.pdsll.musicfy.models.Response.Success
import com.pdsll.musicfy.models.Response.Failure

@Composable
fun GetImageFromDatabase(
    viewModel: UserScreenViewModel = hiltViewModel(),
    createImageContent: @Composable (imageUrl: String)->Unit
){
    when (val getImageFromDatabaseResponse = viewModel.getImageFromDatabaseResponse){
        is Loading -> ProgressBar()
        is Success -> getImageFromDatabaseResponse.data?.let {imageUrl->
            createImageContent(imageUrl)
        }
        is Failure -> print(getImageFromDatabaseResponse.e)
    }
}