package com.example.vehicool.app.api
import com.example.vehicool.app.servicio.FacturaService
import com.example.vehicool.app.servicio.UsuarioService
import com.example.vehicool.app.servicio.VehiculoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val usuarioService: UsuarioService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioService::class.java)
    }

    val facturaService: FacturaService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FacturaService::class.java)
    }

    val vehiculoService: VehiculoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VehiculoService::class.java)
    }
}
