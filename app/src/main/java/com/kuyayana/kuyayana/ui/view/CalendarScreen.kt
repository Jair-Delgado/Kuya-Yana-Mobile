package com.kuyayana.kuyayana.ui.view

import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import com.kuyayana.kuyayana.ui.viewmodel.CalendarViewModel
import org.intellij.lang.annotations.JdkConstants

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter

import java.time.format.TextStyle
import java.util.Locale


@Composable
fun CalendarScreen(calendarViewModel: CalendarViewModel = viewModel()) {
    val events by calendarViewModel.eventos.collectAsState()
    val startEvents = remember { mutableStateOf(mutableListOf<String>()) }
    val selectedEvent = remember { mutableStateOf(Event("", "", "", Teacher("", "", "", "", Subject()))) }
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    var selectedDate: LocalDate? = LocalDate.now()
    var showResultDialog = remember { mutableStateOf(false) }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val context = LocalContext.current

    Box(modifier = Modifier.padding(16.dp)) {
        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(showResultDialog, selectedEvent, startEvents, events, day, isSelected = selectedDate == day.date) { day ->
                    selectedDate = if (selectedDate == day.date) null else day.date
                }
            },
            monthHeader = { month ->
                MonthHeader(month = month.weekDays.first().map { it.date.month.toString() }, month.weekDays.first().map { it.date.year.toString() })
                val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            }
        )
    }

    if (showResultDialog.value) {
        AlertDialog(
            onDismissRequest = { showResultDialog.value = false },
            title = { Text("Clase:") },
            text = {
                Column {
                    Text(
                        "Titulo: ${selectedEvent.value.title} ",
                        fontSize = 20.sp
                    )
                    Text(
                        "Materia: ${selectedEvent.value.teacher?.subject?.subjectName ?: "Desconocido"} ",
                        fontSize = 20.sp
                    )
                    Text(
                        "Empieza: ${selectedEvent.value.start.substring(11, 16)} ",
                        fontSize = 20.sp
                    )
                    Text(
                        "Termina: ${selectedEvent.value.end.substring(11, 16)} ",
                        fontSize = 20.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResultDialog.value = false
                        try {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmXXX")
                            val eventStartTime = LocalDateTime.parse(selectedEvent.value.start, formatter)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                                .toEpochMilli()

                            val currentTime = Instant.now().toEpochMilli()

                            if (eventStartTime > currentTime) {
                                scheduleNotification(
                                    context,
                                    selectedEvent.value.title,
                                    selectedEvent.value.teacher?.subject?.subjectName ?: "Desconocido",
                                    eventStartTime
                                )
                            } else {
                                Toast.makeText(context, "La hora de inicio ya ha pasado", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(context, "Error al programar la notificación: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
@Composable
fun MonthHeader(month: List<String>, year: List<String>) {
    Text(
        text = "${month[month.size - 1].toLowerCase().capitalize()}-${year[year.size - 1]} ",
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun Day(show: MutableState<Boolean>, selectedEvent: MutableState<Event>, auxStarts: MutableState<MutableList<String>>, events: List<Event>, day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    val dayDate: String = day.date.toString()
    var i = 0
    var auxBool = false
    events.forEach {
        i = i + 1
        val aux = it.start.substring(0, 10)
        auxStarts.value.add(aux)
        if (aux == dayDate) {
            auxBool = true
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(
                        enabled = true,
                        onClick = {
                            onClick(day)
                            selectedEvent.value = it
                            show.value = true
                        }
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = day.date.dayOfMonth.toString())
            }
        } else if (!auxBool) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable(
                        enabled = true,
                        onClick = { onClick(day) }
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = day.date.dayOfMonth.toString())
            }
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}

@Preview
@Composable
fun CalendarPreview(){
    CalendarScreen()
}