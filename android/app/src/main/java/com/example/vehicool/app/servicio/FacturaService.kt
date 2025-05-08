package com.example.vehicool.app.servicio

import com.example.vehicool.app.entidades.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import vehicool.backend.DTO.AutenticarDTO
import vehicool.backend.DTO.UsuarioDTO

interface UsuarioService {

    @POST("api/usuarios/autenticar")
    fun autenticar(@Body credenciales: AutenticarDTO): Call<UsuarioDTO>

    @POST("api/usuarios/")
    fun crearUsuario(@Body usuario: Usuario): Call<UsuarioDTO>

    @GET("api/usuarios/{id}")
    fun getUsuario(@Path("id") id: Long): Call<UsuarioDTO>
}
