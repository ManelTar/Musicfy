package com.pdsll.musicfy.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FotosUsuariosViewModel : ViewModel() {
    val state = mutableStateOf(FotosUsuarios())
    val uid = FirebaseAuth.getInstance().currentUser?.uid


    init {
        getSpecificDataFoto()
    }


    // Obtener datos de un usuario específico
    private fun getSpecificDataFoto() {
        viewModelScope.launch {
            state.value = getDataFoto(uid)
        }
    }

    suspend fun getDataFoto(id: String?): FotosUsuarios {
        val db = FirebaseFirestore.getInstance()
        var foto = FotosUsuarios()

        try {
            val querySnapshot =
                db.collection("fotos_de_perfil").whereEqualTo("_id", id).get().await()
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                foto = documentSnapshot.toObject(FotosUsuarios::class.java) ?: FotosUsuarios()
            }
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
        }

        return foto
    }
}