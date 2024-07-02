package com.kuyayana.kuyayana

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.KuyaYanaTheme
import com.google.firebase.firestore.DocumentReference
import com.kuyayana.kuyayana.data.repository.TeacherRepository
import com.kuyayana.kuyayana.ui.view.appBars.KuyaYanaNavigationBar
import com.kuyayana.kuyayana.ui.view.appBars.KuyaYanaTopAppBar

import com.kuyayana.kuyayana.ui.view.CreateTeacherScreen
import com.kuyayana.kuyayana.ui.view.ScheduleScreen
import com.kuyayana.kuyayana.ui.view.SubjectScreen
import com.kuyayana.kuyayana.ui.view.TaskList
import com.kuyayana.kuyayana.ui.viewmodel.CategoryViewModel
import com.kuyayana.kuyayana.ui.viewmodel.SubjectViewModel
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel
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
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    var visible by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(true) }
    val currentScreen =
        KuyaYanaScreen.valueOf(backStackEntry?.destination?.route ?: KuyaYanaScreen.TaskList.name)

    Scaffold(
        topBar = {
            KuyaYanaTopAppBar(currentScreen = currentScreen, authViewModel = AuthViewModel())
        },
        bottomBar = {
            KuyaYanaNavigationBar(navController)
        },
        floatingActionButton = {

            FloatingActionButton(
                onClick = { expanded = !expanded },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Crossfade(targetState = expanded, label = "") { expanded->

                    Icon(
                        if (expanded) Icons.Filled.Add else Icons.Filled.Clear,
                        contentDescription = if (expanded)"Cerrar" else "Agregar"
                    )
                }
                AnimatedVisibility(
                    visible = !expanded,
                    enter = slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = 50)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = 50)
                    )
                ) {
                    // Botones adicionales encima del FAB
                    Column(
                        modifier = Modifier
                            //.align(Alignment.BottomEnd)
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                            .animateContentSize()
                    ) {
                        Button(
                            onClick = { /* Acción del primer botón */ },
                            modifier = Modifier
                                .padding(16.dp)
                        )
                        { Text("Botón 1") }

                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { /* Acción del segundo botón */ },modifier = Modifier
                            .padding(16.dp)) {
                            Text("Botón 2")
                        }
                    }
                }

            }
            Box(
                modifier = Modifier.animateContentSize()
            ) {





            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = KuyaYanaScreen.TaskList.name,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(route = KuyaYanaScreen.TaskList.name) {
                        TaskList(categoryViewModel = CategoryViewModel())
                    }
                    composable(route = KuyaYanaScreen.Calendar.name) {
                        //CalendarScreen()
                        CreateTeacherScreen(teacherViewModel = TeacherViewModel())
                    }
                    composable(route = KuyaYanaScreen.Schedule.name) {
                        ScheduleScreen()
                    }
                    composable(route = KuyaYanaScreen.Subject.name) {
                        SubjectScreen()
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    KuyaYanaTheme(darkTheme = false) {
        KuyaYanaApp()
    }
}
