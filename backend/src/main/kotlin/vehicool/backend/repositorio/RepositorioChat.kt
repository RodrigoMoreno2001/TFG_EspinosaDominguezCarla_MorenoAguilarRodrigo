package vehicool.backend.repositorio

import org.springframework.data.repository.CrudRepository
import vehicool.backend.entities.MensajeChat

// Hereda de CrudRepository, que proporciona operaciones CRUD básicas

interface RepositorioChat: CrudRepository<MensajeChat, Long> {

    // Este metodo genera una query automaticamente por convencion de nombres de Spring Data JPA

    fun findAllByReparacionIdOrderByHoraAsc(reparacionId: Long): List<MensajeChat>

}