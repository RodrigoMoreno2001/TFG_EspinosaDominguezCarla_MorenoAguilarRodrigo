package vehicool.backend.DTO.salida

import java.time.LocalDate

data class ReparacionDTO(
    val id: Long? = null,
    val fechaEntrada: LocalDate = LocalDate.now(),
    val estado: String = "",
    val servicios: String? = null,
    val motivos: String,
    val vehiculo: VehiculoDTO,
    val factura: Long?
)
