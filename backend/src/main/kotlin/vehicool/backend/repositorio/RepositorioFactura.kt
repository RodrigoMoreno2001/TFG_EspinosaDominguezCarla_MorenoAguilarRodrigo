package vehicool.backend.repositorio

import org.springframework.data.repository.CrudRepository
import vehicool.backend.entities.Factura

interface RepositorioFactura: CrudRepository<Factura, Long> {
}