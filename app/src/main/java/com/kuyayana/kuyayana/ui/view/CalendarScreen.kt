package com.kuyayana.kuyayana.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/*
* Vista del calendario
* */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen ( ){
    Column {
        Text(text = "Calendario")
    }
}




@Preview
@Composable
fun CalendarPreview(){
    CalendarScreen()
}