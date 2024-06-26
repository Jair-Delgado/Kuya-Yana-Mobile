package com.kuyayana.kuyayana.data

import java.sql.Date
import java.time.Year

data class Task (
    val date: String,
    val title : String,
    val content : String,
    val year: String
)

val tasks = listOf(
    Task("Junio 22", "Tarea", "Hacer 10 ejercicios de mates","2024"),
    Task("Junio 23", "Deberes", "Leer 20 páginas de un libro","2024"),
    Task("Junio 24", "Proyecto", "Completar la presentación del proyecto","2024"),
    Task("Junio 25", "Prueba", "Realizar prueba de vocabulario","2024"),
    Task("Junio 26", "Examen", "Estudiar para el examen de química","2024"),
    Task("Junio 27", "Recordatorio", "Practicar guitarra por una hora","2024"),
    Task("Junio 28", "Tarea", "Enviar el informe semanal","2024"),
    Task("Junio 29", "Deberes", "Revisar notas de la clase de historia","2024"),
    Task("Junio 30", "Recordatorio", "Limpiar y organizar el escritorio","2024"),
    Task("Julio 1", "Recordatorio", "Salir a cenar con amigos","2024")
)