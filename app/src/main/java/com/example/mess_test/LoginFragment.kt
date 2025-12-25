package com.example.mess_test

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.frag_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tokenInput = view.findViewById<EditText>(R.id.token)

        val long = view.findViewById<Button>(R.id.loginBtn)
        val register = view.findViewById<Button>(R.id.registerBtn)

        long.setOnClickListener {
            requireContext()
                .getSharedPreferences("auth", Context.MODE_PRIVATE)
                .edit()
                .putString("token", tokenInput.text.toString())
                .apply()

            findNavController().navigate(R.id.usersFragment)
        }

        register.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }
}
