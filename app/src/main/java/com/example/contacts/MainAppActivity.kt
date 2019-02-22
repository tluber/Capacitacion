package com.example.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment

class MainAppActivity : AppCompatActivity() {

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        token = intent.getStringExtra("token")

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment = UsersFragment()
        var bundle = bundleOf("token" to token)
        when (item.itemId) {
            R.id.nav_users -> {
                selectedFragment = UsersFragment()
                selectedFragment.arguments = bundle
            }
            R.id.nav_settings -> {
                selectedFragment = SettingsFragment()
                selectedFragment.arguments = bundle
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
        return@OnNavigationItemSelectedListener true
    }
}
