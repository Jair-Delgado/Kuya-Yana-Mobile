package com.kuyayana.kuyayana.ui.viewmodel

import android.util.Log
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

class EventViewModel: ViewModel() {

    private val repository = EventRepository()
    private val subjectRepository = SubjectRepository()
    private val teacherRepository = TeacherRepository()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events : StateFlow<List<Event>> = _events

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    val subjects :StateFlow<List<Subject>> = _subjects

    private val _teachers = MutableStateFlow<List<Teacher>>(emptyList())
    val teachers :StateFlow<List<Teacher>> = _teachers

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories :StateFlow<List<Category>> = _categories

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

}