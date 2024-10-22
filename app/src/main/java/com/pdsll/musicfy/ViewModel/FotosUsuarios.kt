package com.pdsll.musicfy.ViewModel

import com.google.firebase.Timestamp

data class FotosUsuarios(
    val _id: String = "",
    val url: String = "",
    val createdAt: Timestamp = Timestamp.now()
)