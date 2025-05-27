package vehicool.backend.DTO.entrada

data class FacturaDTO(
    val id: Long? = 0,
    val fecha: String,
    val servicios: String,
    val importeTotal: Double = 0.0,
    val usuario: Long,
    val reparacion: Long? = null
)
