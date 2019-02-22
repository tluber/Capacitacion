package com.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import kotlinx.android.synthetic.main.activity_main.*

class TermsConditionsActivity : AppCompatActivity() {

    private var termsAndConditionsWebView: WebView? = null
    private val url = "http://creativecore.com.ar/capacitacion/terms.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_conditions)

        termsAndConditionsWebView = findViewById(R.id.termsAndConditionsWebView)
        termsAndConditionsWebView!!.loadUrl(url)
    }

    fun navigateToMainActivity(view: View) {
        // Do something in response to button
        finish()
    }
}
