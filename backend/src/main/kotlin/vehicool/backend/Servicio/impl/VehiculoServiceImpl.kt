package vehicool.backend.servicio.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import vehicool.backend.DTO.entrada.VehiculoInputDTO
import vehicool.backend.DTO.salida.VehiculoDTO
import vehicool.backend.entities.Vehiculo
import vehicool.backend.genericas.GenericServiceImpl
import vehicool.backend.mapeadores.toVehiculo
import vehicool.backend.mapeadores.vehiculoToDTO
import vehicool.backend.repositorio.RepositorioReparacion
import vehicool.backend.repositorio.RepositorioUsuario
import vehicool.backend.repositorio.RepositorioVehiculo
import vehicool.backend.servicio.api.VehiculoServiceAPI

/**
 * Servicio de vehiculo. Siempre devolvemos DTOs y recibimos InputDTOs del cliente.
 */

@Service
class VehiculoServiceImpl : GenericServiceImpl<Vehiculo, Long>(), VehiculoServiceAPI {

    @Autowired
    lateinit var repositorioVehiculo: RepositorioVehiculo

    @Autowired
    lateinit var usuarioRepositorio: RepositorioUsuario

    @Autowired
    lateinit var reparacionRepositorio: RepositorioReparacion

    override val dao: CrudRepository<Vehiculo, Long>
        get() {
            return repositorioVehiculo
        }

    override fun obtenerTodosDTO(): MutableList<VehiculoDTO>? {
        return dao.findAll().map{ it.vehiculoToDTO() }.toMutableList()
    }

    override fun obtenerPorIdDTO(id: Long): VehiculoDTO? {
        return dao.findById(id).orElse(null)?.vehiculoToDTO()
    }

    override fun crearCoche(dto: VehiculoInputDTO): VehiculoDTO {

        val usuario = usuarioRepositorio.findById(dto.usuarioId).orElseThrow {
            RuntimeException("Usuario no encontrado")
        }

        return dao.save(dto.toVehiculo(usuario)).vehiculoToDTO()
    }

    override fun obtenerPorIdUsuarioDTO(id: Long): List<VehiculoDTO> {
        return repositorioVehiculo.findAllByUsuarioId(id).map { it.vehiculoToDTO()}
    }

    override fun eliminarCoche(id: Long) {
        dao.deleteById(id)
    }
}