package com.kuyayana.kuyayana.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.common.base.Objects
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import com.kuyayana.kuyayana.data.models.Record
import com.kuyayana.kuyayana.data.models.Section
import com.kuyayana.kuyayana.data.models.Subject
import com.kuyayana.kuyayana.data.models.Teacher
import kotlinx.coroutines.tasks.await

class RecordRepository {
    private val db = FirebaseFirestore.getInstance()
    private val recordCollection = db.collection("record")
    private val auth = FirebaseAuth.getInstance()


    suspend fun getRecords(): List<Record> {
        try {
            val uid = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            val snapshot = recordCollection.whereEqualTo("subject.user.id", uid).get().await()
            return snapshot.documents.mapNotNull { document ->
                val id = document.id
                val finalGrade = document.getDouble("finalGrade")as? Double ?: return@mapNotNull null

                val sectionsMap = document.get("sections") as? MutableList<Map<String,Any>> ?: return@mapNotNull null
                val sections: MutableList<Section> = mutableListOf()
                sectionsMap.forEach{section->

                    val auxSectionGrade = section.get("sectionGrade") as? Number ?: return@mapNotNull null
                    val auxResults = section.get("showResults") as? Boolean ?: return@mapNotNull null
                    val auxPercentage = section.get("percentage") as? Number ?: return@mapNotNull null
                    val auxGrades = section.get("grades") as? MutableList<Double> ?: return@mapNotNull null

                    val auxSection = Section(auxPercentage,auxGrades,auxSectionGrade,auxResults)
                    sections.add(auxSection)
                }

                val subjectMap = document.get("subject") as? Map<String, Any>
                val subjectName = subjectMap?.get("subjectName") as? String ?: return@mapNotNull null
                val subjectId = subjectMap?.get("id") as? String ?: return@mapNotNull null
                val userMap = subjectMap?.get("user") as? Map<String, Any>
                val userId = userMap?.get("id") as? String ?: return@mapNotNull null

                val subject = Subject(subjectName, subjectId)
                Log.d(TAG, "getRecords: "+ Record(id,finalGrade, sections, subject))
                Record(id,finalGrade, sections, subject)

            }

        }catch (e: Exception){
            Log.e(TAG, "getRecords: ",e )
            return emptyList()
        }

    }
    suspend fun updateRecord(record : Record?){
        val uid = auth.currentUser?.uid ?: throw Exception("User not authenticated")
        val recordData = hashMapOf(

            "finalGrade" to record?.finalGrade,
            "sections" to record?.sections,
            "subject" to hashMapOf(
                "id" to record?.subject?.id,
                "subjectName" to record?.subject?.subjectName,
                "user" to hashMapOf(
                    "id" to uid
                )
            )
        )
        if (record != null) {
            recordCollection.document(record.id).set(recordData)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                .await()
        }
    }

    suspend fun deleteTeacher(record: Record) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            // Suponiendo que el nombre es único y se puede usar para buscar el documento
            val querySnapshot = recordCollection
                .whereEqualTo("subject.subjectName", record.subject?.subjectName)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                document.reference.delete().await()
            } else {
                Log.e("RecordRepository", "Error: No teacher found with the given criteria")
            }
        } else {
            Log.e("RecordRepository", "Error: User not authenticated")
        }
    }
}