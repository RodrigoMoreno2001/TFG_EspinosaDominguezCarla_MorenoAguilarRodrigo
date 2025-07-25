package vehicool.backend.mapeadores

import vehicool.backend.DTO.salida.ReparacionDTO
import vehicool.backend.entities.Reparacion

fun Reparacion.reparacionToDTO(): ReparacionDTO {
    return ReparacionDTO(
        id = this.id,
        fechaEntrada = this.fechaEntrada,
        estado = this.estado,
        servicios = this.servicios,
        motivos = this.motivos,
        vehiculo = this.vehiculo.vehiculoToDTO(),
        factura = this.factura?.id
    )
}
