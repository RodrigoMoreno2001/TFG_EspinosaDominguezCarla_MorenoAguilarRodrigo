package vehicool.backend.DTO.entrada
import java.time.LocalDate

data class FacturaInputDTO(
    val id: Long?= null,
    val fecha: LocalDate = LocalDate.now(),
    val servicios: String= "",
    val importeTotal: Double = 0.0,
    val usuarioId: Long,
    var reparacionId: Long
)

