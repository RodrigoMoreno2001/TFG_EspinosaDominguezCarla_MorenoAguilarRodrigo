package com.example.vehicool.app.DTO

data class Usuario(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val privilegios: String = "usuario"
)
