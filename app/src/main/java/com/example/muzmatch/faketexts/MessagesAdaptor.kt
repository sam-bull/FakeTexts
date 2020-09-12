package com.example.muzmatch.faketexts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muzmatch.faketexts.databinding.ViewMessageBinding

class MessagesAdaptor(private val messages: List<String>) :
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
        holder.binding.apply {
            text = messages[position]
        }
    }
}