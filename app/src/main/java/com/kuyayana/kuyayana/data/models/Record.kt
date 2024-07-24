package com.kuyayana.kuyayana.data.models

data class Record (
    val id: String = "",
    var finalGrade: Double = 0.0,
    val sections: MutableList<Section> = mutableListOf<Section>(),
    val subject: Subject?
){


}

