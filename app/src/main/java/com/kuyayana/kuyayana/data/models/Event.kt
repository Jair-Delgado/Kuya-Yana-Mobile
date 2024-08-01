package com.kuyayana.kuyayana.data.models

data class Event(
    var id: String= "",
    val title : String = "",
    val description: String = "",
    val teacher: Teacher?,
    val start : String = "",
    val end: String = ""
)
