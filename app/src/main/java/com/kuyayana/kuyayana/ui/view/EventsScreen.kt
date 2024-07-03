package com.kuyayana.kuyayana.ui.view

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
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import com.kuyayana.kuyayana.ui.viewmodel.EventViewModel

@Composable
fun EventsScreen (
    eventViewModel: EventViewModel = viewModel(),
    categoryName: Category
){
    var title by remember { mutableStateOf("")}
    var description by remember { mutableStateOf("")}
    var end by remember { mutableStateOf("")}
    var start by remember { mutableStateOf("")}


    var selectedTeacher by remember { mutableStateOf<Teacher?>(null)}
    var selectedSubject by remember { mutableStateOf<Subject?>(null)}


    val subjects by eventViewModel.subjects.collectAsState()
    val teachers by eventViewModel.teachers.collectAsState()
    val categories by eventViewModel.categories.collectAsState()



    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titulo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripccion") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = start,
            onValueChange = { start = it },
            label = { Text("Inicia") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = end,
            onValueChange = { end = it },
            label = { Text("Finaliza") },
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
        var expandedTeacher by remember { mutableStateOf(false)}
        Box {
            OutlinedTextField(
                value = selectedTeacher?.teacherName?: "Seleccionar Profesor",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedTeacher = true},
                enabled = false,
                readOnly = true,
                label = { Text("Profesor") }
            )
            DropdownMenu(
                expanded = expandedTeacher,
                onDismissRequest = { expandedTeacher = false },

                ) {
                teachers.forEach { teacher ->
                    DropdownMenuItem(
                        text = {Text(teacher.teacherName)},
                        onClick = {
                            selectedTeacher = teacher
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.padding(vertical = 16.dp))
        Button(
            onClick = {
                if (selectedSubject != null ) {
                    val newEvent = Event(
                        title = title,
                        description = description,
                        start = start,
                        end = end
                    )
                    eventViewModel.createEvent(
                        newEvent,
                        selectedSubject!!,
                        selectedTeacher!!
                    )
                    title = ""
                    description = ""
                    end = ""
                    start = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Evento")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun EventsScreenPreview(){
   // EventsScreen()
}