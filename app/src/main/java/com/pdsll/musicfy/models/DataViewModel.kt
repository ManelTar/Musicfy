package com.pdsll.musicfy.models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class DataViewModel: ViewModel(){
    val stateM = mutableStateOf<List<Album>>(emptyList())
    //val stateS = mutableStateOf<List<Series>>(emptyList())
    init {
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            stateM.value = getAllDataFromFirestore()
            //stateS.value = getAllSeriesFromFirestore()
        }
    }
}

suspend fun getAllDataFromFirestore(): List<Album> {
    val db = FirebaseFirestore.getInstance()
    val albumList = mutableListOf<Album>()

    try {
        val documentSnapshots = db.collection("Album").get().await()
        documentSnapshots.forEach { documentSnapshot ->
            val result = documentSnapshot.toObject<Album>()
            albumList.add(result)
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("Error", "getAllDataFromFirestore: $e")
        e.printStackTrace()
    }

    return albumList
}