package com.kuyayana.kuyayana.ui.view

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuyayana.kuyayana.data.models.Teacher
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel

@Composable
fun TeacherListScreen(
    teacherViewModel: TeacherViewModel
) {
    val teachers by teacherViewModel.teachers.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(teachers) { teacher ->
            TeacherItem(teacher, viewModel = teacherViewModel)
        }
    }
}

@Composable
fun TeacherItem(
    teacher: Teacher,
    modifier: Modifier = Modifier,
    viewModel: TeacherViewModel
) {
    var expandEvent by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .animateContentSize()
            .height(if (expandEvent) 210.dp else 80.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expandEvent = !expandEvent
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color(0xFFEAE2B7)
        )
    ) {
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            Text(
                text = "${teacher.teacherName} ${teacher.teacherLastName}",
                fontWeight = FontWeight.Bold,
                fontSize = if (expandEvent) 25.sp else 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
            if (expandEvent) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Correo: ${teacher.email}",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Telefono: ${teacher.phoneNumber}",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                teacher.subject?.let { subject ->
                    Text(
                        text = "Materia: ${subject.subjectName}",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    IconButton(
                        onClick = {
                            Log.d("TeacherItems", "Deleting teacher")
                            viewModel.deleteTeacher(teacher)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Localized description",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}



/*@Preview(showBackground = true)
@Composable
fun teacherListPreview(){
    TeacherItem(teacherS = Teacher(), teacherViewModel = )
}*/
