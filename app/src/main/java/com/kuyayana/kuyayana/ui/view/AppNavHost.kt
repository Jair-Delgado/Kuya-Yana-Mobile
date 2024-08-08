package com.kuyayana.kuyayana.ui.view

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.kuyayana.kuyayana.KuyaYanaApp
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.routes.KuyaYanaScreen
import com.kuyayana.kuyayana.ui.viewmodel.EventViewModel
import com.kuyayana.kuyayana.ui.viewmodel.SubjectViewModel
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel
import com.kuyayana.kuyayana.ui.viewmodel.auth.AuthViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
){
     val auth = FirebaseAuth.getInstance()
     var isAuth: String = ""
    if (auth.currentUser != null){
        isAuth = KuyaYanaScreen.Main.name
    }else {
        isAuth = KuyaYanaScreen.Login.name
    }
    NavHost(
        navController = navController,

        startDestination = isAuth

    ) {
        composable(route = KuyaYanaScreen.Login.name) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(KuyaYanaScreen.Main.name)},
                onRegisterClick = {navController.navigate(KuyaYanaScreen.Register.name)}
            )
        }
        composable(route = KuyaYanaScreen.Register.name) {
            RegisterScreen(onRegisterSuccess = { navController.navigate(KuyaYanaScreen.Main.name)},
                onLoginClick =
                    { navController.navigate(KuyaYanaScreen.Login.name) }
                )
        }
        composable(route = KuyaYanaScreen.Main.name) {
            KuyaYanaApp(
                onLogOutClick = {navController.navigate(KuyaYanaScreen.Login.name)}
            )

        }
    }
}