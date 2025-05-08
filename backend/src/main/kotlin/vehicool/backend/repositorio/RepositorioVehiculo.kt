package vehicool.backend.repositorio

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import vehicool.backend.entities.Factura
import vehicool.backend.entities.Vehiculo

@Repository
interface RepositorioVehiculo: CrudRepository<Vehiculo, Long>{
    fun findAllByUsuarioId(usuarioId: Long): List<Vehiculo>
}
