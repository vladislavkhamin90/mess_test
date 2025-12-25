package com.example.mess_test

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mess_test.model.Message

class ChatAdapter(
    private val myUserId: String
) : ListAdapter<Message, ChatAdapter.ChatViewHolder>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val bubble = itemView.findViewById<LinearLayout>(R.id.bubble)
        private val sender = itemView.findViewById<TextView>(R.id.sender)
        private val text = itemView.findViewById<TextView>(R.id.messageText)

        fun bind(message: Message) {

            val isMe = message.from == myUserId

            text.text = message.text

            sender.visibility = if (isMe) View.GONE else View.VISIBLE
            sender.text = message.from

            bubble.setBackgroundResource(
                if (isMe) R.drawable.bg_bubble_me
                else R.drawable.bg_bubble_other
            )

            val params = bubble.layoutParams as FrameLayout.LayoutParams
            params.gravity = if (isMe) Gravity.END else Gravity.START
            bubble.layoutParams = params
        }
    }

    class Diff : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(old: Message, new: Message): Boolean =
            old.timestamp == new.timestamp

        override fun areContentsTheSame(old: Message, new: Message): Boolean =
            old == new
    }
}
