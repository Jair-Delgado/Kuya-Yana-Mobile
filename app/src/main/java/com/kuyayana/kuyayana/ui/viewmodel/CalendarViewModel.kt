package com.kuyayana.kuyayana.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.repository.EventRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel: ViewModel() {
    private val _eventos = MutableLiveData<List<Event>>()
    val eventos : LiveData< List<Event>> get() =_eventos
    private val repository = EventRepository()

    init {
        getEvents()
    }
    fun getEvents(){
        viewModelScope.launch {
            _eventos.value = repository.getEvents()
        }
    }
}