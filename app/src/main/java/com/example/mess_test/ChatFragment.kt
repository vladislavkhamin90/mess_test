package com.example.mess_test

import ChatWebSocket
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mess_test.model.Message

class ChatFragment : Fragment(R.layout.frag_chat) {

    private lateinit var socket: ChatWebSocket
    private lateinit var adapter: ChatAdapter
    private val messages = mutableListOf<Message>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val toUserId = arguments?.getString("userId")
            ?: run {
                return
            }

        val myUserId = requireContext()
            .getSharedPreferences("auth", Context.MODE_PRIVATE)
            .getString("token", null)
            ?: run {
                return
            }

        adapter = ChatAdapter(myUserId)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
        }
        recycler.adapter = adapter

        socket = ChatWebSocket()
        socket.connect(myUserId) { message ->
            messages.add(message)
            adapter.submitList(messages.toList())
            recycler.scrollToPosition(messages.lastIndex)
        }

        view.findViewById<Button>(R.id.sendBtn).setOnClickListener {
            val input = view.findViewById<EditText>(R.id.message)
            val text = input.text.toString()

            if (text.isBlank()) return@setOnClickListener

            val msg = Message(
                from = myUserId,
                to = toUserId,
                text = text,
                timestamp = System.currentTimeMillis()
            )

            socket.send(msg)

            messages.add(msg)
            adapter.submitList(messages.toList())
            recycler.scrollToPosition(messages.lastIndex)

            input.setText("")
        }

        val toolbar = view.findViewById<Toolbar>(R.id.toolbarMess)

        toolbar.setNavigationOnClickListener { view
            findNavController().navigate(
                R.id.action_register_to_users
            )
        }
    }

    override fun onDestroyView() {
        if (::socket.isInitialized) {
            socket.close()
        }
        super.onDestroyView()
    }
}
