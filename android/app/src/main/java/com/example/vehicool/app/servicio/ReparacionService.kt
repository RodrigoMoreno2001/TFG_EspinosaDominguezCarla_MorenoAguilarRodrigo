package com.example.vehicool.app.servicio
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import vehicool.backend.DTO.entrada.FacturaDTO
import com.example.vehicool.app.DTO.salida.FacturaOutputDTO
import com.example.vehicool.app.DTO.salida.ReparacionOutputDTO
import vehicool.backend.DTO.entrada.ReparacionDTO
/**
 * Servicio Retrofit para operaciones relacionadas con reparaciones
 */

interface ReparacionService {

    @GET("api/reparacion/todos")
    fun getReparaciones(): Call<List<ReparacionDTO>>

    @GET("api/reparacion/{id}")
    fun getReparacion(@Path("id") id: Long): Call<ReparacionDTO>

    @GET("api/reparacion/usuario/{id}")
    fun getPorUsuario(@Path("id") id: Long):  Call<List<ReparacionDTO>>

    @POST("api/reparacion/")
    fun crearReparacion(@Body reparacion: ReparacionOutputDTO): Call<ReparacionDTO>

}
