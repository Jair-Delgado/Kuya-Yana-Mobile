package com.kuyayana.kuyayana.data.routes

import androidx.compose.ui.res.stringResource
import com.kuyayana.kuyayana.R
enum class KuyaYanaScreen(val title: Int){
    TaskList(title = R.string.tareas),
    Calendar(title = R.string.calendario),
    Schedule(title = R.string.horario),
    Subject(title = R.string.asignaturas),
    Event(title= R.string.eventos),
    Teacher(title = (R.string.crea_un_profesor)),
    LogOut(title = (R.string.cerrando_sesion) ),
    Login(title = ( R.string.inicio)),
    Register(title = ( R.string.inicio)),
    Main(title = ( R.string.inicio)),
    TeacherList(title = (R.string.lista_teachers)),
    Calculator(title = (R.string.Calculator))
}