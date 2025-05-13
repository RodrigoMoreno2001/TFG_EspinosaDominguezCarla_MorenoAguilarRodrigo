package vehicool.backend.DTO.salida

data class ChatDto(
    val emisorId: Long,
    val receptorId: Long,
    val reparacionId: Long,
    val mensaje: String,
)
