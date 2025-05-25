package vehicool.backend.mapeadores

import vehicool.backend.DTO.salida.VehiculoDTO
import vehicool.backend.entities.Vehiculo

fun Vehiculo.vehiculoToDTO(): VehiculoDTO {
    return VehiculoDTO(
        id = this.id,
        matricula = this.matricula,
        modelo = this.modelo,
        anyo = this.anyo,
    )
}
