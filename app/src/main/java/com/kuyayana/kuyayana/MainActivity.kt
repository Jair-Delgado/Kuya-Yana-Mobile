package com.kuyayana.kuyayana


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.KuyaYanaTheme
import com.google.firebase.FirebaseApp
import com.kuyayana.kuyayana.ui.view.AppNavHost
import com.kuyayana.kuyayana.ui.viewmodel.auth.AuthViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            KuyaYanaTheme {
               AppNavHost()
               // KuyaYanaApp()
            }
        }
    }


}
