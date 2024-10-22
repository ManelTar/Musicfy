package com.pdsll.musicfy.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {

    val state = mutableStateOf(User()) // Objeto de usuario

    init {
        getDataUser()
    }

    private fun getDataUser() {
        viewModelScope.launch {
            // Obtener información del usuario actualmente autenticado
            val currentUser = FirebaseAuth.getInstance().currentUser

            // Verificar si el usuario está autenticado
            if (currentUser != null) {
                // Obtener UID del usuario autenticado
                val uid = currentUser.uid
                state.value = getDataFromUser(uid)
            }
        }
    }


    suspend fun getDataFromUser(userId: String): User {
        val db = FirebaseFirestore.getInstance()
        var user = User()

        try {
            val querySnapshot =
                db.collection("users").whereEqualTo("user_id", userId).get().await()

            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                user = documentSnapshot.toObject(User::class.java) ?: User()

                // Agregar logging para verificar si el nombre se recupera correctamente
                Log.d("getDataFromUser", "Nombre del usuario: ${user.user_id}")
            } else {
                Log.d("getDataFromUser", "Documento no encontrado para el usuario: $userId")
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "getDataFromUser: $e")
            e.printStackTrace()
        }

        return user
    }

    // ArrayList local para el carrito de compras

    val user = state.value

    fun updateUrl(userId: String, newImg: String) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            try {
                Log.d("updateUrl", "User con id: $userId")
                Log.d("updateUrl", "URL: $newImg")
                val docRef = db.collection("users").document(userId)

                // Update the "imageUrl" field with the new URL
                docRef.update("image", newImg).await()

                // Log success message
                Log.d("updateUrl", "URL successfully updated for user: $newImg")
            } catch (e: FirebaseFirestoreException) {
                Log.e("updateUrl", "Error updating URL: $e")
                e.printStackTrace()
            }
        }
    }

    // Coger nombre de usuario por el email
    suspend fun getUsernameByEmail(email: String): String {
        val db = FirebaseFirestore.getInstance()
        return try {
            val querySnapshot = db.collection("users").whereEqualTo("email", email).get().await()
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                val user = documentSnapshot.toObject(User::class.java)
                user?.username ?: ""  // Devuelve el nombre de usuario si existe
            } else {
                "Unknown"  // Devuelve un valor por defecto si no se encuentra el usuario
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e("getUsernameByEmail", "Error fetching username: $e")
            "Unknown"
        }
    }



}
