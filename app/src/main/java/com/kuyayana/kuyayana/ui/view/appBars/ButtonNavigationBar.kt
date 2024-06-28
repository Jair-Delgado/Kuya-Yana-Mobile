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
import com.kuyayana.kuyayana.KuyaYanaScreen
import com.kuyayana.kuyayana.R

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
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.labelLarge
            ) },
            onClick = {
                navController.navigate(KuyaYanaScreen.TaskList.name)},
            selected = false
        )
        NavigationBarItem(
            icon = { Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Calendario",
                tint = MaterialTheme.colorScheme.surface
            )},
            label = { Text("Calendario",color = Color.White) },
            selected = false,
            onClick = {navController.navigate(KuyaYanaScreen.Calendar.name)}
        )
        NavigationBarItem(
            icon = {Icon(
                painter = painterResource(id = R.drawable.schedule),
                contentDescription = "Inicio",
                tint = MaterialTheme.colorScheme.surface
            )},
            label = { Text("Horario",color = Color.White) },
            selected = false,
            onClick = { navController.navigate(KuyaYanaScreen.Schedule.name) }
        )
        NavigationBarItem(
            icon = {Icon(
                painter = painterResource(id = R.drawable.subject),
                contentDescription = "Inicio",
                tint = MaterialTheme.colorScheme.surface
            )},
            label = { Text("Materias",color = Color.White) },
            selected = false,
            onClick = {  navController.navigate(KuyaYanaScreen.Subject.name)}
        )
    }
}
