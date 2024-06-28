package com.kuyayana.kuyayana.ui.view.appBars

import com.kuyayana.kuyayana.KuyaYanaScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
    modifier: Modifier = Modifier
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
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



