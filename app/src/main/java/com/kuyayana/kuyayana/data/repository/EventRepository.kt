package com.kuyayana.kuyayana.data.repository

import android.content.ContentValues.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.models.Event
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import kotlinx.coroutines.tasks.await
import android.util.Log
import com.kuyayana.kuyayana.data.models.Record
import com.kuyayana.kuyayana.data.models.Section

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

    suspend fun getEvents(): List<Event> {
        try {
            val uid = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            val snapshot = eventCollection.whereEqualTo("teacher.subject.user.id", uid).whereNotEqualTo("eventCategory.categoryName","clase").get().await()
            return snapshot.documents.mapNotNull { document ->
                val id = document.id
                val description = document.get("description")as? String ?: return@mapNotNull null
                val title = document.get("title")as? String ?: return@mapNotNull null
                val start = document.get("start")as? String ?: return@mapNotNull null
                val end = document.get("end")as? String ?: return@mapNotNull null

                val teacherMap = document.get("teacher") as? Map<String,Any>
                val teacherName = teacherMap?.get("teacherName") as? String ?: return@mapNotNull  null
                val email = teacherMap?.get("email") as? String ?: return@mapNotNull  null
                val phoneNumber = teacherMap?.get("phoneNumber") as? String ?: return@mapNotNull  null
                val teacherLastName = teacherMap?.get("teacherLastName") as? String ?: return@mapNotNull  null

                val subjectMap = teacherMap.get("subject") as? Map<String, Any>
                val subjectName = subjectMap?.get("subjectName") as? String ?: return@mapNotNull null
                val userMap = subjectMap?.get("user") as? Map<String, Any>
                val userId = userMap?.get("id") as? String ?: return@mapNotNull null
                val subject = Subject(subjectName, userId)

                val teacher  =Teacher(teacherName,teacherLastName,email,phoneNumber,subject)

                Event(id,title, description, teacher,start,end)

            }
        }catch (e: Exception){
            Log.e(TAG, "getEvents: ",e )
            return  emptyList()
        }


    }
    suspend fun getClass(): List<Event> {
        try {
            val uid = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            val snapshot = eventCollection.whereEqualTo("teacher.subject.user.id", uid).whereEqualTo("eventCategory.categoryName","clase").get().await()
            return snapshot.documents.mapNotNull { document ->

                val id = document.id
                val description = document.get("description")as? String ?: return@mapNotNull null
                val title = document.get("title")as? String ?: return@mapNotNull null
                val start = document.get("start")as? String ?: return@mapNotNull null
                val end = document.get("end")as? String ?: return@mapNotNull null
                val teacherMap = document.get("teacher") as? Map<String,Any>
                val teacherName = teacherMap?.get("teacherName") as? String ?: return@mapNotNull  null
                val email = teacherMap?.get("email") as? String ?: return@mapNotNull  null
                val phoneNumber = teacherMap?.get("phoneNumber") as? String ?: return@mapNotNull  null
                val teacherLastName = teacherMap?.get("teacherLastName") as? String ?: return@mapNotNull  null
                val subjectMap = teacherMap.get("subject") as? Map<String, Any>
                val subjectName = subjectMap?.get("subjectName") as? String ?: return@mapNotNull null
                val userMap = subjectMap?.get("user") as? Map<String, Any>
                val userId = userMap?.get("id") as? String ?: return@mapNotNull null
                val subject = Subject(subjectName, userId)

                val teacher  =Teacher(teacherName,teacherLastName,email,phoneNumber,subject)

                Event(id,title, description, teacher,start,end)

            }
        }catch (e: Exception){
            Log.e(TAG, "getClass: ",e )
            return emptyList()
        }

    }
}