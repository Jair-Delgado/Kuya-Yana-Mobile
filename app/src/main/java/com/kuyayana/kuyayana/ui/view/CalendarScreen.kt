package com.kuyayana.kuyayana.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.DocumentReference
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import com.kuyayana.kuyayana.ui.viewmodel.SubjectViewModel
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel


/*
* Vista del calendario
* */
/*@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen ( ){
    Column {
        Text(text = "Calendario")
    }
}




@Preview
@Composable
fun CalendarPreview(){
    CalendarScreen()
}*/



@Composable
fun CreateTeacherScreen(teacherViewModel: TeacherViewModel) {
    var email by remember { mutableStateOf("") }
    var teacherName by remember { mutableStateOf("") }
    var teacherLastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var selectedSubjectId by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }
    val subjects by teacherViewModel.subjects.collectAsState()

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
                        text = {Text(subject.subjectName)},
                        onClick = {
                            selectedSubject = subject
                            expanded = false

                        }
                    )
                }
            }
        }


        /*OutlinedTextField(
            value = selectedSubjectId,
            onValueChange = { selectedSubjectId = it },
            label = { Text("ID de Asignatura") },
            modifier = Modifier.fillMaxWidth()
        )*/
        Spacer(Modifier.padding(vertical = 16.dp))
        Button(
            onClick = {
                if (selectedSubject != null) {
                    val newTeacher = Teacher(
                        email = email,
                        teacherName = teacherName,
                        teacherLastName = teacherLastName,
                        phoneNumber = phoneNumber,
                        subject = selectedSubject!!.subjectName
                    )
                    teacherViewModel.createTeacher(newTeacher, selectedSubject!!)
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
    }
}

/*@Preview(showBackground = true)
@Composable
fun PreviewCreateTeacherScreen() {
    val viewModel: TeacherViewModel = viewModel()
    CreateTeacherScreen(viewModel)
}*/
