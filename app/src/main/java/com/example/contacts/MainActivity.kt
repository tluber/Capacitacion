package com.example.contacts

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        dialog.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val termsAndConditionsTextView : TextView = findViewById(R.id.termsAndConditionsTextView)
        termsAndConditionsTextView.setOnClickListener {
            val intent = Intent(this, TermsConditionsActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }

    fun navigateToLoginActivity(view: View) {
        // Do something in response to button
        val intent = Intent(this, LoginActivity::class.java).apply {
        }
        if (termsAndConditionsCheckBox.isChecked){
            startActivity(intent)
        } else {
            showTermsAndConditionsAlert()
        }
    }

    private fun showTermsAndConditionsAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Atención")
        builder.setMessage("Para poder continuar debe aceptar los terminos y condiciones")
        builder.setPositiveButton("Ok", positiveButtonClick)
        builder.show()
    }
}
