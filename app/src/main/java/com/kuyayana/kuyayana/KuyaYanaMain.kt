package com.kuyayana.kuyayana

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.routes.KuyaYanaScreen
import com.kuyayana.kuyayana.ui.view.CalculatorScreen
import com.kuyayana.kuyayana.ui.view.CalendarScreen
import com.kuyayana.kuyayana.ui.view.appBars.KuyaYanaNavigationBar

import com.kuyayana.kuyayana.ui.view.TeacherScreen
import com.kuyayana.kuyayana.ui.view.EventsScreen
import com.kuyayana.kuyayana.ui.view.SubjectScreen
import com.kuyayana.kuyayana.ui.view.TaskList
import com.kuyayana.kuyayana.ui.view.TeacherListScreen
import com.kuyayana.kuyayana.ui.viewmodel.EventViewModel
import com.kuyayana.kuyayana.ui.viewmodel.SubjectViewModel
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel
import com.kuyayana.kuyayana.ui.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KuyaYanaApp(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onLogOutClick:() -> Unit

) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = KuyaYanaScreen.valueOf(backStackEntry?.destination?.route ?: KuyaYanaScreen.TaskList.name)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp) // Ajusta el ancho del menú lateral
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_transparent), // Cambia esto por tu logo
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(width = 300.dp, height = 300.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))//
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Kuyayana",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(32.dp))
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = null) },
                    label = { Text("Lista de Profesores") },
                    selected = false,
                    onClick = {
                        navController.navigate(KuyaYanaScreen.TeacherList.name)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    label = { Text("Crea un evento") },
                    selected = false,
                    onClick = {
                        navController.navigate(KuyaYanaScreen.Event.name)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    label = { Text("Calendario") },
                    selected = false,
                    onClick = {
                        navController.navigate(KuyaYanaScreen.Schedule.name)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Edit, contentDescription = null) },
                    label = { Text("Materias") },
                    selected = false,
                    onClick = {
                        navController.navigate(KuyaYanaScreen.Subject.name)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profesores") },
                    selected = false,
                    onClick = {
                        navController.navigate(KuyaYanaScreen.Teacher.name)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                Spacer(modifier = Modifier.weight(1f))
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                    label = { Text("Cerrar Sesión") },
                    selected = false,
                    onClick = {
                        authViewModel.logout()
                        onLogOutClick()
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .clickable {  }
                )
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            titleContentColor = MaterialTheme.colorScheme.secondary
                        ),
                        title = {
                            Text(stringResource(currentScreen.title))
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Localized description",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Localized description",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }
                            IconButton(onClick = {
                                authViewModel.logout()
                                onLogOutClick()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "Localized description",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    KuyaYanaNavigationBar(navController)
                },
               /* floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* Toggle some state if needed */ },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add"
                        )
                    }
                },*/
                floatingActionButtonPosition = FabPosition.End,
                content = { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = KuyaYanaScreen.TaskList.name,
                        Modifier.padding(paddingValues)
                    ) {
                        composable(route = KuyaYanaScreen.TaskList.name) {
                            TaskList(eventViewModel = EventViewModel())
                        }
                        composable(route = KuyaYanaScreen.Calendar.name) {
                            CalendarScreen()
                        }
                        composable(route = KuyaYanaScreen.Schedule.name) {
                           CalendarScreen()
                        }
                        composable(route = KuyaYanaScreen.Subject.name) {
                            SubjectScreen(
                                navController,
                                subjectViewModel = SubjectViewModel()
                            )
                        }
                        composable(route = KuyaYanaScreen.Event.name) {
                            EventsScreen(
                                eventViewModel = EventViewModel()
                            )
                        }
                        composable(route = KuyaYanaScreen.Teacher.name) {
                            TeacherScreen(teacherViewModel = TeacherViewModel())
                        }
                        composable(route = KuyaYanaScreen.TeacherList.name) {
                            TeacherListScreen(
                                teacherViewModel = TeacherViewModel()
                            )
                        }
                        composable(route = KuyaYanaScreen.Calculator.name) {
                            CalculatorScreen()
                        }

                    }
                }
            )
        }
    )
}