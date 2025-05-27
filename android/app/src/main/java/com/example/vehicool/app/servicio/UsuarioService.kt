package com.example.vehicool.app.servicio

import com.example.vehicool.app.DTO.salida.UsuarioOutputDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.vehicool.app.DTO.salida.AutenticarDTO
import vehicool.backend.DTO.entrada.UsuarioDTO
/**
 * Servicio Retrofit para operaciones relacionadas con usuarios
 */
interface UsuarioService {

    @POST("api/usuarios/autenticar")
    fun autenticar(@Body credenciales: AutenticarDTO): Call<UsuarioDTO>

    @POST("api/usuarios/")
    fun crearUsuario(@Body usuarioOutputDTO: UsuarioOutputDTO): Call<UsuarioDTO>

    @GET("api/usuarios/{id}")
    fun getUsuario(@Path("id") id: Long): Call<UsuarioDTO>
}
