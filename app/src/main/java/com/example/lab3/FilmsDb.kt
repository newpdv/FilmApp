package com.example.lab3

import com.example.lab3.Models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FilmsDb {
    private var db: FirebaseFirestore = Firebase.firestore

    fun getUser(login: String, response: (User?) -> Unit) {
        db.collection("users")
            .whereEqualTo("login", login)
            .limit(1)
            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty())
                {
                    for (document in it.documents) {
                        val userInfo = document.toObject(User::class.java)
                        response(userInfo)
                        break
                    }
                }
            }
    }

}