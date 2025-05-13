package com.example.vehicool.app.api
import com.example.vehicool.app.servicio.ChatService
import com.example.vehicool.app.servicio.FacturaService
import com.example.vehicool.app.servicio.UsuarioService
import com.example.vehicool.app.servicio.VehiculoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, JsonSerializer<LocalDateTime> { src, _, _ ->
            JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
        })
        .create()
    
    val usuarioService: UsuarioService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UsuarioService::class.java)
    }

    val facturaService: FacturaService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(FacturaService::class.java)
    }

    val vehiculoService: VehiculoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(VehiculoService::class.java)
    }

    val chatService: ChatService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ChatService::class.java)
    }
}
