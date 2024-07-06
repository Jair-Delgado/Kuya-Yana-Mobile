package com.kuyayana.kuyayana.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.kuyayana.kuyayana.data.models.Subject
import kotlinx.coroutines.flow.merge
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
                    "id" to subject.id,
                    "subjectName" to subject.subjectName,
                    "users" to hashMapOf(uid to true)
                )
               val documentRef = subjectCollection
                    .add(subjectData)
                    .await()
                 documentRef
                     .update("id",documentRef.id)
                     .await()
                documentRef.id
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
                /*snapshot.documents.map { document ->
                    val subject = document.toObject(Subject::class.java)
                    if (subject != null){
                        subject.id = document.id
                    }
                }*/

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
    suspend fun deleteSubject(subjectId:String){
        val uid = auth.currentUser?.uid
        if (uid != null){
            subjectCollection
                .document(subjectId)
                .delete()
                .await()
        }else{
            Log.e("SubjectRepository", "Error: User not authenticated")
        }
    }
    suspend fun updateSubject(documentId : String, newData: String){
        try {
            val uid = auth.currentUser?.uid
            if (uid != null){
                val data = hashMapOf(
                    "subjectName" to newData
                )
                subjectCollection
                    .document(documentId)
                    //.set(data, SetOptions.merge())
                    .update("subjectName",newData)
                    .await()
                Log.d("SubjectRepository", "Subject with ID $documentId updated successfully")
            }else{
                Log.e("SubjectRepository", "Error: User not authenticated")
            }
        }catch (e: Exception){
            Log.e("SubjectRepository", "Error updating subject", e)
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