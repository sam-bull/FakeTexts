package com.example.muzmatch.faketexts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.muzmatch.faketexts.databinding.ViewMessageBinding

class MessagesAdaptor(private val messages: List<Message>, private val context: Context) :
    RecyclerView.Adapter<MessagesAdaptor.MessageViewHolder>() {

    class MessageViewHolder(val binding: ViewMessageBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ViewMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.binding.apply {
            text = message.message
            background = message.getBackground()?.let { getDrawable(context, it) }
            gravity= message.getGravity()
        }
    }
}