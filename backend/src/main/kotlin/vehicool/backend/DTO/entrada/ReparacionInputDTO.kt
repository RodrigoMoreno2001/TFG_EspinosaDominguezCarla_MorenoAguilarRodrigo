package vehicool.backend.DTO

import java.time.LocalDate

data class ReparacionInputDTO(
    val id: Long?= null,
    val fechaEntrada: LocalDate = LocalDate.now(),
    val estado: String,
    val servicios: String,
    val vehiculoId: Long,
    val facturaId: Long? = null
)
