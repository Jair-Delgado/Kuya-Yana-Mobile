package com.kuyayana.kuyayana.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import kotlinx.coroutines.tasks.await
import android.util.Log

class EventRepository {

    //Instancias de Firestore
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    //Referencias a las colecciones
    private val eventCollection = db.collection("event")
    private val teacherCollection = db.collection("teacher")

    suspend fun addEvent(
        event: Event,
        subject: Subject,
        teacher:Teacher,
    ) {
        try {
            //verifica si un usuario esta registrado, sino devuelve null (se usa para que cada creacion se este vinculada al usuario)

            val uid = auth.currentUser?.uid
            if(uid != null ){
                val eventData = hashMapOf(
                    "id" to event.id,
                    "title" to event.title,
                    "description" to event.description,
                    "start" to event.start,
                    "end" to event.end,
                    "eventCategory" to hashMapOf(
                        "categoryName" to "evento"
                    ),
                    "teacher" to hashMapOf(
                        "email" to teacher.email,
                        "teacherName" to teacher.teacherName,
                        "teacherLastName" to teacher.teacherLastName,
                        "phoneNumber" to teacher.phoneNumber,
                        "subject" to hashMapOf(
                            "subjectName" to subject.subjectName,
                            "user" to hashMapOf("id" to uid)
                        ),
                    ),
                )
                //Guarda la info de eventData en el documento
                val documentReference =eventCollection
                    .add(eventData)
                    .await()
                documentReference
                    .update("id",documentReference.id)
                    .await()
                documentReference.id
            }
            Log.d("EventRepository", "Event added ${event.description}")
        }catch (e: Exception){
            Log.e("EventRepository", "Error adding a event")
        }
    }
    suspend fun getEvents(): List<Event>{
        return try {
            Log.d("EventRepository", "Fetching events from Firestore")
            val uid = auth.currentUser?.uid
            if (uid != null){
                val snapshot = eventCollection
                    //verifica que en la coleccion haya un campo users y este este en true
                    .whereEqualTo("teacher.subject.user.id",uid)
                    .get()
                    .await()
                snapshot.toObjects(Event::class.java)
            }else{
                Log.e("EventRepository", "Error: User not authenticated")
                emptyList()
            }
        }catch (e: Exception){
            Log.e("EventRepository", "Error getting events", e)
            emptyList()
        }
    }
}