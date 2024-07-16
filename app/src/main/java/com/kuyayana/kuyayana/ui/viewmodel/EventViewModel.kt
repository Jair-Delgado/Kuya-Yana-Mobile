package com.kuyayana.kuyayana.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import com.kuyayana.kuyayana.data.repository.CategoryRepository
import com.kuyayana.kuyayana.data.repository.EventRepository
import com.kuyayana.kuyayana.data.repository.SubjectRepository
import com.kuyayana.kuyayana.data.repository.TeacherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class EventViewModel: ViewModel() {

    private val repository = EventRepository()
    private val subjectRepository = SubjectRepository()
    private val teacherRepository = TeacherRepository()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events : StateFlow<List<Event>> = _events

    /*private val _eventos = MutableLiveData<Map<LocalDate, List<String>>>()
    val eventos : LiveData<Map<LocalDate, List<String>>> get()=_eventos*/

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    val subjects :StateFlow<List<Subject>> = _subjects

    private val _teachers = MutableStateFlow<List<Teacher>>(emptyList())
    val teachers :StateFlow<List<Teacher>> = _teachers

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories :StateFlow<List<Category>> = _categories

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _category = MutableStateFlow<Category?>(null)
    val category : StateFlow<Category?> get() = _category

    init {
        getEvents()
        getSubjects()
        getTeachers()
    }
    fun createEvent(
        event: Event,
        subject: Subject,
        teacher: Teacher
    ){
        viewModelScope.launch {
            try {
                repository.addEvent(
                    event,
                    subject,
                    teacher
                )
                getEvents()
                Log.d("EventViewModel","Event added")
            }catch (e: Exception){
                Log.e("EventViewModel", "Error creating event")
            }
        }
    }
    fun getEvents(){
        viewModelScope.launch {
            _events.value = repository.getEvents()
        }
    }
    fun getSubjects() {
        viewModelScope.launch {
            _subjects.value = subjectRepository.getSubjects()
        }
    }
    fun getTeachers() {
        viewModelScope.launch {
            _teachers.value = teacherRepository.getTeachers()
        }
    }
    fun updateMessage(date: String) {
        viewModelScope.launch {

            val selectedCalendar = Calendar.getInstance().apply {
                time = SimpleDateFormat("dd-MM-yy", Locale.getDefault()).parse(date)!!
            }
            val currentDate = Calendar.getInstance()

            val differenceInMillis = selectedCalendar.timeInMillis - currentDate.timeInMillis
            val differenceInDays = (differenceInMillis / (1000 * 60 * 60 * 24)).toInt()

            _message.value = when {
                differenceInDays > 0 -> "El evento termina dentro de $differenceInDays días"
                differenceInDays == 0 -> "El evento termina hoy"
                else -> "El evento ya ha terminado"
            }
            Log.d("EventViewModel", "Mensaje actualizado: ${_message.value}")
        }
    }


}