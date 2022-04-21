package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.lab3.Models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var formEmail: EditText
    private lateinit var formPassword: EditText
    private lateinit var authError: TextView
    private var db: FilmsDb = FilmsDb()

    private fun hashString(input: String): String {
        return MessageDigest.getInstance("SHA-256")
                            .digest(input.toByteArray())
                            .fold("") { str, it -> str + "%02x".format(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.btn_login)

        formEmail = findViewById(R.id.et_email)
        formPassword = findViewById(R.id.et_password)

        authError = findViewById(R.id.log_error)

        loginButton.setOnClickListener {
            var login = formEmail.text.toString()
            var password = formPassword.text.toString()

            db.getUser(login) { user ->
                if (user?.password == hashString(password))
                {
                    authError.visibility = View.INVISIBLE

                    var userPremium = user?.role == "premium"
                    val intent = Intent(this, ListActivity::class.java)
                    intent.putExtra("USER_PREMIUM", userPremium)
                    startActivity(intent)
                }
                else
                {
                    authError.visibility = View.VISIBLE
                }
            }
        }
    }
}