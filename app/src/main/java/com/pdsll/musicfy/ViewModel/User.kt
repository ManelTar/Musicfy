package com.pdsll.musicfy.ViewModel

// Data class representing a User
data class User(
    // Default values for properties
    val id: String? = "",
    val user_id: String = "",
    val display_name: String = "",
    val image: String = "",
    val email: String = "",
    val tlf: String = "",
    val username: String = ""
) {
    // Function to convert User object to a mutable map for Firestore
    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            // Map properties to corresponding keys
            "user_id" to this.user_id,
            "display_name" to this.display_name
        )
    }
}
