package com.kuyayana.kuyayana

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.KuyaYanaTheme
import com.google.firebase.firestore.DocumentReference
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.repository.TeacherRepository
import com.kuyayana.kuyayana.data.routes.KuyaYanaScreen
import com.kuyayana.kuyayana.ui.view.CalendarScreen
import com.kuyayana.kuyayana.ui.view.appBars.KuyaYanaNavigationBar
import com.kuyayana.kuyayana.ui.view.appBars.KuyaYanaTopAppBar

import com.kuyayana.kuyayana.ui.view.TeacherScreen
import com.kuyayana.kuyayana.ui.view.EventsScreen
import com.kuyayana.kuyayana.ui.view.LoginScreen
import com.kuyayana.kuyayana.ui.view.ScheduleScreen
import com.kuyayana.kuyayana.ui.view.SubjectScreen
import com.kuyayana.kuyayana.ui.view.TaskList
import com.kuyayana.kuyayana.ui.view.TeacherListScreen
import com.kuyayana.kuyayana.ui.viewmodel.CategoryViewModel
import com.kuyayana.kuyayana.ui.viewmodel.EventViewModel
import com.kuyayana.kuyayana.ui.viewmodel.SubjectViewModel
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel
import com.kuyayana.kuyayana.ui.viewmodel.auth.AuthViewModel

@Composable
fun KuyaYanaApp(
    navController: NavHostController = rememberNavController(),
    //navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    var visible by remember { mutableStateOf(true) }
    var isOverlayVisible by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(true) }

    //val categories by categoryViewModel.categories.collectAsState()
    val currentScreen =
        KuyaYanaScreen.valueOf(backStackEntry?.destination?.route ?: KuyaYanaScreen.TaskList.name)

    Scaffold(
        topBar = {
            KuyaYanaTopAppBar(
                currentScreen = currentScreen,
                authViewModel = AuthViewModel(),
                navController = navController
            )
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
                        contentDescription = if (expanded) stringResource(R.string.cerrar) else stringResource(
                            R.string.agregar
                        )
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.BottomEnd
            ) {
                NavHost(
                    navController = navController,
                    startDestination = KuyaYanaScreen.TaskList.name,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(route = KuyaYanaScreen.TaskList.name) {
                        TaskList(eventViewModel = EventViewModel())
                    }
                    composable(route = KuyaYanaScreen.Calendar.name) {
                        CalendarScreen()
                        //CreateTeacherScreen(teacherViewModel = TeacherViewModel())
                    }
                    composable(route = KuyaYanaScreen.Schedule.name) {
                        ScheduleScreen()
                    }
                    composable(route = KuyaYanaScreen.Subject.name) {
                        SubjectScreen(
                            navController,
                            subjectViewModel = SubjectViewModel()
                        )
                    }
                    composable(route = KuyaYanaScreen.Event.name){
                        EventsScreen(
                            eventViewModel = EventViewModel(),
                            categoryName = Category()
                        )
                    }
                    composable(route = KuyaYanaScreen.Teacher.name){
                        TeacherScreen(teacherViewModel = TeacherViewModel())
                    }
                    composable(route = KuyaYanaScreen.TeacherList.name){
                       TeacherListScreen()
                    }
                }
                Box(
                    modifier = Modifier
                ){
                    AnimatedVisibility(
                        visible = !expanded,
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(vertical = 16.dp, horizontal = 8.dp)
                                .animateContentSize(),
                            horizontalAlignment = Alignment.End
                        ) {
                            ElevatedButton(
                                onClick = {
                                    navController.navigate(KuyaYanaScreen.Event.name)
                                    expanded = !expanded
                                },
                                modifier = modifier
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Evento",
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.event),
                                    contentDescription = "Localized description",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                )
                            }
                            ElevatedButton(
                                onClick = { navController.navigate(KuyaYanaScreen.Teacher.name)},
                                modifier = modifier
                                    .padding(vertical = 16.dp)

                            ) {
                                Text(
                                    text = "Clase",
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.class_icon),
                                    contentDescription = "Localized description",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            Spacer(modifier = Modifier.height(56.dp))
                        }
                    }
                }
            }
        }
    )
}

