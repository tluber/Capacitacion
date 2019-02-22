package com.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun navigateToRegistrationActivity(view: View) {
        // Do something in response to button
        val intent = Intent(this, RegistrationActivity::class.java).apply {
        }
        startActivity(intent)
    }

    fun navigateToEmailLoginActivity(view: View) {
        // Do something in response to button
        val intent = Intent(this, EmailLoginActivity::class.java).apply {
        }
        startActivity(intent)
    }
}
