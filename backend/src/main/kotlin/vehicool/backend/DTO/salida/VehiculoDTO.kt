package vehicool.backend.DTO

import vehicool.backend.entities.Usuario

data class VehiculoDTO(
    val matricula: String = "",
    val modelo: String = "",
    val anyo: Int = 2001
)
