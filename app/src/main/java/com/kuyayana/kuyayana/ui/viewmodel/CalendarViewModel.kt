package com.kuyayana.kuyayana.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.models.Record
import com.kuyayana.kuyayana.data.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel: ViewModel() {
    private val _eventos = MutableStateFlow<List<Event>>(emptyList())
    val eventos : StateFlow< List<Event>> =_eventos

    private val repository = EventRepository()

    init {
        getEvents()
    }
    fun getEvents(){
        viewModelScope.launch {
            _eventos.value = repository.getClass()
            Log.d("getEvents: ",_eventos.value.toString())
        }
    }
}