package vehicool.backend.mapeadores
import vehicool.backend.DTO.FacturaDTO
import vehicool.backend.entities.Factura

fun Factura.facturaToDTO(): FacturaDTO {
    return FacturaDTO(
        id = this.id,
        fecha = this.fecha,
        importeTotal = this.importeTotal,
        usuario = this.usuario.id,
        reparacion = this.reparacion?.id ?: 0
    )
}
