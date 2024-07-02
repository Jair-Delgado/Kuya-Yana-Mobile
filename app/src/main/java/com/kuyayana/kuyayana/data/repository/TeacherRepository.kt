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
    private val teacherCollection = db.collection("teachers")
    private val subjectCollection = db.collection("subject")

    suspend fun addTeacher(teacher: Teacher, subjectName: Subject) {
        try {
            val uid = auth.currentUser?.uid
            if (uid != null){
                val teacherData = hashMapOf(
                    "teacherName" to teacher.teacherName,
                    "teacherLastName" to teacher.teacherLastName,
                    "email" to teacher.email,
                    "phoneNumber" to teacher.phoneNumber,
                    "user" to hashMapOf(uid to true),
                    "subject" to hashMapOf(
                        "subjectName" to subjectName
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

                val snapshot = teacherCollection.
                whereEqualTo("users.$uid", true)
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
    /*suspend fun getSubjects(): List<Subject> {
        return db.collection("subject")
            .get()
            .await()
            .toObjects(Subject::class.java)
    }*/
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
     suspend fun getSubjectDocumentReference(subject: Subject): DocumentReference {

        val query = db.collection("subject")
            .whereEqualTo("subjectName", subject.subjectName)
        val result = query.get().await()

        if (result.documents.isNotEmpty()) {
            return result.documents[0].reference
        } else {
            throw IllegalArgumentException("Subject document not found for subject: ${subject.subjectName}")
        }
    }

}