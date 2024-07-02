package com.kuyayana.kuyayana.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import com.kuyayana.kuyayana.data.repository.SubjectRepository
import com.kuyayana.kuyayana.data.repository.TeacherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeacherViewModel: ViewModel() {

    private val repository = TeacherRepository()
    private val subjectRepository = SubjectRepository()
    private val _teachers = MutableStateFlow<List<Teacher>>(emptyList())
    val teachers: StateFlow<List<Teacher>> = _teachers

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    val subjects :StateFlow<List<Subject>> = _subjects

    init {
        getTeachers()
        getSubjects()
    }
    fun createTeacher(teacher: Teacher,subject: Subject) {
        viewModelScope.launch {
            try {
                val subjectId = repository.getSubjectDocumentReference(subject)
                repository.addTeacher(teacher, subjectName = subject)
                getTeachers()

            } catch (e: Exception) {
                Log.e("TeacherViewModel","Error creating teacher")
            }
        }
    }
    fun getTeachers() {
        viewModelScope.launch {
            try {
                _teachers.value = repository.getTeachers()
                Log.d("TeacherViewModel", "Teachers retrieved successfully")
            } catch (e: Exception) {
                Log.e("TeacherViewModel", "Error getting teachers", e)
            }
        }
    }
    fun getSubjects() {
        viewModelScope.launch {
            _subjects.value = repository.getSubjects()
        }
    }
}
