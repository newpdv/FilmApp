package com.example.lab3

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    private lateinit var formEmail: EditText
    private lateinit var formPassword: EditText
    private lateinit var authError: TextView
    private lateinit var loginButton: Button

    private var testLogin = "test@test.com"
    private var testPassword = "qwerty"

    private var testWrongLogin = "somewronguser"
    private var testWrongPassword = "somewrongpassword"

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testAuthorizeInit() {
        activityRule.scenario.onActivity {
            authError = it.findViewById(R.id.log_error)

            assertFalse(authError.isVisible)
        }
    }

    @Test
    fun testAuthorizeValid() {
        activityRule.scenario.onActivity {
            formEmail = it.findViewById(R.id.et_email)
            formPassword = it.findViewById(R.id.et_password)
            loginButton = it.findViewById(R.id.btn_login)
            authError = it.findViewById(R.id.log_error)

            formEmail.setText(testLogin)
            formPassword.setText(testPassword)
            loginButton.performClick()

            assertFalse(authError.isVisible)
        }
    }

    @Test
    fun testAuthorizeInvalid() {
        activityRule.scenario.onActivity {
            formEmail = it.findViewById(R.id.et_email)
            formPassword = it.findViewById(R.id.et_password)
            loginButton = it.findViewById(R.id.btn_login)
            authError = it.findViewById(R.id.log_error)

            formEmail.setText(testWrongLogin)
            formPassword.setText(testWrongPassword)
            loginButton.performClick()

            assertTrue(authError.isVisible)
        }
    }
}