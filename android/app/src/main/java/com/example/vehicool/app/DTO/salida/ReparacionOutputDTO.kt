package com.example.vehicool.app.DTO.salida

import java.time.LocalDate

data class ReparacionOutputDTO(
    val id: Long? = null,
    val fechaEntrada: LocalDate = LocalDate.now(),
    val estado: String,
    val servicios: String? = null,
    val motivos: String,
    val vehiculoId: Long,
    val facturaId: Long? = null
)