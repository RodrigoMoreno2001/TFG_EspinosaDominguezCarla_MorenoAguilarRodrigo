package vehicool.backend.DTO.entrada

data class UsuarioDTO(
    val id: Long,
    val nombre: String,
    val correo: String,
    val privilegios: String
)
