package com.pdsll.musicfy.pantallas.image

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdsll.musicfy.models.UserScreenViewModel
import com.pdsll.musicfy.models.Response.Failure
import com.pdsll.musicfy.models.Response.Success
import com.pdsll.musicfy.models.Response.Loading
import com.pdsll.musicfy.components.ProgressBar

@Composable
fun AddImageToStorage(
    viewModel: UserScreenViewModel = hiltViewModel(),
    addImageToDatabase: (download: Uri) -> Unit
) {
    when (val addImageToStorageResponse = viewModel.addImageToStorageResponse) {
        is Loading -> ProgressBar()
        is Success -> addImageToStorageResponse.data?.let { downloadUrl ->
            LaunchedEffect(
                downloadUrl
            ) { addImageToDatabase(downloadUrl) }
        }

        is Failure -> print(addImageToStorageResponse.e)
    }

}
