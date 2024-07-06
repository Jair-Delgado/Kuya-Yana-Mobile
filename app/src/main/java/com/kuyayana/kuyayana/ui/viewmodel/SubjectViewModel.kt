package com.kuyayana.kuyayana.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.repository.SubjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SubjectViewModel : ViewModel() {
    private val repository = SubjectRepository()

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    val subjects: StateFlow<List<Subject>> = _subjects


    init {
        getSubjects()
    }
    fun addSubject(subject: Subject) {
        viewModelScope.launch {
            try {
                repository.addSubject(subject)
                Log.d("SubjectViewModel", "Subject added successfully")
                getSubjects()
            } catch (e: Exception) {
                Log.e("SubjectViewModel", "Error adding Subject", e)
            }
        }
    }
    fun getSubjects() {
        viewModelScope.launch {
            try {
                _subjects.value = repository.getSubjects()
                Log.d("SubjectViewModel", "Subjects retrieved successfully")
            } catch (e: Exception) {
                Log.e("SubjectViewModel", "Error getting subjects", e)
            }
        }
    }
    fun deleteSubject(subjectId: String){
        viewModelScope.launch {
            try {
                Log.d("SubjectViewModel", "Deleting subject with ID: $subjectId")
                repository.deleteSubject(subjectId)
                Log.d("SubjectViewModel", "Subject deleted successfully")
                getSubjects() // Actualiza la lista de subjects después de la eliminación
            } catch (e: Exception) {
                Log.e("SubjectViewModel", "Error deleting Subject", e)
            }
        }
    }
    fun updateSubject(documentId: String, newData:String){
        viewModelScope.launch {
            try {
                Log.d("SubjectViewModel", "Updating subject with ID: $documentId")
                repository.updateSubject(documentId, newData)
                getSubjects()
            }catch (e: Exception){
                Log.e("SubjectViewModel", "Error updating Subject", e)
            }
        }
    }
}
