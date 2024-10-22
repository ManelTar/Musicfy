package com.pdsll.musicfy.ViewModel

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import com.pdsll.musicfy.models.Result
import kotlinx.coroutines.tasks.await

class LoginScreenViewModel : ViewModel() {
    // Firebase Authentication instance
    private val auth: FirebaseAuth = Firebase.auth
    private val _currentUser = MutableLiveData<FirebaseUser?>(null)
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    // LiveData to observe loading state
    private val _loading = MutableLiveData(false)

    private val _errorMessage = MutableLiveData<String?>()

    private val _deleteStatus = MutableLiveData<Result<Unit>>()
    val deleteStatus: LiveData<Result<Unit>> get() = _deleteStatus

    fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$"
        val passwordMatcher = Regex(passwordPattern)
        return passwordMatcher.find(password) != null
    }

    fun isEmailValid(email: String): Boolean {
        val emailPattern = "\\w+@\\w+\\.\\w+"
        val emailMatcher = Regex(emailPattern)
        return emailMatcher.find(email) != null
    }

    fun showAlertDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }


    init {
        // Comprobar si hay un usuario autenticado al iniciar
        _currentUser.value = auth.currentUser
    }

    // Function to sign in with email and password
    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        home: () -> Unit
    ) = viewModelScope.launch {
        try {
            if (email.isEmpty() || password.length < 6) {
                // Mostrar mensaje de error al usuario
                Toast.makeText(context, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT)
                    .show()
                _errorMessage.value =
                    "El email no puede estar vacío y la contraseña debe tener al menos 6 caracteres."
                return@launch
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    home()
                    _currentUser.value = auth.currentUser // Actualizar el usuario actual
                    _errorMessage.value = null // Limpiar cualquier mensaje de error
                } else {
                    Toast.makeText(context, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT)
                        .show()
                    _errorMessage.value = "Error al iniciar sesión: ${task.exception?.message}"
                    Log.d("Musicfy", "signInWithEmailPassword ${task.exception?.message}")
                }
            }
        } catch (ex: Exception) {
            Toast.makeText(context, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            _errorMessage.value = "Error al iniciar sesión: ${ex.message}"
            Log.d("Musicfy", "signInWithEmailPassword ${ex.message}")
        }
    }

    // Comprobar si el username está disponible
    suspend fun isUsernameAvailable(username: String): Boolean {
        val db = FirebaseFirestore.getInstance()
        return try {
            val querySnapshot = db.collection("users").whereEqualTo("username", username).get().await()
            querySnapshot.isEmpty  // Devuelve true si no hay documentos (disponible)
        } catch (e: FirebaseFirestoreException) {
            Log.e("isUsernameAvailable", "Error checking username availability: $e")
            false  // En caso de error, consideramos que no está disponible
        }
    }

    // Function to create a new user with email and password
    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        nombre: String,
        userName: String,
        tlf: String?,
        context: Context,
        home: () -> Unit
    ) = viewModelScope.launch {
        // Check loading state to prevent multiple simultaneous requests
        if (_loading.value == false) {
            _loading.value = true
            try {
                if (password.length < 6) {
                    showAlertDialog(context, "Error", "La contraseña debe tener al menos 6 caracteres.")
                    _loading.value = false
                    return@launch
                }

                if (!isPasswordValid(password)) {
                    showAlertDialog(context, "Error", "La contraseña debe contener al menos una letra mayúscula, una letra minúscula y un dígito.")
                    _loading.value = false
                    return@launch
                }

                if (!isEmailValid(email)) {
                    showAlertDialog(context, "Error", "Introduce un correo válido.")
                    _loading.value = false
                    return@launch
                }

                if (tlf != null) {
                    if (tlf.length != 9 || !tlf.all { it.isDigit() }) {
                        showAlertDialog(context, "Error", "Introduce correctamente el número de teléfono.")
                        _loading.value = false
                        return@launch
                    }
                }

                // Verificar disponibilidad del nombre de usuario
                if (!isUsernameAvailable(userName)) {
                    showAlertDialog(context, "Error", "El nombre de usuario ya está en uso. Por favor, elige otro.")
                    _loading.value = false
                    return@launch
                }

                // Crear usuario en Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val displayName = nombre
                            val tlfFormatted = "+34 $tlf"
                            createUser(displayName, tlfFormatted, userName)  // Llama a createUser
                            createFavLists()
                            home()
                        } else {
                            // User creation failed
                            Log.d("Musicfy", "createUserWithEmailAndPassword: ${task.exception?.message}")
                        }
                        _loading.value = false
                    }
            } catch (ex: Exception) {
                Toast.makeText(context, "Revisa los datos introducidos.", Toast.LENGTH_SHORT).show()
                _errorMessage.value = "Error al iniciar sesión: ${ex.message}"
                Log.d("Musicfy", "signInWithEmailPassword ${ex.message}")
                _loading.value = false
            }
        }
    }


    // Function to create a user document in Firestore
    private fun createUser(name: String?, tlf: String?, userName: String?) {
        val email = auth.currentUser?.email ?: return // Return if no email available
        val _userId = auth.currentUser?.uid
        val image = "https://cdn.icon-icons.com/icons2/3946/PNG/512/user_icon_250929.png"

        val userMap = mutableMapOf<String, Any>()
        userMap["user_id"] = _userId.toString()  // Use email as document name
        userMap["display_name"] = name.toString()
        userMap["email"] = email // Redundant, included for clarity
        userMap["tlf"] = tlf.toString()
        userMap["image"] = image
        userMap["username"] = userName.toString()

        // Add user document to Firestore collection
        FirebaseFirestore.getInstance().collection("users").document(_userId.toString())
            .set(userMap)
            .addOnSuccessListener {
                // Success callback, handle successful saving
            }.addOnFailureListener { exception ->
                // Error callback, handle any issues during saving
                Log.e("createUser", "Error saving user data: $exception")
            }
    }

    fun sendPasswordReset(email: String, home: () -> Unit) {
        // [START send_password_reset]
        val emailAddress = email

        com.google.firebase.Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Musicfy", "Email sent.")
                }
            }
        // [END send_password_reset]
    }

    fun changePassword(currentPassword: String, newPassword: String, context: Context) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Contraseña cambiada correctamente
                                    Toast.makeText(
                                        context,
                                        "Contraseña cambiada correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    // Error al cambiar la contraseña
                                    Toast.makeText(
                                        context,
                                        "Error al cambiar la contraseña",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        // Contraseña actual incorrecta
                        Toast.makeText(context, "Contraseña actual incorrecta", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    //Se crea la lista de favoritos vacía para el usuario
    private fun createFavLists() {
        val email = auth.currentUser?.email ?: return
        val list: List<String> = emptyList()

        val shoppingListMap = mutableMapOf<String, Any>()
        shoppingListMap["_id"] = email.toString()
        shoppingListMap["albumes"] = list



        FirebaseFirestore.getInstance().collection("Fav").document(email)
            .set(shoppingListMap).addOnSuccessListener {
                // Success callback, handle successful saving
            }.addOnFailureListener { exception ->
                // Error callback, handle any issues during saving
                Log.e("createUser", "Error saving user data: $exception")
            }
    }

    //Otras funcionalidades
    //LogOut
    fun logOut() {
        auth.signOut()
        _currentUser.value = null
    }

    fun updateUserFields(updates: Map<String, Any>) {
        val userId = auth.currentUser?.uid ?: return
        val userDocRef = FirebaseFirestore.getInstance().collection("users").document(userId)

        // Actualizar los campos en Firestore
        userDocRef.update(updates).addOnSuccessListener {
            Log.d("updateUserFields", "Campos actualizados con éxito")
        }.addOnFailureListener { exception ->
            Log.e("updateUserFields", "Error al actualizar campos: $exception")
        }
    }

    fun updateUser(name: String?, tlf: String?) {
        val updates = mutableMapOf<String, Any>()

        if (!name.isNullOrEmpty()) {
            updates["display_name"] = name
        }

        if (!tlf.isNullOrEmpty()) {
            updates["tlf"] = tlf
        }

        if (updates.isNotEmpty()) {
            updateUserFields(updates)
        }
    }

    fun deleteUserAccount() = viewModelScope.launch {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            try {
                // Eliminar los datos del usuario en Firestore
                deleteUserData(user.uid, {
                    // Después de eliminar los datos del usuario, eliminar el usuario de Firebase Authentication
                    user.delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                _deleteStatus.postValue(Result.Success(Unit))
                            } else {
                                task.exception?.let { exception ->
                                    _deleteStatus.postValue(Result.Failure(exception))
                                }
                            }
                        }
                }, { exception ->
                    _deleteStatus.postValue(Result.Failure(exception))
                })
            } catch (ex: Exception) {
                _deleteStatus.postValue(Result.Failure(ex))
            }
        } ?: run {
            _deleteStatus.postValue(Result.Failure(Exception("No authenticated user found")))
        }
    }

    private fun deleteUserData(
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
