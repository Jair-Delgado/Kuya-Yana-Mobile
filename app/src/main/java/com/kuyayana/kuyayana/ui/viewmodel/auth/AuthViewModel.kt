package com.kuyayana.kuyayana.ui.viewmodel.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.kuyayana.kuyayana.R
import com.kuyayana.kuyayana.data.Task
import com.kuyayana.kuyayana.data.models.User
import com.kuyayana.kuyayana.data.routes.KuyaYanaScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AuthViewModel(): ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    //private lateinit var googleSignInClient: GoogleSignInClient

    //LOGIN
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    //REGISTER
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    /*init {
        configureGoogleSignIn()
    }

     fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("82558435317-6ra3dj3irg96lh3t4scubs01bsdkue17.apps.googleusercontent.com")
            .requestScopes(Scope(CalendarScopes.CALENDAR))
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }
    fun getGoogleSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }
    // Manejar el resultado del inicio de sesión
    fun handleSignInResult(data: Intent) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            // Aquí puedes usar la cuenta para realizar operaciones en el calendario
        } catch (e: ApiException) {
            // Manejar error
        }
    }

*/
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            _loginState.value = LoginState.Success
                        }else{
                            _loginState.value = LoginState.Error(task.exception?.message ?: "Unknown error")
                        }
                    }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                _registerState.value = RegisterState.Loading
                val authResult = auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _registerState.value = RegisterState.Success
                        } else {
                            _registerState.value = RegisterState.Error(task.exception?.message ?: "Unknown error")
                        }
                    }
                    .await()

                val uid = authResult.user?.uid ?: ""
                val user = User(uid, email)
                db.collection("users").document(uid).set(user).await()

            } catch (e: Exception) {
                _registerState.value = RegisterState.Error(e.message ?: "Unknown error")
            }
        }
    }
    fun logout() {
        auth.signOut()

    }
}


sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}