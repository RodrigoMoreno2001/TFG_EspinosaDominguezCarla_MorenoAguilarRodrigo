package com.example.vehicool.app.entidades

data class Usuario(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val privilegios: String = "usuario"
)