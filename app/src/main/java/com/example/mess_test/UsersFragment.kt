package com.example.mess_test

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mess_test.network.RetrofitClient
import kotlinx.coroutines.launch

class UsersFragment : Fragment(R.layout.frag_users) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val toolbar = view.findViewById<Toolbar>(R.id.toolbarUsers)

        val navController = findNavController()

        toolbar.setNavigationOnClickListener{
            navController.popBackStack()
        }

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            val users = RetrofitClient.api.getUsers()
            recycler.adapter = UsersAdapter(users) { user ->

                val bundle = Bundle().apply {
                    putString("userId", user.id)
                }

                findNavController()
                    .navigate(R.id.chatFragment, bundle)
            }
        }
    }
}
