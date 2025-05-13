package com.example.vehicool.app.servicio
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import vehicool.backend.DTO.entrada.FacturaDTO
import com.example.vehicool.app.DTO.salida.FacturaOutputDTO
import com.example.vehicool.app.DTO.salida.VehiculoOutputDTO
import vehicool.backend.DTO.entrada.VehiculoDTO

interface VehiculoService {

    @GET("api/vehiculos/{id}")
    fun getVehiculo(@Path("id") id: Long): Call<VehiculoDTO>

    @GET("api/vehiculos/usuarios/{id}")
    fun getVehiculoPorIdUsuario(@Path("id") id: Long): Call<List<VehiculoDTO>>

    @POST("api/vehiculos/")
    fun crearVehiculo(@Body vehiculo: VehiculoOutputDTO): Call<VehiculoDTO>
}
