package com.kuyayana.kuyayana.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kuyayana.kuyayana.data.models.Category
import kotlinx.coroutines.tasks.await


class CategoryRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val categoryCollection = db.collection("category")

    suspend fun addCategory(category: Category) {
        try {
            val uid = auth.currentUser?.uid
            if (uid != null){
                val categoryData = hashMapOf(
                    "categoryName" to category.categoryName,
                    "users" to hashMapOf(uid to true) // Map with uid as key and true as value
                )
                categoryCollection
                    .document()
                    .set(categoryData)
                    .await()
            }

            //categoryCollection.document(category.id).set(category).await()

            Log.d("CategoryRepository", "Category added: ${category.categoryName}")

        } catch (e: Exception) {

            Log.e("CategoryRepository", "Error adding category", e)
        }
    }

    suspend fun getCategories(): List<Category> {
        return try {

            Log.d("CategoryRepository", "Fetching categories from Firestore")
            val uid = auth.currentUser?.uid

            if (uid != null) {

                val snapshot = categoryCollection.
                whereEqualTo("users.$uid", true)
                    .get()
                    .await()

                snapshot.toObjects(Category::class.java)
            }else{
                Log.e("CategoryRepository", "Error: User not authenticated")
                emptyList()
            }


        } catch (e: Exception) {

            Log.e("CategoryRepository", "Error getting items", e)
            emptyList()

        }
    }
}