package vehicool.backend.servicio.api

import GenericServiceAPI
import vehicool.backend.DTO.ReparacionDTO
import vehicool.backend.entities.Reparacion

interface ReparacionServiceAPI : GenericServiceAPI<Reparacion,Long>{
    fun obtenerTodosDTO(): MutableList<ReparacionDTO>?
    fun obtenerPorIdDTO(id: Long): ReparacionDTO?
    fun crearReparacion(reparacion: Reparacion): ReparacionDTO?
}
