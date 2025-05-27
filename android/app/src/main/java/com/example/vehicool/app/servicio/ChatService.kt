package com.example.vehicool.app.servicio

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import vehicool.backend.DTO.salida.ChatDto

/**
 * Servicio Retrofit para manejar las operaciones relacionadas con el chat.
 */

interface ChatService {
    @GET("api/chat/{id}")
    fun getMensajes(@Path("id") id: Long): Call<List<ChatDto>>
}
