package com.kuyayana.kuyayana.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kuyayana.kuyayana.data.models.User
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("user")

    suspend fun addUser(user: User){
        userCollection.document(user.uid).set(user).await()
    }



}