package com.example.vehicool.app.servicio

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import vehicool.backend.DTO.salida.ChatDto

object ChatWebSocket {

    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()
    private var listener: ((ChatDto) -> Unit)? = null

    fun connect(repairId: Long, onMessageReceived: (ChatDto) -> Unit) {
        val request = Request.Builder()
            .url("ws://10.0.2.2:8080/ws/chat/$repairId")
            .build()

        listener = onMessageReceived

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                val message = Gson().fromJson(text, ChatDto::class.java)
                listener?.invoke(message)
            }
        })
    }

    fun sendMessage(chatDto: ChatDto) {
        val json = Gson().toJson(chatDto)
        webSocket?.send(json)
    }

    fun close() {
        webSocket?.close(1000, null)
        webSocket = null
    }
}
