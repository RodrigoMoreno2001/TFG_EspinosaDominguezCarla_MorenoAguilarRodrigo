package com.example.vehicool.app.servicio
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import vehicool.backend.DTO.entrada.FacturaDTO
import com.example.vehicool.app.DTO.salida.FacturaOutputDTO

/**
 * Servicio Retrofit para operaciones relacionadas con facturas.
 */

interface FacturaService {

    @GET("api/factura/{id}")
    fun getFactura(@Path("id") id: Long): Call<FacturaDTO>

    @GET("api/factura/usuario/{id}")
    fun getFacturaPorIdUsuario(@Path("id") id: Long): Call<List<FacturaDTO>>

    @POST("api/factura/")
    fun crearFactura(@Body factura: FacturaOutputDTO): Call<FacturaDTO>
}
