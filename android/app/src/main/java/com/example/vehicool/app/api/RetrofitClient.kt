package com.example.vehicool.app.api
import com.example.vehicool.app.servicio.ChatService
import com.example.vehicool.app.servicio.FacturaService
import com.example.vehicool.app.servicio.ReparacionService
import com.example.vehicool.app.servicio.UsuarioService
import com.example.vehicool.app.servicio.VehiculoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Objeto singleton que configura y proporciona acceso a Retrofit.
 */
object RetrofitClient {

    // 10.0.2.2 apunta al localhost de la máquina host.
    private const val BASE_URL = "http://10.0.2.2:8080/"

    /**
     * Configuración personalizada de Gson para adaptar tipos de fecha y hora.
     */
    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val usuarioService: UsuarioService by lazy { retrofit.create(UsuarioService::class.java) }

    val facturaService: FacturaService by lazy { retrofit.create(FacturaService::class.java) }

    val reparacionService: ReparacionService by lazy { retrofit.create(ReparacionService::class.java) }

    val vehiculoService: VehiculoService by lazy { retrofit.create(VehiculoService::class.java) }

    val chatService: ChatService by lazy { retrofit.create(ChatService::class.java) }
}
