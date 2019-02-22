package com.example.contacts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.contacts.models.User
import com.example.contacts.models.rest.ApiClient
import com.example.contacts.models.rest.entity.UsersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UsersFragment : Fragment() {

    private lateinit var listView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_users, container, false)

        val apiClient = ApiClient()

        var usersList : UsersResponse

        val token = arguments!!.getString("token")

        val intent = Intent(activity, ProfileDetailActivity::class.java).apply {
        }

        GlobalScope.launch(Dispatchers.Main){
            usersList = apiClient.getUsers(token).await()
            listView = view.findViewById(R.id.user_list)
            listView.adapter = CustomAdapter(activity, usersList)

            listView.setOnItemClickListener { parent, view, position, id ->

                val user = parent.getItemAtPosition(position) as User
                //val userBundle = bundleOf("user" to user)

                intent.putExtra("userFirstName", user.firstName)
                intent.putExtra("userLastName", user.lastName)
                intent.putExtra("userEmail", user.email)
                intent.putExtra("userGender", user.gender)
                intent.putExtra("userBirthday", user.birthday)
                startActivity(intent)
                //Toast.makeText(activity, "${user.firstName} - ${user.lastName} - ${user.gender} - ${user.birthday}", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private class CustomAdapter(context: Context?, usersResponse: UsersResponse?) : BaseAdapter() {

        private val thisContext : Context?
        private val uResponse : UsersResponse?

        init {
            thisContext = context
            uResponse = usersResponse
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(thisContext)
            val userRow = layoutInflater.inflate(R.layout.user_list_row, parent, false)
            val usersList = uResponse!!.users

            val fullName = userRow.findViewById<TextView>(R.id.username)
            val firstName = usersList.get(position).firstName
            val lastName = usersList.get(position).lastName

            fullName.text = firstName + " " + lastName

            return userRow
        }

        override fun getItem(position: Int): Any {
            return uResponse!!.users.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return uResponse!!.users.size
        }

    }
}