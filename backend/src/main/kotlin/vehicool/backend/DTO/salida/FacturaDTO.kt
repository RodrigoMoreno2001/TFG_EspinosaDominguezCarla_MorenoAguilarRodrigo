package vehicool.backend.DTO.salida

import java.time.LocalDate

data class FacturaDTO(
    val id: Long? = 0,
    val fecha: LocalDate = LocalDate.now(),
    val servicios: String,
    val importeTotal: Double = 0.0,
    val usuario: Long,
    val reparacion: Long? = null
)
