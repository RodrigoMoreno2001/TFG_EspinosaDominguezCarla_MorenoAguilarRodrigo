package vehicool.backend.servicio.api

import GenericServiceAPI
import vehicool.backend.DTO.salida.ReparacionDTO
import vehicool.backend.DTO.entrada.ReparacionInputDTO
import vehicool.backend.entities.Reparacion

interface ReparacionServiceAPI : GenericServiceAPI<Reparacion,Long>{
    fun obtenerTodosDTO(): MutableList<ReparacionDTO>?
    fun obtenerPorIdDTO(id: Long): ReparacionDTO?
    fun crearReparacion(reparacionInputDTO: ReparacionInputDTO): ReparacionDTO?
    fun obtenerPorIdUsuario(id: Long): List<ReparacionDTO>
}
