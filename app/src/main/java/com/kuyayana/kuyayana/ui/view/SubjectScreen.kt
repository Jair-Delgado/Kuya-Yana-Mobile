package com.kuyayana.kuyayana.ui.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.ui.viewmodel.SubjectViewModel

@Composable
fun SubjectScreen(
    subjectViewModel: SubjectViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }

    // Recolecta las materias del ViewModel
    val subjects by subjectViewModel.subjects.collectAsState()

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
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de materias
        LazyColumn {
            items(subjects) { subject ->
                Text(text = subject.subjectName)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview () {
   SubjectScreen()
}
