package com.example.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProfileDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)

        val firstName = intent.getStringExtra("userFirstName")
        val lastName = intent.getStringExtra("userLastName")
        val gender = intent.getStringExtra("userGender")
        val email = intent.getStringExtra("userEmail")
        val birthday = intent.getStringExtra("userBirthday")

        loadUser(firstName, lastName, gender, email, birthday)

    }

    private fun loadUser(firstName: String, lastName: String, gender: String, email: String, birthday: String){

        val firstNameTextView = findViewById<TextView>(R.id.firstName)
        val lastNameTextView = findViewById<TextView>(R.id.lastName)
        val sexTextView = findViewById<TextView>(R.id.sex)
        val emailTextView = findViewById<TextView>(R.id.email)
        val ageTextView = findViewById<TextView>(R.id.age)

        firstNameTextView.text = firstName
        lastNameTextView.text = lastName
        sexTextView.text = gender
        emailTextView.text = email
        ageTextView.text = birthday
    }
}
