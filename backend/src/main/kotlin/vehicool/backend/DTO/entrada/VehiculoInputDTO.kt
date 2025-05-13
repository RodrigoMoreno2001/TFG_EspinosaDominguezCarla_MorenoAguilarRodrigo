package vehicool.backend.DTO.entrada

data class VehiculoInputDTO(
    val id: Long?= null,
    val matricula: String = "",
    val modelo: String = "",
    val anyo: Int = 2001,
    val usuarioId: Long,
)

