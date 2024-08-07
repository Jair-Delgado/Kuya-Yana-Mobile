package com.kuyayana.kuyayana.ui.view

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.ui.viewmodel.SubjectViewModel
import com.kuyayana.kuyayana.R
import com.kuyayana.kuyayana.data.routes.KuyaYanaScreen

@Composable
fun SubjectScreen(
    navController: NavHostController ,
    subjectViewModel: SubjectViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }

    // Recolecta las materias del ViewModel
    val subjects by subjectViewModel.subjects.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingSubject by remember { mutableStateOf<Subject?>(null) }

    LaunchedEffect(subjects) {
        Log.d("SubjectScreen", "Subjects: $subjects")
    }
    Column(modifier = Modifier.padding(16.dp)) {
        // Campo  para ingresar el nombre de la materia
        Text("Ingresar Materia:")
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre Materia") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Botón para agregar una nueva materia
        Button(onClick = {
            if (name.isNotBlank()) {
                val subject = Subject( subjectName = name)
                subjectViewModel.addSubject(subject)
                name = ""
            }
        }) {
            Text(stringResource(id = R.string.agregar))
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp)
        ) {
            items(subjects){subject ->
                KYItems(
                    subject,
                    onDelete = {
                        Log.d("SubjectScreen", "Deleting subject with ID: ${subject.id}")
                        subjectViewModel.deleteSubject(subject.id)},
                    onEdit = {
                        editingSubject = subject
                        showDialog = true
                    }
                )
                if (showDialog && editingSubject != null) {
                    EditDialog(
                        subject = editingSubject!!,
                        onSave = { newValue ->
                        subjectViewModel.updateSubject(editingSubject!!.id,newValue)
                            showDialog = false
                            editingSubject = null
                    }, onDismiss = {
                            showDialog = false
                        showDialog = false
                    })
                }
            }
        }
    }
}
@Composable
fun KYItems(
    subject: Subject,
    onDelete: (String) -> Unit,
    onEdit: ()-> Unit,
    modifier: Modifier = Modifier
) {
    val documentId = remember {subject.id}
    var expandSubject by remember { mutableStateOf(false) }

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        modifier = Modifier
            .padding(10.dp)
            .animateContentSize()
            .height(if (expandSubject) 180.dp else 80.dp)
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expandSubject = !expandSubject
            }
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        ){
            Text(
                text = subject.subjectName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
            if (expandSubject){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {

                        IconButton(
                            onClick = {
                                Log.d("KYItems", "Deleting subject with ID: $documentId")
                                onDelete(documentId)
                            } ,
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
                        IconButton(
                            onClick = onEdit ,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Localized description",
                                tint = Color.Yellow
                            )
                        }
                }
            }
        }
    }
}
@Composable
fun EditDialog(
        subject: Subject,
        onSave: (String) -> Unit,
        onDismiss: () -> Unit
){
    var newValue by remember { mutableStateOf(subject.subjectName)}
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Editar Materia") },
        text = {
            TextField(
                value = newValue,
                onValueChange = { newValue = it },
                label = { Text("Nuevo Valor") }
            )
        },
        confirmButton = {
            Button(onClick = {
                onSave(newValue)
                onDismiss()
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = {onDismiss()}) {
                Text("Cancelar")
            }
        }
    )
}
