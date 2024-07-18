package com.kuyayana.kuyayana.ui.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel


@Composable
fun TeacherScreen(
    teacherViewModel: TeacherViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var teacherName by remember { mutableStateOf("") }
    var teacherLastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    var selectedSubject by remember { mutableStateOf<Subject?>(null) }

    val subjects by teacherViewModel.subjects.collectAsState()
    val teachers by teacherViewModel.teachers.collectAsState()

    LaunchedEffect(teachers) {
        Log.d("TeacherScreen", "Teachers: $teachers")
    }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = teacherName,
            onValueChange = { teacherName = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = teacherLastName,
            onValueChange = { teacherLastName = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(vertical = 8.dp))

        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = selectedSubject?.subjectName ?: "Seleccionar Asignatura",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                enabled = false,
                readOnly = true,
                label = { Text("Asignatura") }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },

                ) {
                subjects.forEach { subject ->
                    DropdownMenuItem(
                        text = { Text(subject.subjectName) },
                        onClick = {
                            selectedSubject = subject
                            expanded = false

                        }
                    )
                }
            }
        }

        Spacer(Modifier.padding(vertical = 16.dp))
        Button(
            onClick = {
                if (selectedSubject != null) {
                    val newTeacher = Teacher(
                        email = email,
                        teacherName = teacherName,
                        teacherLastName = teacherLastName,
                        phoneNumber = phoneNumber,
                        subject = selectedSubject
                    )
                    teacherViewModel.createTeacher(
                        newTeacher,
                        selectedSubject!!
                    )
                    email = ""
                    teacherName = ""
                    teacherLastName = ""
                    phoneNumber = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Profesor")
        }

        LazyColumn {
            items(teachers){ teacher ->
                Text(text = teacher.teacherName)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}