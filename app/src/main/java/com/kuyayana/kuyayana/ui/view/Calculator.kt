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
import com.kuyayana.kuyayana.ui.viewmodel.TeacherViewModel
import io.grpc.internal.JsonParser
import org.json.JSONObject
import java.util.Objects


@Composable
fun CalculatorScreen(teacherViewModel: TeacherViewModel = viewModel()) {
    val subjects by teacherViewModel.subjects.collectAsState()
    val records by teacherViewModel.records.collectAsState()
    var selectedRecordInt: Int? = 0
    var selectedRecord by remember { mutableStateOf( records.firstOrNull()) }

    var notes by remember { mutableStateOf(listOf<Float>()) }
    var newNote by remember { mutableStateOf("") }
    var auxPercentage by remember { mutableStateOf("") }

    var percentage by remember { mutableStateOf("") }
    var percentages by remember { mutableStateOf(mutableListOf("")) }
    var average by remember { mutableStateOf<Float?>(null) }
    var calculatedPercentage by remember { mutableStateOf<Float?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Dropdown menu for selecting subject
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
                    var i = 0
                    DropdownMenuItem(
                        text = { Text(record.subject?.subjectName ?: "error") },
                        onClick = {
                            selectedRecord = record
                            selectedRecordInt = selectedRecord?.sections?.size
                            percentages = mutableListOf()
                            selectedRecord?.sections?.forEach{section->
                                percentages.add(section.percentage.toString())
                            }
                            expanded = false
                        }
                    )
                    i = i + 1
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))





        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ){
            var i = 0
            selectedRecord?.sections?.forEach { section->
                i= i+1
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Sección"+ (i), modifier = Modifier.padding(4.dp))
                    Text("%"+ section.percentage, modifier = Modifier.padding(4.dp))
                    IconButton(onClick = {
                        selectedRecord?.sections?.remove(section)
                    },modifier = Modifier.padding(4.dp)) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Quitar Sección")
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {

                    OutlinedTextField(
                        value = newNote,
                        onValueChange = {newNote= it
                            Log.d(TAG, "CalculatorScreen2: "+ newNote)},
                        label = { Text("Nota") },
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color.Gray)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    IconButton(onClick = {
                        Log.d(TAG, "CalculatorScreen: "+ newNote)
                        section.grades.add(newNote.toDouble())
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir Nota")
                    }

                }

                LazyRow(
                    modifier = Modifier
                        // Allow space to expand and scroll
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
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Nota: ${grade}", textAlign = TextAlign.Center)

                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        section.grades.removeAt(section.grades.size-1)
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar Nota")
                    }


                    OutlinedTextField(
                        value = auxPercentage,
                        onValueChange = { auxPercentage = it},
                        label = { Text("%") },
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, Color.Gray)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    IconButton(onClick = {
                        if (auxPercentage.length > 0){
                            section.percentage = auxPercentage.toFloat()
                            Log.d(TAG, "CalculatorScreen3: "+ section.percentage)
                        }

                    }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Eliminar Nota")
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
                            selectedRecord?.sections?.forEach{section ->
                                section.sectionGrade = 0
                                section.grades.forEach{grade->
                                    section.sectionGrade = section.sectionGrade.toDouble() + grade
                                }
                                section.sectionGrade = section.sectionGrade.toDouble() / section.grades.size
                                section.sectionGrade = (section.sectionGrade.toDouble() * section.percentage.toDouble()) / 100
                                Log.d(TAG, "sectionGr: "+ section.sectionGrade)
                                var aux: Double?
                                aux = selectedRecord?.finalGrade?.plus(section.sectionGrade.toDouble())
                                if (aux != null) {
                                    selectedRecord?.finalGrade = aux
                                    Log.d(TAG, "sectionGr: "+ selectedRecord?.finalGrade)
                                }
                            }
                            showResultDialog = true // Show dialog with results
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
                            teacherViewModel.updateRecord(selectedRecord)

                        } catch (e: Exception) {
                            errorMessage = "Error al guardar"
                        }
                    }
                ) {
                    Text("Guardar")
                }
            }
            // Dialog for showing results
            if (showResultDialog) {
                AlertDialog(
                    onDismissRequest = { showResultDialog = false },
                    title = { Text("Resultados") },
                    text = {

                        Column {
                            var i = 0
                            selectedRecord?.sections?.forEach{section->
                                i = i+1
                                Text("Sección ${i}: ${String.format("%.2f", section.sectionGrade)}", fontSize = 20.sp)

                            }
                            Text("Total: ${String.format("%.2f", selectedRecord?.finalGrade)}", fontSize = 20.sp)
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








        // Input for new note
    /*    Row(verticalAlignment = Alignment.CenterVertically) {
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
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    val noteValue = newNote.toFloatOrNull()
                    if (noteValue != null) {
                        notes = notes + noteValue
                        newNote = ""
                        errorMessage = null
                    } else {
                        errorMessage = "Nota inválida"
                    }
                }
            ) {
                Text("Agregar Nota")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display notes in squares
        LazyRow(
            modifier = Modifier
                .weight(1f) // Allow space to expand and scroll
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .scrollable(orientation = Orientation.Horizontal, state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notes) { note ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .border(1.dp, Color.Gray)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Nota: ${note}", textAlign = TextAlign.Center)
                        IconButton(onClick = {
                            notes = notes - note
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar Nota")
                        }
                    }
                }
            }
        }

        // Input for percentage
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = percentage,
                onValueChange = { percentage = it },
                label = { Text("Porcentaje") },
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color.Gray)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Calculate button
        Button(
            onClick = {
                try {
                    val avg = if (notes.isNotEmpty()) notes.average().toFloat() else 0f
                    val perc = percentage.toFloatOrNull() ?: 0f
                    average = avg
                    calculatedPercentage = avg * perc / 100
                    errorMessage = null
                    showResultDialog = true // Show dialog with results
                } catch (e: Exception) {
                    errorMessage = "Error en el cálculo"
                }
            }
        ) {
            Text("Calcular")
        }

        // Display error message
        errorMessage?.let {
            Text(it, color = Color.Red, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reset button
        Button(onClick = {
            notes = emptyList()
            newNote = ""
            percentage = ""
            average = null
            calculatedPercentage = null
            errorMessage = null
        }) {
            Text("Reiniciar")
        }
    }

    // Dialog for showing results
    if (showResultDialog) {
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text("Resultados") },
            text = {
                Column {
                    average?.let {
                        Text("Promedio: ${String.format("%.2f", it)}", fontSize = 20.sp)
                    }
                    calculatedPercentage?.let {
                        Text("Nota Calculada: ${String.format("%.2f", it)}", fontSize = 20.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showResultDialog = false }
                ) {
                    Text("Aceptar")
                }
            }
        )*/
    }
}


