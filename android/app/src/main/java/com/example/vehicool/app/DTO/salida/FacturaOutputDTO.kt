package com.example.vehicool.app.DTO.salida

data class FacturaOutputDTO(
    val id: Long?= null,
    val fecha: String,
    val importeTotal: Double = 0.0,
    val usuarioId: Long,
    var reparacionId: Long
)