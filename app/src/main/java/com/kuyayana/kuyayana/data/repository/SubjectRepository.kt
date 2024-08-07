package com.kuyayana.kuyayana.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.kuyayana.kuyayana.data.models.Section
import com.kuyayana.kuyayana.data.models.Subject
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.tasks.await

class SubjectRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val subjectCollection = db.collection("subject")
    private val recordCollection = db.collection("record")

    @SuppressLint("SuspiciousIndentation")
    suspend fun addSubject(subject: Subject) {
        try {
            val uid = auth.currentUser?.uid
            if (uid != null){
                val subjectData = hashMapOf(
                    "id" to subject.id,
                    "subjectName" to subject.subjectName,
                    "user" to hashMapOf("id" to uid)
                )

               val documentRef = subjectCollection
                    .add(subjectData)
                    .await()
                 documentRef
                     .update("id",documentRef.id)
                     .await()
                documentRef.id

                val recordData = hashMapOf(
                    "id" to "",
                    "finalGrade" to 0.0,
                    "sections" to mutableListOf<Section>(),
                    "subject" to hashMapOf(
                        "id" to documentRef.id,
                        "subjectName" to subject.subjectName,
                        "user" to hashMapOf("id" to uid)
                    )
                )
                val recordRef = recordCollection.add((recordData)).await()
                recordRef.update("id",recordRef.id).await()
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
                whereEqualTo("user.id", uid)
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
            val snapshot = recordCollection.whereEqualTo("subject.id",subjectId).get().await()
            if (!snapshot.isEmpty){
                snapshot.forEach{
                    recordCollection.document(it.id).delete().await()
                }
            }

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
                Log.d("updateSubject: ",documentId)
                val snapshot =recordCollection.whereEqualTo("subject.id",documentId).get().await()
                Log.d( "updateSubject: ", snapshot.documents.size.toString())
                if (!snapshot.isEmpty){
                    val subjectMap = snapshot.documents[0].get("subject") as? Map<String, Any>
                    val subjectId = subjectMap?.get("id") as? String ?

                    val recordData = hashMapOf(
                            "id" to snapshot.documents[0].id,
                        "finalGrade" to snapshot.documents[0].get("finalGrade"),
                        "sections" to snapshot.documents[0].get("sections"),
                        "subject" to hashMapOf(
                        "id" to subjectId,
                        "subjectName" to newData,
                        "user" to hashMapOf("id" to uid)
                    )
                    )
                    recordCollection.document(snapshot.documents[0].id).update(recordData).await()
                }
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