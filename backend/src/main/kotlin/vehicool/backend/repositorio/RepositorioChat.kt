package vehicool.backend.repositorio

import org.springframework.data.repository.CrudRepository
import vehicool.backend.entities.MensajeChat

interface RepositorioChat: CrudRepository<MensajeChat, Long> {
    fun findAllByReparacionIdOrderByHoraAsc(reparacionId: Long): List<MensajeChat>
}