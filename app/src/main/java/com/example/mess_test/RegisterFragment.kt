package com.example.mess_test

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mess_test.network.ApiProvider
import com.example.mess_test.network.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterFragment : Fragment(R.layout.frag_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val usernameEt = view.findViewById<EditText>(R.id.username)
        val passwordEt = view.findViewById<EditText>(R.id.password)
        val registerBtn = view.findViewById<Button>(R.id.registerBtn)

        val prefs = requireContext()
            .getSharedPreferences("auth", Context.MODE_PRIVATE)

        registerBtn.setOnClickListener {

            val username = usernameEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Введите логин и пароль",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = ApiProvider.api.register(
                        RegisterRequest(
                            username = username,
                            password = password
                        )
                    )

                    prefs.edit()
                        .putString("token", response.token)
                        .putString("userId", response.userId)
                        .apply()

                    findNavController().navigate(
                        R.id.action_registerFragment_to_loginFragment
                    )

                } catch (e: HttpException) {
                    when (e.code()) {
                        400 -> {
                            Toast.makeText(
                                requireContext(),
                                "Пользователь уже существует или данные неверны",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                requireContext(),
                                "Ошибка сервера: ${e.code()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка сети",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
