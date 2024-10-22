package com.pdsll.musicfy.dataClass

import android.net.Uri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.pdsll.musicfy.ViewModel.UserViewModel
import com.pdsll.musicfy.models.Response
import com.pdsll.musicfy.repository.AddImageToStorageResponse
import com.pdsll.musicfy.repository.AddImageUrlToFirestoreResponse
import com.pdsll.musicfy.repository.GetImageFromFirestoreResponse
import com.pdsll.musicfy.repository.ImageRepository
import com.pdsll.musicfy.utils.Constants.COLECCION
import com.pdsll.musicfy.utils.Constants.CREATED_AT
import com.pdsll.musicfy.utils.Constants.IMAGES
import com.pdsll.musicfy.utils.Constants.IMAGE_NAME
import com.pdsll.musicfy.utils.Constants.UID
import com.pdsll.musicfy.utils.Constants._id
import com.pdsll.musicfy.utils.Constants.display_name
import com.pdsll.musicfy.utils.Constants.email
import com.pdsll.musicfy.utils.Constants.image
import com.pdsll.musicfy.utils.Constants.user_id
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore
): ImageRepository {
    override suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse {
        return try{
            val downloadUrl = storage.reference.child(IMAGES).child(IMAGE_NAME)
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Response.Success(downloadUrl)
        }
        catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun addImageUrlToFirestore(download: Uri): AddImageUrlToFirestoreResponse {
        return try{
            val db = FirebaseFirestore.getInstance()

            val docRef = db.collection("users").document(UID)
            // Actualiza el documento en la colección

            docRef.update("image", download).await()

            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun getImageUrlFromFirestore(): GetImageFromFirestoreResponse {
        return try{
            val imageUrl = db.collection(COLECCION).document(UID).get().await().getString(image)
            Response.Success(imageUrl)
        }
        catch(e:Exception){
            Response.Failure(e)
        }
}
}
