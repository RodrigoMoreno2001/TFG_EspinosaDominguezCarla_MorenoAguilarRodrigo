package vehicool.backend.DTO

data class ReparacionInputDTO(
    val id: Long?= null,
    val estado: String,
    val servicios: String,
    val vehiculoId: Long,
    val facturaId: Long? = null
)
