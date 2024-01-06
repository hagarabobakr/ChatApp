package com.example.chatapp.database

import com.example.chatapp.database.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreUtils {
    val usersCollectionName = "users"
    fun insertUserInDatabase(user: User): Task<Void> {
        val docRef = getUsersCollection()
            .document(user.id!!)
        return docRef.set(user)
    }

    fun getUsersCollection(): CollectionReference {
        val dataBase = FirebaseFirestore.getInstance()
        return dataBase.collection(usersCollectionName)
    }
    fun getUserFromDatabase(uid:String): Task<DocumentSnapshot> {
        val docRef = getUsersCollection()
            .document(uid)
        return docRef.get()
    }
}