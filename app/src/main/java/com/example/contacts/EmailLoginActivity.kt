package com.example.contacts

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.contacts.models.rest.ApiClient
import com.example.contacts.models.rest.base.ServiceInterceptor
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmailLoginActivity : AppCompatActivity() {

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        dialog.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_login)
    }

    fun navigateBackToActivity(view: View) {
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

    fun login(view: View) {
        // Do something in response to button
        val intent = Intent(this, MainAppActivity::class.java).apply {
        }
        val username = findViewById<TextInputLayout>(R.id.emailTextInputLayout)
        val password = findViewById<TextInputLayout>(R.id.passwordTextInputLayout)

        val usernameStr = username.editText!!.text.toString()
        val passwordStr = password.editText!!.text.toString()

        val base = "$usernameStr:$passwordStr"
        val authHeader = "Basic ${Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP)}"

        ServiceInterceptor().setToken(authHeader)

        val apiClient = ApiClient()

        if (validateField(username) && validateField(password)) {
            GlobalScope.launch(Dispatchers.Main){
                val loginResponse = apiClient.login2(authHeader).await()

                if (loginResponse.token.isEmpty()){
                    showWrongUsernamePasswordAlert()
                } else {
                    intent.putExtra("token", loginResponse.token)
                    startActivity(intent)
                }
            }
        }
    }

    private fun showWrongUsernamePasswordAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Email o Contraseña incorrecto")
        builder.setPositiveButton("Ok", positiveButtonClick)
        builder.show()
    }
}
