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
    suspend fun getTeachers(): List<Teacher> {
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
    }
}