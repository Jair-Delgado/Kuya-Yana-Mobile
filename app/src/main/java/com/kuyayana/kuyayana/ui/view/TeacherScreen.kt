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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import com.kuyayana.kuyayana.data.routes.KuyaYanaScreen
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel


@Composable
fun TeacherScreen(
    teacherViewModel: TeacherViewModel = viewModel(),
    navController: NavHostController,
) {
    var email by remember { mutableStateOf("") }


    var teacherName by remember { mutableStateOf("") }
    var teacherLastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    //validaciones

    var isValidEmail by remember { mutableStateOf(true) }
    var isValidTeacherName by remember { mutableStateOf(true) }
    var isValidTeacherLastName by remember { mutableStateOf(true) }
    var isValidPhoneNumber by remember { mutableStateOf(true) }

    val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    val phonePattern = "^[0-9]{1,10}$"
    val namePattern = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$"


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
            onValueChange = {
                email = it
                isValidEmail = Regex(emailPattern).matches(it)
            },
            label = { Text("Email") },
            isError = !isValidEmail,
            modifier = Modifier.fillMaxWidth()
        )
        if (!isValidEmail) {
            Text(
                text = "Por favor ingrese un correo válido",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = teacherName,
            onValueChange = {
                teacherName = it
                isValidTeacherName = Regex(namePattern).matches(it)
            },
            label = { Text("Nombre") },
            isError = !isValidTeacherName,
            modifier = Modifier.fillMaxWidth()
        )
        if (!isValidTeacherName) {
            Text(
                text = "Por favor ingrese un nombre válido (solo letras y espacios)",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = teacherLastName,
            onValueChange = {
                teacherLastName = it
                isValidTeacherLastName = Regex(namePattern).matches(it)
            },
            label = { Text("Apellido") },
            isError = !isValidTeacherLastName,
            modifier = Modifier.fillMaxWidth()
        )
        if (!isValidTeacherLastName) {
            Text(
                text = "Por favor ingrese un apellido válido (solo letras y espacios)",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                if (it.length <= 10 && Regex(phonePattern).matches(it)) {
                    phoneNumber = it
                    isValidPhoneNumber = true
                } else {
                    isValidPhoneNumber = false
                }
            },
            label = { Text("Teléfono") },
            isError = !isValidPhoneNumber,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (!isValidPhoneNumber) {
            Text(
                text = "Por favor ingrese un número válido (solo números y máximo 10 dígitos)",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

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
            /*DropdownMenu(
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
        }*/

            Spacer(Modifier.padding(vertical = 16.dp))
            Button(
                onClick = {
                    if (isValidEmail && isValidTeacherName && isValidTeacherLastName && isValidPhoneNumber != null) {
                        val newTeacher = Teacher(
                            email = email,
                            teacherName = teacherName,
                            teacherLastName = teacherLastName,
                            phoneNumber = phoneNumber,
                            subject = Subject()
                        )
                        teacherViewModel.createTeacher(newTeacher, Subject())
                        // Clear fields after successful creation
                        email = ""
                        teacherName = ""
                        teacherLastName = ""
                        phoneNumber = ""
                        selectedSubject = null
                        navController.navigate(KuyaYanaScreen.TeacherList.name)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Profesor")
            }

        }
    }
}