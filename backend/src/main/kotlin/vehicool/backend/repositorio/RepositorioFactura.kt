package vehicool.backend.repositorio

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import vehicool.backend.entities.Factura
import vehicool.backend.entities.Usuario

interface RepositorioFactura: CrudRepository<Factura, Long> {
    fun findAllByUsuarioId(usuarioId: Long): List<Factura>
}