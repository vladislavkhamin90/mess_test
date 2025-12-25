package com.example.mess_test

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mess_test.network.RegisterRequest
import com.example.mess_test.network.RetrofitClient
import kotlinx.coroutines.launch

class RegisterFragment : Fragment(R.layout.frag_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val username = view.findViewById<EditText>(R.id.username)
        val password = view.findViewById<EditText>(R.id.password)

        view.findViewById<Button>(R.id.registerBtn).setOnClickListener {
            lifecycleScope.launch {
                val res = RetrofitClient.api.register(
                    RegisterRequest(
                        username.text.toString(),
                        password.text.toString()
                    )
                )

                requireContext()
                    .getSharedPreferences("auth", Context.MODE_PRIVATE)
                    .edit()
                    .putString("token", res.token)
                    .apply()

                findNavController().navigate(
                    R.id.action_register_to_users
                )
            }
        }
    }
}
