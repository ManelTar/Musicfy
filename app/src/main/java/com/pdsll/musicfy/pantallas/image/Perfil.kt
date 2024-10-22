package com.pdsll.musicfy.pantallas.image

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.pdsll.musicfy.ViewModel.FotosUsuariosViewModel
import com.pdsll.musicfy.ViewModel.LoginScreenViewModel
import com.pdsll.musicfy.ViewModel.UserViewModel
import com.pdsll.musicfy.models.UserScreenViewModel
import com.pdsll.musicfy.navigation.Pantalla
import com.pdsll.musicfy.ui.theme.Letra
import com.pdsll.musicfy.ui.theme.MonsFontBold
import com.pdsll.musicfy.ui.theme.Purple80
import com.pdsll.musicfy.ui.theme.Red
import com.pdsll.musicfy.ui.theme.RedTint
import com.pdsll.musicfy.utils.Constants
import com.pdsll.musicfy.utils.Constants.ALL_IMAGES

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Perfil(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    loginScreenViewModel: LoginScreenViewModel = viewModel(),
    fotosUsuariosViewModel: FotosUsuariosViewModel = viewModel(),
    userScreenViewModel: UserScreenViewModel = hiltViewModel()
) {
    val getUser = userViewModel.state.value
    val currentUser = FirebaseAuth.getInstance().currentUser
    val galleryLauncher = rememberLauncherForActivityResult(contract = GetContent()) { imageUri ->
        imageUri?.let {
            userScreenViewModel.addImageToStorage(imageUri)
        }

    }

    val fotosUsuario = fotosUsuariosViewModel.state.value


    var uri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Red), // Background color
        verticalArrangement = Arrangement.Top, // Align elements to the top
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(RedTint), // Background color
            verticalArrangement = Arrangement.Top, // Align elements to the top
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(top = 10.dp, start = 10.dp)
            ) {

                Image(
                    painter = rememberAsyncImagePainter(model = getUser.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Ajusta la imagen al tamaño del contenedor
                    modifier = Modifier
                        .width(180.dp)
                        .height(180.dp)
                        .clip(CircleShape)// Make the image circular
                )
                // Clickable area for the "mini button"
                Box(modifier = Modifier
                    .size(40.dp) // Adjust size as needed
                    .align(Alignment.BottomEnd) // Position in bottom-right corner
                    .padding(8.dp) // Add some padding for better visual separation
                    .background(Color.Transparent) // Make the area invisible
                    .clickable {
                        galleryLauncher.launch(ALL_IMAGES)


                        Constants.UID = getUser.user_id

                        Log.d("Fotos", "Url de la foto ${Constants.UID}")
                        userViewModel.updateUrl(getUser.user_id, fotosUsuario.url)

                    }) {
                    // Optional content for the "mini button" (e.g., Icon)
                    Icon(
                        imageVector = Icons.Default.Edit, // Use desired icon
                        contentDescription = "Edit Profile",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp) // Adjust icon size
                    )
                }
            }

            AddImageToStorage(addImageToDatabase = { downloadUrl ->
                userScreenViewModel.addImageToDatabase((downloadUrl))
                Log.d("DownloadUrl", "downloadUrl: $downloadUrl")
            })


            Spacer(modifier = Modifier.height(20.dp))
            // Second text
            Text(
                text = "Usuario: " + getUser.display_name,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    color = Letra,
                    fontFamily = MonsFontBold
                )// White text color
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Correo: " + getUser.email,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    color = Letra,
                    fontFamily = MonsFontBold
                )// White text color
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Teléfono: " + getUser.tlf,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 13.sp,
                    color = Letra,
                    fontFamily = MonsFontBold
                )// White text color
            )

        }
    }



    Box(
        modifier = Modifier
            .padding(0.dp, 0.dp, 10.dp, 130.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        androidx.compose.material3.Button(
            onClick = {
                navController.navigate(Pantalla.Ajustes.route)
            },
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(Purple80),
            modifier = Modifier
                .height(40.dp)
        ) {
            Text(
                text = "Ajustes",
                color = Letra,
                fontFamily = MonsFontBold
            ) // White text color
        }

    }

    Box(
        modifier = Modifier
            .padding(0.dp, 0.dp, 10.dp, 75.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        androidx.compose.material3.Button(
            onClick = {
                navController.navigate(Pantalla.Inicio.route)
                navController.navigate(Pantalla.LogIn.route)
                loginScreenViewModel.logOut()
            },
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(Purple80),
            modifier = Modifier
                .height(50.dp)
        ) {
            Text(
                text = "Cerrar sesión",
                color = Letra,
                fontFamily = MonsFontBold
            ) // White text color
        }

    }
}
