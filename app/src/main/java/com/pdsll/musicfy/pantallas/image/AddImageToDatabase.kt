package com.pdsll.musicfy.pantallas.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdsll.musicfy.components.ProgressBar
import com.pdsll.musicfy.models.UserScreenViewModel
import com.pdsll.musicfy.models.Response.Loading
import com.pdsll.musicfy.models.Response.Success
import com.pdsll.musicfy.models.Response.Failure

@Composable
fun AddImageToDatabase(
    viewModel: UserScreenViewModel = hiltViewModel(),
    showSnackBar: (isImageAddedToDatabase : Boolean)-> Unit
){
    when (val addImageToDatabaseResponse = viewModel.addImageToDatabaseResponse){
        is Loading -> ProgressBar()
        is Success -> addImageToDatabaseResponse.data?.let{isImageAddedToDatabase->
            LaunchedEffect(isImageAddedToDatabase){
                showSnackBar(isImageAddedToDatabase)
            }
        }
        is Failure -> print(addImageToDatabaseResponse.e)
    }
}