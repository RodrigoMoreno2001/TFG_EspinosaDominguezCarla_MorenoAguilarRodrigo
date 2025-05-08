package vehicool.backend.mapeadores
import vehicool.backend.DTO.entrada.FacturaInputDTO
import vehicool.backend.DTO.salida.FacturaDTO
import vehicool.backend.entities.Factura
import vehicool.backend.entities.Reparacion
import vehicool.backend.entities.Usuario

fun FacturaInputDTO.toFactura(usuario: Usuario, reparacion: Reparacion): Factura{
    return Factura(
        this.id,
        this.fecha,
        this.importeTotal,
        usuario,
        reparacion
    );
}
