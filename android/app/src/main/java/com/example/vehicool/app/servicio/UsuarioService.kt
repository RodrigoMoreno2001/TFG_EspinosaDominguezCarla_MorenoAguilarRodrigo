package com.example.vehicool.app.servicio

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import vehicool.backend.DTO.AutenticarDTO
import vehicool.backend.DTO.UsuarioDTO

interface UsuarioService {
    @POST("api/usuarios/autenticar")
    fun autenticar(@Body credenciales: AutenticarDTO): Call<UsuarioDTO>
}
