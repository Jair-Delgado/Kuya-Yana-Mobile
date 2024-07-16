package com.kuyayana.kuyayana.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kuyayana.kuyayana.ui.viewmodel.CalendarViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun CalendarScreen(viewModel: CalendarViewModel = viewModel()){
    val events by viewModel.eventos.observeAsState(initial = emptyList())

    /*val currentDate = remember{LocalDate.now()}
    val currentMonth = remember { YearMonth.now() }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    //val calendarState = rememberCalendarState(currentMonth)
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed*/


    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

   /* val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )*/
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    Box(modifier = Modifier.padding(16.dp)){
        /*WeekCalendar(
            state = state,
            dayContent = { Day(it) }
        )*/
        HorizontalCalendar(
            state = state,
            dayContent = { Day(it) },
            monthHeader = {month ->
                val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                DaysOfWeekTitle(daysOfWeek = daysOfWeek())
            }
        )

    }


}
/*@Composable
fun DayContent(day: CalendarDay, events: List<Event>){
    Box(
        modifier = Modifier
            .padding(4.dp)
    ){
        Text(text = day.date.dayOfMonth.toString())
        if (events.isNotEmpty()){
            Box(modifier = Modifier
                //.size(8.dp)
                .background(Color.Red, CircleShape)
                .align(Alignment.BottomEnd)
            )
        }
    }
}
@Composable
fun MonthHeader(month: YearMonth){
    Text(
        text = "${month.month.name.toLowerCase().capitalize()} ${month.year}",
        modifier = Modifier
            .padding(16.dp)
    )
}*/
/*@Composable
fun Day(day: WeekDay) {
    Box(
        modifier = Modifier
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.date.dayOfMonth.toString())
    }
}*/

@Composable
fun Day(day: CalendarDay) {
    Box(
        modifier = Modifier
            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.date.dayOfMonth.toString())
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