package vehicool.backend.mapeadores

import vehicool.backend.DTO.salida.UsuarioDTO
import vehicool.backend.entities.Usuario

fun Usuario.usuarioToDTO(): UsuarioDTO {
    return UsuarioDTO(
        id = this.id,
        nombre = this.nombre,
        correo = this.correo,
        privilegios = this.privilegios,
        vehiculos = this.vehiculos.map { it.id }.toMutableList()
    )
}
