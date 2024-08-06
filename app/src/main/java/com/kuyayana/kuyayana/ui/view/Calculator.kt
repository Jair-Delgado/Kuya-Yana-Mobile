package com.kuyayana.kuyayana.ui.view


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuyayana.kuyayana.data.models.Record
import com.kuyayana.kuyayana.data.models.Section
import com.kuyayana.kuyayana.ui.viewmodel.SubjectViewModel
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel
import io.grpc.internal.JsonParser
import org.json.JSONObject
import java.util.Objects


@Composable
fun CalculatorScreen(
    teacherViewModel: TeacherViewModel = viewModel(),
    subjectViewModel: SubjectViewModel = viewModel(),
) {
    val subjects by subjectViewModel.subjects.collectAsState()
    val records by teacherViewModel.records.collectAsState()
    var selectedRecord by remember { mutableStateOf(records.firstOrNull()?.also { if (it.sections.isEmpty()) it.sections.add(Section()) }) }

    var newNote by remember { mutableStateOf("") }
    var auxPercentage by remember { mutableStateOf("") }

    var percentages by remember { mutableStateOf(mutableListOf<String>()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }
    var showPercentage by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Box {
            OutlinedTextField(
                value = selectedRecord?.subject?.subjectName ?: "Seleccionar Asignatura",
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
                onDismissRequest = { expanded = false }
            ) {
                records.forEach { record ->
                    DropdownMenuItem(
                        text = { Text(record.subject?.subjectName ?: "error") },
                        onClick = {
                            selectedRecord = record.also { if (it.sections.isEmpty()) it.sections.add(Section()) }
                            percentages = mutableListOf()
                            selectedRecord?.sections?.forEach { section ->
                                percentages.add(section.percentage.toString())
                            }
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Text("Mostrar como: ", modifier = Modifier.padding(bottom = 8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = showPercentage,
                    onClick = { showPercentage = true }
                )
                Text("Porcentaje", modifier = Modifier.padding(end = 8.dp))
                RadioButton(
                    selected = !showPercentage,
                    onClick = { showPercentage = false }
                )
                Text("Promedio")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            selectedRecord?.sections?.forEachIndexed { index, section ->
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Sección ${index + 1}", modifier = Modifier.padding(4.dp))
                        Text("%${section.percentage}", modifier = Modifier.padding(4.dp))
                        IconButton(onClick = {
                            selectedRecord?.sections?.remove(section)
                        }, modifier = Modifier.padding(4.dp)) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Quitar Sección")
                        }
                        IconButton(onClick = {
                            selectedRecord?.sections?.add(Section())
                        }, modifier = Modifier.padding(4.dp)) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Sección")
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = newNote,
                            onValueChange = { newNote = it },
                            label = { Text("Nota") },
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, Color.Gray)
                                .padding(8.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                        IconButton(onClick = {
                            if (newNote.isNotEmpty()) {
                                section.grades.add(newNote.toDouble())
                                newNote = ""
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir Nota")
                        }
                    }

                    LazyRow(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .scrollable(
                                orientation = Orientation.Horizontal,
                                state = rememberScrollState()
                            ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(section.grades) { grade ->
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .border(1.dp, Color.Gray)
                                    .padding(8.dp)
                                    .clickable {
                                        section.grades.remove(grade)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Nota: $grade", textAlign = TextAlign.Center)
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (section.grades.isNotEmpty()) {
                                section.grades.removeAt(section.grades.size - 1)
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar Nota")
                        }

                        OutlinedTextField(
                            value = auxPercentage,
                            onValueChange = { auxPercentage = it },
                            label = { Text("%") },
                            modifier = Modifier
                                .weight(1f)
                                .border(1.dp, Color.Gray)
                                .padding(8.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                        IconButton(onClick = {
                            if (auxPercentage.isNotEmpty()) {
                                section.percentage = auxPercentage.toFloat()
                                auxPercentage = ""
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Actualizar Porcentaje")
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        try {
                            selectedRecord?.finalGrade = 0.0
                            selectedRecord?.sections?.forEach { section ->
                                val sectionAverage = if (section.grades.isNotEmpty()) section.grades.average() else 0.0
                                section.sectionGrade = (sectionAverage * section.percentage.toDouble()) / 100
                                selectedRecord?.finalGrade = (selectedRecord?.finalGrade ?: 0.0) + section.sectionGrade.toDouble()
                            }
                            showResultDialog = true
                        } catch (e: Exception) {
                            errorMessage = "Error en el cálculo"
                        }
                    }
                ) {
                    Text("Calcular")
                }
                Button(
                    onClick = {
                        try {
                            Log.d( "CalculatorScreen: ",selectedRecord?.subject.toString())
                            teacherViewModel.updateRecord(selectedRecord)
                        } catch (e: Exception) {
                            errorMessage = "Error al guardar"
                        }
                    }
                ) {
                    Text("Guardar")
                }
            }

            if (showResultDialog) {
                AlertDialog(
                    onDismissRequest = { showResultDialog = false },
                    title = { Text("Resultados") },
                    text = {
                        Column {
                            selectedRecord?.sections?.forEachIndexed { index, section ->
                                val displayValue = if (showPercentage) {
                                    section.sectionGrade
                                } else {
                                    section.grades.average()
                                }
                                Text("Sección ${index + 1}: ${String.format("%.2f", displayValue)}", fontSize = 20.sp)
                            }
                            val totalValue = if (showPercentage) {
                                selectedRecord?.finalGrade
                            } else {
                                selectedRecord?.sections?.flatMap { it.grades }?.average() ?: 0.0
                            }
                            Text("Total: ${String.format("%.2f", totalValue)}", fontSize = 20.sp)
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { showResultDialog = false }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}
