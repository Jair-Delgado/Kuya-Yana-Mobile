package com.kuyayana.kuyayana.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kuyayana.kuyayana.data.models.Subject
import kotlinx.coroutines.tasks.await

class SubjectRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val subjectCollection = db.collection("subject")

    suspend fun addSubject(subject: Subject) {
        try {
            val uid = auth.currentUser?.uid
            if (uid != null){
                val subjectData = hashMapOf(
                    "subjectName" to subject.subjectName,
                    "users" to hashMapOf(uid to true)
                )
                subjectCollection
                    .document()
                    .set(subjectData)
                    .await()
            }
            Log.d("SubjectRepository", "Subject added: ${subject.subjectName}")
        } catch (e: Exception) {

            Log.e("SubjectRepository", "Error adding a subject", e)
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
    suspend fun getSubjectDocumentById(subjectId: String): DocumentReference?{
        return try {
            val docRef = subjectCollection.document(subjectId)
            val docSnapshot = docRef.get().await()

            if (docSnapshot.exists()) {
                docRef
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("SubjectRepository", "Error getting subject document by ID", e)
            null
        }
    }
}