package com.example.vehicool.app.servicio
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import vehicool.backend.DTO.entrada.FacturaDTO
import com.example.vehicool.app.DTO.salida.FacturaOutputDTO
import vehicool.backend.DTO.entrada.ReparacionDTO

interface ReparacionService {

    @GET("api/reparacion/{id}")
    fun getReparacion(@Path("id") id: Long): Call<ReparacionDTO>
}
