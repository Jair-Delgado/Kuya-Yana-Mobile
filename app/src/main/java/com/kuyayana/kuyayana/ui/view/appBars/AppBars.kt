package com.kuyayana.kuyayana.ui.view.appBars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kuyayana.kuyayana.KuyaYanaScreen
import com.kuyayana.kuyayana.ui.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.launch

/*
* Barras de Navegación
*
*/

//TOP BAR
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KuyaYanaTopAppBar(
    currentScreen: KuyaYanaScreen,
    authViewModel:AuthViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFE04B5A),
            titleContentColor = Color.White,
        ),
        title = { Text(stringResource(currentScreen.title))

        },
        navigationIcon = {

            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }

            }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }

        },

        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                authViewModel.logout()

            }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ExitToApp,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },

    )
}


//BUTTON BAR
@Composable
fun KuyaYanaNavigationBar(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavigationBar(
        modifier = modifier
            .clip(MaterialTheme.shapes.small),
        containerColor = Color(0xFF003049)
    ) {
        NavigationBarItem(
            icon = { Icon(
                Icons.Filled.Home,
                contentDescription = "Inicio",
                tint = Color.White
            )},
            label = { Text(
                "Inicio",
                color = Color.White
            ) },
            selected = false,
            onClick = { navController.navigate(KuyaYanaScreen.TaskList.name) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.DateRange, contentDescription = "Inicio",tint = Color.White) },
            label = { Text("Calendario",color = Color.White) },
            selected = false,
            onClick = {navController.navigate(KuyaYanaScreen.Calendar.name)}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Inicio",tint = Color.White) },
            label = { Text("Horario",color = Color.White) },
            selected = false,
            onClick = { navController.navigate(KuyaYanaScreen.Schedule.name) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.FavoriteBorder, contentDescription = "Inicio",tint = Color.White) },
            label = { Text("Materias",color = Color.White) },
            selected = false,
            onClick = {  navController.navigate(KuyaYanaScreen.Subject.name)}
        )
    }
}

