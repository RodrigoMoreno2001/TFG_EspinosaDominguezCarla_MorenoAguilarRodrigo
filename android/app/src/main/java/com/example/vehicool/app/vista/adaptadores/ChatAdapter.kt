package com.example.vehicool.app.vista.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R
import vehicool.backend.DTO.salida.ChatDto

// El adaptador recibe la lista de mensajes y el ID del emisor actual (para saber qué mensajes son propios).

class ChatAdapter(private val messages: MutableList<ChatDto>,private val emisorId:Long) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Constantes que identifican si un mensaje es recibido o enviado

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }

    // Crea la vista del ViewHolder según si el mensaje es enviado o recibido.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cv_mensaje_enviado, parent, false)
                SentMessageViewHolder(view)
            }
            VIEW_TYPE_RECEIVED -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cv_mensaje_recibido, parent, false)
                ReceivedMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
    // Enlaza el contenido del mensaje al ViewHolder correspondiente.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is SentMessageViewHolder -> holder.bind(message)
            is ReceivedMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    // Determina si el mensaje es enviado o recibido.

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]

        return if (message.emisorId == emisorId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    fun agregarMensaje(message: ChatDto) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
    // ViewHolder para los mensajes enviados por el usuario.
    class SentMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageTextView: TextView = view.findViewById(R.id.textMensaje)

        fun bind(message: ChatDto) {
            messageTextView.text = message.mensaje
        }
    }
    // ViewHolder para los mensajes recibidos por el usuario.
    class ReceivedMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageTextView: TextView = view.findViewById(R.id.textMensaje)

        fun bind(message: ChatDto) {
            messageTextView.text = message.mensaje
        }
    }
}
