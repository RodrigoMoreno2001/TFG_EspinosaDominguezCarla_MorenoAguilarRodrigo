package com.example.vehicool.app.vista


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R
import com.example.vehicool.app.api.RetrofitClient
import com.example.vehicool.app.utils.SessionManager
import com.example.vehicool.app.vista.adaptadores.ChatAdapter
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.salida.ChatDto

class FragmentChat : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private val mensajes = mutableListOf<ChatDto>()
    private var reparacionId: Long? = null
    private lateinit var webSocket: WebSocket
    private lateinit var editTextMensaje: EditText
    private lateinit var botonEnviar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // recupera el id de la reparacion (cada reparacion tiene su chat)
        reparacionId = arguments?.getLong("reparacionId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vista = inflater.inflate(R.layout.fragment_chat, container, false)

        val emisorId = SessionManager(requireContext()).getId()
        editTextMensaje = vista.findViewById(R.id.etMensaje)
        botonEnviar = vista.findViewById(R.id.btnEnviar)
        recyclerView = vista.findViewById(R.id.chatRecyclerView)
        adapter = ChatAdapter(mensajes, SessionManager(requireContext()).getId())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Se recuperan mensajes antiguos y se establece la conexión WebSocket

        obtenerMensajes(reparacionId!!)
        conectarWebSocket(reparacionId!!)

        botonEnviar.setOnClickListener {
            val mensajeTexto = editTextMensaje.text.toString()
            if (mensajeTexto.isNotEmpty()) {
                val mensaje = ChatDto(
                    emisorId = emisorId,
                    receptorId = 1, // este id siempre va ser el mecanico
                    reparacionId = reparacionId!!,
                    mensaje = mensajeTexto,
                )
                enviarMensaje(mensaje)
                editTextMensaje.text.clear()
            }
        }

        return vista
    }
    // Obtiene el historial de mensajes desde el servidor usando Retrofit
    fun obtenerMensajes(id: Long){
        RetrofitClient.chatService.getMensajes(id).enqueue(object : Callback<List<ChatDto>> {
            override fun onResponse(call: Call<List<ChatDto>>, response: Response<List<ChatDto>>) {
                if (response.isSuccessful && response.body() != null) {
                    mensajes.clear()
                    mensajes.addAll(response.body()!!)
                    recyclerView.scrollToPosition(mensajes.size - 1)
                }
            }

            override fun onFailure(call: Call<List<ChatDto>>, t: Throwable) {
                Log.e("LOGIN", "Error de conexión", t)
            }
        })
    }
    // Establece una conexión WebSocket con el servidor para recibir mensajes en tiempo real
    private fun conectarWebSocket(id: Long) {
        val request = Request.Builder()
            .url("ws://10.0.2.2:8080/ws/chat/$id")
            .build()

        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            // esta parte se ejecuta cuando se recibe un mensaje de un cliente externo
            override fun onMessage(webSocket: WebSocket, text: String) {
                val mensaje = Gson().fromJson(text, ChatDto::class.java)
                requireActivity().runOnUiThread {
                    adapter.agregarMensaje(mensaje)
                    recyclerView.scrollToPosition(adapter.itemCount - 1)
                }
            }
        })
    }

    private fun enviarMensaje(mensaje: ChatDto) {
        val jsonMensaje = Gson().toJson(mensaje)
        webSocket.send(jsonMensaje)
        editTextMensaje.text.clear()
    }
}