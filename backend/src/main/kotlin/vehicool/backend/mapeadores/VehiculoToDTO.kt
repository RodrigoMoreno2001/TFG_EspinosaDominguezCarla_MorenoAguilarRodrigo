package vehicool.backend.mapeadores

import vehicool.backend.DTO.UsuarioDTO
import vehicool.backend.DTO.VehiculoDTO
import vehicool.backend.entities.Usuario
import vehicool.backend.entities.Vehiculo

fun Vehiculo.vehiculoToDTO(): VehiculoDTO {
    return VehiculoDTO(
        matricula = this.matricula,
        modelo = this.modelo,
        anyo = this.anyo,
    )
}
