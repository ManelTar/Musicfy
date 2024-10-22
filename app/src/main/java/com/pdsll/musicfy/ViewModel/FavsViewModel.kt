package com.pdsll.musicfy.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FavsViewModel : ViewModel() {
    val state = mutableStateOf(Fav())
    private val _mutableList = mutableStateListOf<String>()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val authStateListener: FirebaseAuth.AuthStateListener

    init {
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                getSpecificDataLista(currentUser.email!!)
            } else {
                state.value = Fav() // Reset state if no user is logged in
                _mutableList.clear()
            }
        }
        auth.addAuthStateListener(authStateListener)
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authStateListener)
    }

    // Obtener datos de un usuario específico
    private fun getSpecificDataLista(userEmail: String) {
        viewModelScope.launch {
            val userDocRef = FirebaseFirestore.getInstance().collection("Fav").document(userEmail)
            userDocRef.addSnapshotListener { documentSnapshot, e ->
                if (e != null) {
                    Log.w("getSpecificDataLista", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val fav = documentSnapshot.toObject(Fav::class.java)!!
                    state.value = fav
                    _mutableList.clear()
                    _mutableList.addAll(fav.albumes)
                } else {
                    Log.d("getSpecificDataLista", "Current data: null")
                }
            }
        }
    }

    suspend fun getDataLista(id: String?): Fav {
        val db = FirebaseFirestore.getInstance()
        var lista = Fav()

        try {
            val querySnapshot = db.collection("Fav").whereEqualTo("_id", id).get().await()
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                Log.d("documentSnapshot", "documentSnapshot: $documentSnapshot")
                lista = documentSnapshot.toObject(Fav::class.java) ?: Fav()
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "getDataFromFav: $e")
            e.printStackTrace()
        }

        return lista
    }

    // Método para agregar un álbum a la lista de favoritos del usuario actual
    fun updateAlbums(albumId: String) {
        if (!_mutableList.contains(albumId)) {
            _mutableList.add(albumId)
            updateAlbumsInFirestore()
        }
    }

    fun deleteAlbum(albumId: String) {
        if (_mutableList.contains(albumId)) {
            _mutableList.remove(albumId)
            updateAlbumsInFirestore()
        } else {
            Log.d("Borrar", "El albumId no se encuentra en la lista")
        }
    }

    private fun updateAlbumsInFirestore() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.email
        currentUser?.let { email ->
            val userDocRef = FirebaseFirestore.getInstance().collection("Fav").document(email)
            userDocRef.update("albumes", _mutableList).addOnSuccessListener {
                Log.d("updateAlbumsInFirestore", "Lista de álbumes actualizada con éxito")
            }.addOnFailureListener { e ->
                Log.w("updateAlbumsInFirestore", "Error al actualizar la lista de álbumes", e)
            }
        }
    }

    fun isFavorite(albumId: String): Boolean {
        return _mutableList.contains(albumId)
    }

    fun toggleFavorite(albumId: String) {
        if (isFavorite(albumId)) {
            deleteAlbum(albumId)
        } else {
            updateAlbums(albumId)
        }
    }
}