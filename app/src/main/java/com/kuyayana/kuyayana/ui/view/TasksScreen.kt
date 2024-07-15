package com.kuyayana.kuyayana.ui.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuyayana.kuyayana.data.Task
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.tasks
import com.kuyayana.kuyayana.ui.viewmodel.CategoryViewModel
import com.kuyayana.kuyayana.ui.viewmodel.EventViewModel

/*
* Vista de las tareas
* */

//Genera y muestra una lista de las tareas a partir de la fun TaskItem,
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskList (
    //categoryViewModel: CategoryViewModel,
    eventViewModel: EventViewModel
){
    //val categories by categoryViewModel.categories.collectAsState()
    val events by eventViewModel.events.collectAsState()
    LazyColumn(modifier = Modifier
                .fillMaxSize()
    ) {
        items(events){ event ->
              TaskItem(event,eventViewModel)
        }
    }
}

//Tarjeta individual donde se guarda la informacion de la tarea
@Composable
fun TaskItem (
    event: Event,
    eventViewModel: EventViewModel,
    modifier: Modifier = Modifier
) {
    var expandEvent by remember { mutableStateOf(false)}
    val message by eventViewModel.message.observeAsState("")
    Card (
        modifier = modifier
            .padding(8.dp)
            .animateContentSize()
            .height(if (expandEvent) 180.dp else 80.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expandEvent = !expandEvent
            },
          colors = CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.primaryContainer,
              contentColor = Color(0xFFEAE2B7)
          )
    ){
        Row (
            modifier = modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = modifier
                .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        if(expandEvent){
                Box(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = event.description,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .padding(horizontal = 20.dp,
                                   // vertical = 8.dp
                                )
                        )
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                    Text(
                        text = "Termina "+ event.end,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    )
                    /*Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    ) {
                            IconButton(
                                onClick = {} ,
                                modifier = Modifier
                                    //.align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Localized description",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    //.align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Localized description",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }


                    }*/
                }
        }
    }
}
/*@Preview(showBackground = true)
@Composable
fun TaskItemPreview () {
   TaskItem()
}*/
