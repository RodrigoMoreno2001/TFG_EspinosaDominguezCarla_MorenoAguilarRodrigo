package vehicool.backend.repositorio

import org.springframework.data.repository.CrudRepository
import vehicool.backend.entities.Reparacion

interface RepositorioReparacion: CrudRepository<Reparacion, Long> {

    fun findAllByVehiculo_Usuario_Id(usuarioId: Long): List<Reparacion>
}