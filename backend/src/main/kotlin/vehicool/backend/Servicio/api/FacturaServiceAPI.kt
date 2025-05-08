package vehicool.backend.servicio.api

import GenericServiceAPI
import vehicool.backend.DTO.salida.FacturaDTO
import vehicool.backend.DTO.entrada.FacturaInputDTO
import vehicool.backend.entities.Factura

interface FacturaServiceAPI : GenericServiceAPI<Factura,Long>{
    fun obtenerTodosDTO(): MutableList<FacturaDTO>?
    fun obtenerPorIdDTO(id: Long): FacturaDTO?
    fun obtenerPorIdUsuarioDTO(id: Long):  List<FacturaDTO>
    fun crearFactura(dto: FacturaInputDTO): FacturaDTO?
}
