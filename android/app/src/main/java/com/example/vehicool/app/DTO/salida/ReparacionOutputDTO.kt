package com.example.vehicool.app.DTO.salida

data class ReparacionOutputDTO(
    val id: Long?= null,
    val fechaEntrada: String,
    val estado: String,
    val servicios: String,
    val vehiculoId: Long,
    val facturaId: Long? = null
)