package com.kuyayana.kuyayana.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import kotlinx.coroutines.tasks.await


class TeacherRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val teacherCollection = db.collection("teacher")
    private val subjectCollection = db.collection("subject")

    suspend fun addTeacher(teacher: Teacher, subject: Subject) {
        try {
            val uid = auth.currentUser?.uid
            if (uid != null){
                val teacherData = hashMapOf(

                    "teacherName" to teacher.teacherName,
                    "teacherLastName" to teacher.teacherLastName,
                    "email" to teacher.email,
                    "phoneNumber" to teacher.phoneNumber,
                    "subject" to hashMapOf(
                        "subjectName" to subject.subjectName,
                        "user" to hashMapOf(
                            "id" to uid
                        )
                    )
                )
                teacherCollection
                    .document()
                    .set(teacherData)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                    .await()
            }
            Log.d("TeacherRepository", "Teacher added: ${teacher.teacherName}")

        } catch (e: Exception) {

            Log.e("TeacherRepository", "Error adding a teacher", e)
        }
    }
    /*suspend fun getTeachers(): List<Teacher> {
        return try {

            Log.d("TeacherRepository", "Fetching teachers from Firestore")
            val uid = auth.currentUser?.uid
            if (uid != null) {

                val snapshot = teacherCollection
                    .whereEqualTo("subject.user.id", uid)
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
    }*/
    suspend fun getTeachers(): List<Teacher> {
        val uid = auth.currentUser?.uid ?: throw Exception("User not authenticated")
        val snapshot = teacherCollection.whereEqualTo("subject.user.id", uid).get().await()
        return snapshot.documents.mapNotNull { document ->
            val teacherName = document.getString("teacherName") ?: return@mapNotNull null
            val teacherLastName = document.getString("teacherLastName") ?: return@mapNotNull null
            val email = document.getString("email") ?: return@mapNotNull null
            val phoneNumber = document.getString("phoneNumber") ?: return@mapNotNull null

            val subjectMap = document.get("subject") as? Map<String, Any>
            val subjectName = subjectMap?.get("subjectName") as? String ?: return@mapNotNull null
            val userMap = subjectMap?.get("user") as? Map<String, Any>
            val userId = userMap?.get("id") as? String ?: return@mapNotNull null

            val subject = Subject(subjectName, userId)
            Teacher(teacherName, teacherLastName, email, phoneNumber, subject)
        }
    }

    suspend fun deleteTeacher(teacher: Teacher) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            // Suponiendo que el nombre es único y se puede usar para buscar el documento
            val querySnapshot = teacherCollection
                .whereEqualTo("teacherName", teacher.teacherName)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                document.reference.delete().await()
            } else {
                Log.e("TeacherRepository", "Error: No teacher found with the given criteria")
            }
        } else {
            Log.e("TeacherRepository", "Error: User not authenticated")
        }
    }
}