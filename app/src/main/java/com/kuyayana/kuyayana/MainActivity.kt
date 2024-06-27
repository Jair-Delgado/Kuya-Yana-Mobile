package com.kuyayana.kuyayana


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.compose.KuyaYanaTheme
import com.google.firebase.FirebaseApp
import com.kuyayana.kuyayana.ui.view.AppNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            KuyaYanaTheme {
               //AppNavHost()
                KuyaYanaApp()
            }
        }
    }
}
