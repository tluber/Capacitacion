package com.example.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.contacts.models.User
import com.example.contacts.models.UserEdit
import com.example.contacts.models.rest.ApiClient
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyProfileEditActivity : AppCompatActivity() {

    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_edit)

        token = intent.getStringExtra("token")
    }

    fun navigateBack(view: View) {
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

    fun saveUser(view: View){

        val apiClient = ApiClient()

        val firstName = findViewById<TextInputLayout>(R.id.firstName)
        val firstNameStr = firstName.editText!!.text.toString()
        val lastName = findViewById<TextInputLayout>(R.id.lastName)
        val lastNameStr = lastName.editText!!.text.toString()
        val sex = findViewById<TextInputLayout>(R.id.sex)
        val sexStr = sex.editText!!.text.toString()
        val birthday = findViewById<TextInputLayout>(R.id.birthday)
        val birthdayStr = birthday.editText!!.text.toString()

        val user = UserEdit(firstNameStr, lastNameStr, birthdayStr, sexStr)

        if (validateField(firstName) && validateField(lastName) && validateField(sex) && validateField(birthday)){

            GlobalScope.launch(Dispatchers.Main){
                val resultResponse = apiClient.editUser(token, user).await()

                if (resultResponse.result == "OK"){
                    Toast.makeText(view.context, "Usuario actualizado!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
