package com.kuyayana.kuyayana.ui.view.appBars

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.kuyayana.kuyayana.R
import com.kuyayana.kuyayana.data.routes.KuyaYanaScreen

//BUTTON BAR
@Composable
fun KuyaYanaNavigationBar(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavigationBar(
        modifier = modifier
            .clip(MaterialTheme.shapes.small),
        containerColor = MaterialTheme.colorScheme.primary,

    ) {
        NavigationBarItem(
            icon = { Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = stringResource(R.string.inicio),
                tint = MaterialTheme.colorScheme.surface,
            )},
            label = { Text(
                stringResource(R.string.inicio),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge
            ) },
            onClick = {
                navController.navigate(KuyaYanaScreen.TaskList.name)},
            selected = false
        )
        NavigationBarItem(
            icon = { Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = stringResource(id = R.string.calendario),
                tint = MaterialTheme.colorScheme.surface
            )},
            label = { Text(
                stringResource(id = R.string.calendario),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            ) },
            selected = false,
            onClick = {navController.navigate(KuyaYanaScreen.Calendar.name)}
        )
        NavigationBarItem(
            icon = {Icon(
                painter = painterResource(id = R.drawable.schedule),
                contentDescription = stringResource(id = R.string.horario),
                tint = MaterialTheme.colorScheme.surface
            )},
            label = { Text(
                stringResource(id = R.string.horario),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            ) },
            selected = false,
            onClick = { navController.navigate(KuyaYanaScreen.Schedule.name) }
        )
        NavigationBarItem(
            icon = {Icon(
                painter = painterResource(id = R.drawable.subject),
                contentDescription = stringResource(id = R.string.asignaturas),
                tint = MaterialTheme.colorScheme.surface
            )},
            label = { Text(
                stringResource(id = R.string.asignaturas),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )},
            selected = false,
            onClick = {  navController.navigate(KuyaYanaScreen.Subject.name)}
        )
    }
}
