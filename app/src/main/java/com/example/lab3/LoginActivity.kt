package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var formEmail: EditText
    private lateinit var formPassword: EditText
    private lateinit var authError: TextView

    private var userList: Map<String, String> = mapOf(
        "test@test.com" to "65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5", // qwerty
        "admin@test.com" to "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92" // 123456
    )

    private var premiumUsers: Map<String, Boolean> = mapOf(
        "admin@test.com" to true
    )

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
            if (authorize(formEmail.text.toString(), formPassword.text.toString()))
            {
                authError.visibility = View.INVISIBLE

                var userPremium = false
                if (this.premiumUsers.contains(formEmail.text.toString()))
                    userPremium = this.premiumUsers[formEmail.text.toString()] == true

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

    private fun authorize(email: String, password: String): Boolean
    {
        if (this.userList.contains(email)) {
            return this.userList[email] == hashString(password)
        }

        return false
    }
}