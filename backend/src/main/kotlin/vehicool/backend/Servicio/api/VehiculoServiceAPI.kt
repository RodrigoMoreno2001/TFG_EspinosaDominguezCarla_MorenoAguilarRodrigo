package vehicool.backend.servicio.api

import GenericServiceAPI
import vehicool.backend.DTO.VehiculoDTO
import vehicool.backend.entities.Usuario
import vehicool.backend.entities.Vehiculo

interface VehiculoServiceAPI : GenericServiceAPI<Vehiculo,Long>{
    fun obtenerTodosDTO(): MutableList<VehiculoDTO>?
    fun obtenerPorIdDTO(id: Long): VehiculoDTO?
    fun crearCoche(vehiculo: Vehiculo): VehiculoDTO?
    fun eliminarCoche(id: Long)
}
