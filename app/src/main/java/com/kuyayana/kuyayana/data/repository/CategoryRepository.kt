package com.kuyayana.kuyayana.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kuyayana.kuyayana.data.models.Category
import kotlinx.coroutines.tasks.await


class CategoryRepository {
    private val db = FirebaseFirestore.getInstance()
    private val categoryCollection = db.collection("category")

    suspend fun addCategory(category: Category) {
        try {
            categoryCollection.document(category.categoryName).set(category).await()

            Log.d("CategoryRepository", "Category added: ${category.categoryName}")

        } catch (e: Exception) {

            Log.e("CategoryRepository", "Error adding category", e)
        }
    }

    suspend fun getCategories(): List<Category>{
        return try {

            val snapshot = categoryCollection.get().await()
            //snapshot.toObjects(Category::class.java)

            snapshot.documents.map { document ->
                val category = document.toObject(Category::class.java)
                category?.copy(id = document.id) // Asignar el ID del documento a la categoría
            }.filterNotNull()

        }catch (e: Exception){
            Log.e("CategoryRepository", "Error getting categories",e)
            emptyList()
        }
    }
}