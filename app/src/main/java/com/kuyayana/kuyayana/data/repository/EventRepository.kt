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
    private val eventCollection = db.collection("events")
    private val subjectCollection = db.collection("subject")
    private val teacherCollection = db.collection("teacher")
    private val categoryColecction = db.collection("category")

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
                        "phoneNumber" to teacher.phoneNumber
                    ),
                    "subject" to hashMapOf(
                        "subjectName" to subject.subjectName
                    ),
                    "user" to hashMapOf(uid to true)
                )
                //Guarda la info de eventData en el documento
                eventCollection
                    .document()
                    .set(eventData)
                    .await()
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
                    .whereEqualTo("users.$uid",true)
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
    suspend fun getSubjects(): List<Subject> {
        return try {

            Log.d("SubjectRepository", "Fetching subjects from Firestore")
            val uid = auth.currentUser?.uid

            if (uid != null) {

                val snapshot = subjectCollection.
                whereEqualTo("users.$uid", true)
                    .get()
                    .await()

                snapshot.toObjects(Subject::class.java)
            }else{
                Log.e("SubjectRepository", "Error: User not authenticated")
                emptyList()
            }


        } catch (e: Exception) {

            Log.e("SubjectRepository", "Error getting items", e)
            emptyList()

        }
    }
    suspend fun getTeachers(): List<Teacher> {
        return try {

            Log.d("TeacherRepository", "Fetching teachers from Firestore")
            val uid = auth.currentUser?.uid

            if (uid != null) {

                val snapshot = teacherCollection
                    .whereEqualTo("users.$uid", true)
                    .get()
                    .await()
                snapshot.toObjects(Teacher::class.java)
            }else{
                Log.e("TeacherRepository", "Error: User not authenticated")
                emptyList()
            }

        } catch (e: Exception) {
            Log.e("TeacherRepository", "Error getting teachers", e)
            emptyList()

        }
    }
    suspend fun getCategory(categoryName: Category): Category? {
        val snapshot = db.collection("category")
            .document(categoryName.categoryName)
            .get()
            .await()
        return snapshot.toObject(Category::class.java)
    }
    suspend fun getCategories(): List<Category>{
        return try {

            val snapshot = categoryColecction.get().await()
            snapshot.toObjects(Category::class.java)

        }catch (e: Exception){
            Log.e("CategoryRepository", "Error getting categories",e)
            emptyList()
        }
    }


}