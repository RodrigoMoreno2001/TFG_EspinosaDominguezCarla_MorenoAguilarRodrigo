package vehicool.backend.servicio.api

import GenericServiceAPI
import vehicool.backend.DTO.entrada.VehiculoInputDTO
import vehicool.backend.DTO.salida.VehiculoDTO
import vehicool.backend.entities.Vehiculo

interface VehiculoServiceAPI : GenericServiceAPI<Vehiculo,Long>{
    fun obtenerTodosDTO(): MutableList<VehiculoDTO>?
    fun obtenerPorIdDTO(id: Long): VehiculoDTO?
    fun crearCoche(dto: VehiculoInputDTO): VehiculoDTO
    fun obtenerPorIdUsuarioDTO(id: Long):  List<VehiculoDTO>
    fun eliminarCoche(id: Long)
}
