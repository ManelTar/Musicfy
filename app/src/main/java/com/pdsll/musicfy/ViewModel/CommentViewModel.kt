package com.pdsll.musicfy.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.pdsll.musicfy.models.Album
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommentViewModel : ViewModel() {
    val state = mutableStateOf(Comment())
    val stateC = mutableStateOf<List<Comment>>(emptyList())
    val email = FirebaseAuth.getInstance().currentUser?.email

    val commentList = mutableListOf<Comment>()

    fun generarStringAleatorio(longitud: Int): String {
        val caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..longitud)
            .map { caracteresPermitidos.random() }
            .joinToString("")
    }

    init {
        getSpecificDataLista()
        Log.d("CommentList", "Lista ${state.value}")
    }

    // Obtener datos de un usuario específico
    private fun getSpecificDataLista() {
        viewModelScope.launch {
            state.value = getDataLista(email)
            stateC.value = getAllDataFromFirestore()
        }
    }

    suspend fun getDataLista(id: String?): Comment {
        val db = FirebaseFirestore.getInstance()
        var lista = Comment()

        try {
            val querySnapshot =
                db.collection("Comments").whereEqualTo("email", id).get().await()
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                lista = documentSnapshot.toObject(Comment::class.java) ?: Comment()
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("error", "getDataFromComments: $e")
            e.printStackTrace()
        }

        return lista
    }

    suspend fun getAllDataFromFirestore(): List<Comment> {
        val db = FirebaseFirestore.getInstance()
        val itemList = mutableListOf<Comment>()

        try {
            val documentSnapshots = db.collection("Comments").get().await()
            documentSnapshots.forEach { documentSnapshot ->
                val result = documentSnapshot.toObject<Comment>()
                itemList.add(result)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("Error", "getAllDataFromFirestore: $e")
            e.printStackTrace()
        }

        return itemList
    }

    // Método para agregar un comentario a un álbum específico
    fun addComment(albumId: String, comment: String) {

        val idComment = generarStringAleatorio(20)
        // Crear un nuevo comentario
        val newComment = Comment(comment, email, albumId, idComment)

        // Agregar el comentario a la lista local
        commentList.add(newComment)

        // Agregar el comentario a Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("Comments").document(idComment).set(newComment).
        addOnSuccessListener {
            Log.d("addComment", "Comentario agregado con éxito")
        }.addOnFailureListener { e ->
            Log.w("addComment", "Error al agregar comentario", e)
        }
    }

    fun deleteComment(idComment: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Comments").document(idComment).delete().addOnSuccessListener {
            Log.d("deleteComment", "Comentario borrado con éxito")
            // Actualiza la lista local y el estado después de eliminar el comentario
            commentList.removeAll { it.idComment == idComment }
            stateC.value = commentList.toList()
        }.addOnFailureListener { e ->
            Log.w("deleteComment", "Error al borrar comentario", e)
        }
    }
}