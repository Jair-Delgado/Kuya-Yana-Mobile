package com.kuyayana.kuyayana.ui.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import com.kuyayana.kuyayana.data.routes.KuyaYanaScreen
import com.kuyayana.kuyayana.ui.viewmodel.EventViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("RememberReturnType")
@Composable
fun EventsScreen (
    eventViewModel: EventViewModel = viewModel(),
){
    var title by remember { mutableStateOf("")}
    var description by remember { mutableStateOf("")}
    var end by remember { mutableStateOf("")}
    var start by remember { mutableStateOf("")}
    var teacher by remember { mutableStateOf(Teacher("","","","",Subject()))}
    var category by remember { mutableStateOf(Category())}
    var endDate by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }


    var endHour by remember { mutableStateOf(0) }
    var endMinute by remember { mutableStateOf(0) }
    var startHour by remember { mutableStateOf(0) }
    var startMinute by remember { mutableStateOf(0) }


    var selectedTeacher by remember { mutableStateOf<Teacher?>(null)}
    var selectedSubject by remember { mutableStateOf<Subject?>(null)}
    var selectedCategory by remember { mutableStateOf<Category?>(null)}
    var selectedDate by remember { mutableStateOf("")}


    val subjects by eventViewModel.subjects.collectAsState()
    val teachers by eventViewModel.teachers.collectAsState()
    val categories by eventViewModel.categories.collectAsState()
    var message by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val context = LocalContext.current
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        val datePickerDialog = remember {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                        Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.time
                    )
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            startHour = hourOfDay
                            startMinute = minute
                        },
                        startHour,
                        startMinute,
                        true // Formato de 24 horas
                    ).show()
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
        }
        val endDatePickerDialog = remember {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                        Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.time
                    )
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            endHour = hourOfDay
                            endMinute = minute
                        },
                        endHour,
                        endMinute,
                        true // Formato de 24 horas
                    ).show()
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
        }
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
                value = "$startDate ${String.format("%02d:%02d", startHour, startMinute)}",
                onValueChange = {},
                label = { Text("Empieza") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        datePickerDialog.show()
                        //startTimePickerDialog.show()
                    }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "")
                    }
                },
                readOnly = true
            )
            Spacer(Modifier.padding(vertical = 8.dp))
            OutlinedTextField(
                value = "$endDate ${String.format("%02d:%02d", endHour, endMinute)}",
                onValueChange = {},
                label = { Text("Finaliza") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        endDatePickerDialog.show()
                        // endTimePickerDialog.show()
                    }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "")
                    }
                },
                readOnly = true
            )

            //CATEGORIA
            Spacer(Modifier.padding(vertical = 8.dp))

            var expandedCategory by remember { mutableStateOf(false) }
            Box {
                OutlinedTextField(
                    value = selectedCategory?.categoryName ?: "Seleccionar Categoria",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedCategory = true },
                    enabled = false,
                    readOnly = true,
                    label = { Text("Categoria") }
                )
                DropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false },

                    ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = {Text(category.categoryName)},
                            onClick = {
                                selectedCategory = category
                                expandedCategory = false
                            }
                        )
                    }
                }
            }

            //FINALIZA CATEGORIA-------------

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
            Spacer(Modifier.padding(vertical = 8.dp))
            var expandedTeacher by remember { mutableStateOf(false)}
            Box {
                OutlinedTextField(
                    value = selectedTeacher?.teacherName?: "Seleccionar Profesor",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedTeacher = true },
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
                            start = "${startDate}T${String.format("%02d:%02d", startHour, startMinute)}-05:00",
                            //start = start,
                            // end = end,
                            end ="${endDate}T${String.format("%02d:%02d", endHour, endMinute)}-05:00",
                            teacher = teacher
                        )
                        eventViewModel.createEvent(
                            newEvent,
                            selectedSubject!!,
                            selectedTeacher!!,
                            selectedCategory!!
                        )
                        title = ""
                        description = ""
                        // end = ""
                        endHour = 0
                        endMinute = 0
                        start = ""
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Evento")
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun EventsScreenPreview(){
   // EventsScreen()
}
@Composable
fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val timePicker = remember {
        android.app.TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                onTimeSelected(hourOfDay, minute)
            },
            initialHour,
            initialMinute,
            true
        )
    }

    DisposableEffect(Unit) {
        timePicker.setOnDismissListener { onDismiss() }
        timePicker.show()
        onDispose { timePicker.dismiss() }
    }
}