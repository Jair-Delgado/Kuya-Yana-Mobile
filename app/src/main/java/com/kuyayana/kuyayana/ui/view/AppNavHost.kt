package com.kuyayana.kuyayana.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kuyayana.kuyayana.KuyaYanaApp

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("main")},
                onRegisterClick = {navController.navigate("register")}
            )
        }
        composable("register") {
            RegisterScreen(onRegisterSuccess = { navController.navigate("main") })
        }
        composable("main") {
            KuyaYanaApp()
        }
    }
}