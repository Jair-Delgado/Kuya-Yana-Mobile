package com.kuyayana.kuyayana

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kuyayana.kuyayana.ui.theme.KuyaYanaTheme
import com.kuyayana.kuyayana.ui.view.appBars.KuyaYanaNavigationBar
import com.kuyayana.kuyayana.ui.view.appBars.KuyaYanaTopAppBar
import com.kuyayana.kuyayana.ui.view.CalendarScreen
import com.kuyayana.kuyayana.ui.view.ScheduleScreen
import com.kuyayana.kuyayana.ui.view.SubjectScreen
import com.kuyayana.kuyayana.ui.view.TaskList
import com.kuyayana.kuyayana.ui.viewmodel.auth.AuthViewModel


enum class KuyaYanaScreen(val title: Int){
        TaskList(title = R.string.tareas),
        Calendar(title = R.string.calendario),
        Schedule(title = R.string.horario),
        Subject(title = R.string.asignaturas)
    }

    @Composable
    fun KuyaYanaApp(
        navController: NavHostController = rememberNavController(),
        modifier: Modifier = Modifier
    ){
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = KuyaYanaScreen.valueOf(backStackEntry?.destination?.route ?: KuyaYanaScreen.TaskList.name)

        Scaffold(
            topBar = {
               KuyaYanaTopAppBar(currentScreen = currentScreen, authViewModel = AuthViewModel())
            },
            bottomBar = {
                KuyaYanaNavigationBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = KuyaYanaScreen.TaskList.name,
                modifier = Modifier.padding(innerPadding)

            ){
                composable(route = KuyaYanaScreen.TaskList.name){
                    TaskList()
                }
                composable(route = KuyaYanaScreen.Calendar.name){
                    CalendarScreen()
                }
                composable(route = KuyaYanaScreen.Schedule.name){
                    ScheduleScreen()
                }
                composable(route = KuyaYanaScreen.Subject.name){
                    SubjectScreen()
                }

            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        KuyaYanaTheme (darkTheme = false){
            KuyaYanaApp()
        }
    }



