package vehicool.backend.servicio.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import vehicool.backend.DTO.VehiculoDTO
import vehicool.backend.entities.Usuario
import vehicool.backend.entities.Vehiculo
import vehicool.backend.genericas.GenericServiceImpl
import vehicool.backend.mapeadores.vehiculoToDTO
import vehicool.backend.repositorio.RepositorioVehiculo
import vehicool.backend.servicio.api.VehiculoServiceAPI

@Service
class VehiculoServiceImpl : GenericServiceImpl<Vehiculo, Long>(), VehiculoServiceAPI {

    @Autowired
    lateinit var repositorioVehiculo: RepositorioVehiculo

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

    override fun crearCoche(vehiculo: Vehiculo): VehiculoDTO? {
        return dao.save(vehiculo).vehiculoToDTO()
    }

    override fun eliminarCoche(id: Long) {
        dao.deleteById(id)
    }
}