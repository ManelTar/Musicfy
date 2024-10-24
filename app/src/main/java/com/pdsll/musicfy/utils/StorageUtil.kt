package com.pdsll.musicfy.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.util.UUID

class StorageUtil {

    companion object{

        fun uploadToStorage(uri: Uri, context: Context, type: String){

            var storage = Firebase.storage

            var storageRef = storage.reference

            val unique_image_name = UUID.randomUUID()
            var spaceRef: StorageReference

            if(type == "image"){
                spaceRef = storageRef.child("images/$unique_image_name.jpg")
            }else{
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let {
                var uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener{
                    Toast.makeText(
                        context,
                        "Fallo al subir la imágen.",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(
                        context,
                        "Imágen subida correctamente.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}