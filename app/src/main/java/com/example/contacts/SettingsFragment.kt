package com.example.contacts

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.contacts.models.rest.ApiClient
import com.example.contacts.models.rest.base.ServiceInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)

        val token = arguments!!.getString("token")

        val myProfileButton = view.findViewById<Button>(R.id.myProfileButton)
        val termsAndConditionButton = view.findViewById<Button>(R.id.termsAndConditionsButton)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        myProfileButton.setOnClickListener { navigateToMyProfile(token) }
        termsAndConditionButton.setOnClickListener { navigateToTermsAndConditions() }
        logoutButton.setOnClickListener { navigateToLogin(token, view) }

        return view
    }

    private fun navigateToMyProfile(token: String){

        val intent = Intent(activity, MyProfileDetailActivity::class.java).apply {
        }

        val apiClient = ApiClient()

        GlobalScope.launch(Dispatchers.Main){
            val user = apiClient.getUser(token).await()

            intent.putExtra("userFirstName", user.firstName)
            intent.putExtra("userLastName", user.lastName)
            intent.putExtra("userEmail", user.email)
            intent.putExtra("userGender", user.gender)
            intent.putExtra("userBirthday", user.birthday)
            intent.putExtra("token", token)
            startActivity(intent)
        }
    }

    private fun navigateToTermsAndConditions(){

        val intent = Intent(activity, TermsConditionsActivity::class.java).apply {
        }
        startActivity(intent)
    }

    private fun navigateToLogin(token: String, view: View){

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Atención")
        builder.setMessage("¿Está seguro que quiere cerrar sesión?")
        builder.setPositiveButton("Si") { dialog: DialogInterface, which: Int ->
            logout(token)
        }
        builder.setNegativeButton("No") { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun logout(token: String){

        val intent = Intent(activity, LoginActivity::class.java).apply {
        }
        val apiClient = ApiClient()

        GlobalScope.launch(Dispatchers.Main){
            val resultResponse = apiClient.logout(token).await()

            /*if (resultResponse.result == "OK"){ result es null no se por que!!
                startActivity(intent)
            }*/
            startActivity(intent)
        }

    }
}