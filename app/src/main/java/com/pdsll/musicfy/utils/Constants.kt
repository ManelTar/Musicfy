package com.pdsll.musicfy.utils

import com.google.firebase.auth.FirebaseAuth

object Constants {
    val currentUser = FirebaseAuth.getInstance().currentUser
    var IMAGES ="fotos_de_perfil"
    var COLECCION="users"
    const val image ="image"
    const val CREATED_AT ="createdAt"
    var UID= currentUser!!.uid
    var IMAGE_NAME="$UID.jpg"
    const val ALL_IMAGES ="image/*"
    const val _id ="_id"
    val user_id= "user_id"
    val email = "email"
    val display_name = "display_name"
}