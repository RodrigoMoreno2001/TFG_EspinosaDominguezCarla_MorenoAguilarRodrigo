package vehicool.backend.DTO.salida

data class UsuarioDTO(
    val id: Long,
    val nombre: String,
    val correo: String,
    val vehiculos: MutableList<Long>
)
