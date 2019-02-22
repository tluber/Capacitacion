package com.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.contacts.models.User
import com.example.contacts.models.rest.ApiClient
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    fun navigateBackToLoginActivity(view: View) {
        // Do something in response to button
        finish()
    }

    private fun validateField(field: TextInputLayout): Boolean {

        val fieldStr = field.editText!!.text.toString().trim()

        if (fieldStr.isEmpty()){
            field.error = "El campo no puede estar vacío"
            return false
        } else {
            field.error = null
            return true
        }
    }

    fun createUser(view: View){

        val intent = Intent(this, MainAppActivity::class.java).apply {
        }
        val apiClient = ApiClient()

        val firstName = findViewById<TextInputLayout>(R.id.firstName)
        val firstNameStr = firstName.editText!!.text.toString()
        val lastName = findViewById<TextInputLayout>(R.id.lastName)
        val lastNameStr = lastName.editText!!.text.toString()
        val email = findViewById<TextInputLayout>(R.id.email)
        val emailStr = email.editText!!.text.toString()
        val password = findViewById<TextInputLayout>(R.id.password)
        val sex = findViewById<TextInputLayout>(R.id.sex)
        val sexStr = sex.editText!!.text.toString()
        val birthday = findViewById<TextInputLayout>(R.id.birthday)
        val birthdayStr = birthday.editText!!.text.toString()
        val user: User = User(birthdayStr, emailStr, firstNameStr, sexStr, "", lastNameStr)

        if (validateField(firstName) && validateField(lastName) && validateField(email) && validateField(password) && validateField(sex) && validateField(birthday)){

            GlobalScope.launch(Dispatchers.Main){
                val loginResponse = apiClient.registerUser(user).await()

                if (loginResponse.token.isEmpty()){
                    Toast.makeText(view.context, "noooooooooo", Toast.LENGTH_SHORT).show()
                } else {
                    intent.putExtra("token", loginResponse.token)
                    startActivity(intent)
                }
            }
        }
    }
}
