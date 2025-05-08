package vehicool.backend.mapeadores

import vehicool.backend.DTO.salida.ReparacionDTO
import vehicool.backend.entities.Reparacion

fun Reparacion.reparacionToDTO(): ReparacionDTO {
    return ReparacionDTO(
        fechaEntrada = this.fechaEntrada,
        estado = this.estado,
        servicios = this.servicios,
        vehiculo = this.vehiculo.id,
        factura = this.factura?.id
    )
}
