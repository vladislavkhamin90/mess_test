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

        val prefs = requireContext()
            .getSharedPreferences("auth", Context.MODE_PRIVATE)

        val token = prefs.getString("token", null)
        if (!token.isNullOrEmpty()) {
            findNavController().navigate(
                R.id.action_login_to_users
            )
            return
        }

        val tokenInput = view.findViewById<EditText>(R.id.token)
        val loginBtn = view.findViewById<Button>(R.id.loginBtn)
        val registerBtn = view.findViewById<Button>(R.id.goRegisterBtn)

        loginBtn.setOnClickListener {
            val entered = tokenInput.text.toString()
            if (entered.isBlank()) return@setOnClickListener

            prefs.edit()
                .putString("token", entered)
                .apply()

            findNavController().navigate(
                R.id.action_login_to_users
            )
        }

        registerBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_login_to_register
            )
        }
    }
}
