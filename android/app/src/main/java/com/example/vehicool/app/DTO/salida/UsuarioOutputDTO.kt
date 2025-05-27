package com.example.vehicool.app.DTO.salida

data class UsuarioOutputDTO(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val privilegios: String = "Usuario"
)