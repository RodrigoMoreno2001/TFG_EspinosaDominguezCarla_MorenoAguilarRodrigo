package vehicool.backend.DTO

data class ReparacionDTO(
    val fechaEntrada: String,
    val estado: String = "",
    val servicios: String = "",
    val vehiculo: Long,
    val factura: Long?
)
