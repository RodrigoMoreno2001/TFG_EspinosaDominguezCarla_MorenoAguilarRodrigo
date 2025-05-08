package vehicool.backend.DTO.salida

import java.time.LocalDate

data class ReparacionDTO(
    val fechaEntrada: LocalDate = LocalDate.now(),
    val estado: String = "",
    val servicios: String = "",
    val vehiculo: Long,
    val factura: Long?
)
