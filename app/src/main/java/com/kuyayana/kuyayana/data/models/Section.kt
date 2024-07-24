package com.kuyayana.kuyayana.data.models

data class Section (
    var percentage: Number = 0,
    val grades: MutableList<Double> = mutableListOf<Double>(),
    var sectionGrade: Number = 0,
    val showResults: Boolean = false
)