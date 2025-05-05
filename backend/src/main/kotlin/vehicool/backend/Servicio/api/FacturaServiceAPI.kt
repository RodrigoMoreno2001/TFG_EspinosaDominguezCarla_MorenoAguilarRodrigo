package vehicool.backend.servicio.api

import GenericServiceAPI
import vehicool.backend.DTO.FacturaDTO
import vehicool.backend.entities.Factura

interface FacturaServiceAPI : GenericServiceAPI<Factura,Long>{
    fun obtenerTodosDTO(): MutableList<FacturaDTO>?
    fun obtenerPorIdDTO(id: Long): FacturaDTO?
    fun crearFactura(factura: Factura): FacturaDTO?
}
