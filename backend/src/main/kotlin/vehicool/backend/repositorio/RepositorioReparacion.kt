package vehicool.backend.repositorio

import org.springframework.data.repository.CrudRepository
import vehicool.backend.entities.Reparacion

interface RepositorioReparacion: CrudRepository<Reparacion, Long> {
    // Este metodo genera una query automaticamente por convencion de nombres de Spring Data JPA
    fun findAllByVehiculo_Usuario_Id(usuarioId: Long): List<Reparacion>
}