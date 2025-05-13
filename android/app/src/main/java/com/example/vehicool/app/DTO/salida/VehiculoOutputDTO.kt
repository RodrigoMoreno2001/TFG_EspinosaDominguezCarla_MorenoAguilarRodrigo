package com.example.vehicool.app.DTO.salida

data class VehiculoOutputDTO(
    val id: Long? = null,
    val matricula: String = "",
    val modelo: String = "",
    val anyo: Int = 2001,
    val usuarioId: Long,
)