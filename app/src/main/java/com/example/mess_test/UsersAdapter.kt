package com.example.mess_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mess_test.model.User

class UsersAdapter(
    private val users: List<User>,
    private val onClick: (User) -> Unit
) : RecyclerView.Adapter<UsersAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.username)
    }

    override fun onCreateViewHolder(p: ViewGroup, v: Int): VH =
        VH(
            LayoutInflater.from(p.context)
            .inflate(R.layout.item_user, p, false))

    override fun onBindViewHolder(h: VH, i: Int) {
        val user = users[i]
        h.name.text = user.username
        h.itemView.setOnClickListener { onClick(user) }
    }

    override fun getItemCount() = users.size
}
