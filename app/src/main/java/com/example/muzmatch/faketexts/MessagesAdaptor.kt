package com.example.muzmatch.faketexts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muzmatch.faketexts.databinding.*

class MessagesAdaptor(
    private val messages: List<Message>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentMessageViewHolder(val binding: ViewSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ReceivedMessageViewHolder(val binding: ViewReceivedMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    class TimestampViewHolder(val binding: ViewTimestampBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewType.SENT.value -> SentMessageViewHolder(
                ViewSentMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.RECEIVED.value -> ReceivedMessageViewHolder(
                ViewReceivedMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> TimestampViewHolder(
                ViewTimestampBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (getItemViewType(position)) {
            ViewType.SENT.value -> (holder as SentMessageViewHolder).binding.apply {
                text = message.message
                hasTail = message.hasTail
            }
            ViewType.RECEIVED.value -> (holder as ReceivedMessageViewHolder).binding.apply {
                text = message.message
                hasTail = message.hasTail
            }
            ViewType.TIMESTAMP.value -> (holder as TimestampViewHolder).binding.apply { text = message.message}
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return when (message.type) {
            MessageType.SENT -> ViewType.SENT.value
            MessageType.RECEIVED -> ViewType.RECEIVED.value
            MessageType.TIMESTAMP -> ViewType.TIMESTAMP.value
        }
    }
}

enum class ViewType(val value: Int) {
    SENT(0),
    RECEIVED(1),
    TIMESTAMP(2)
}