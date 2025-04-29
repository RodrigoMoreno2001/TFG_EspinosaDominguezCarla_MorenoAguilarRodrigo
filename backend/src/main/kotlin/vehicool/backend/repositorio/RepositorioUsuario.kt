package vehicool.backend.repositorio
import vehicool.backend.entities.Usuario
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioUsuario : CrudRepository<Usuario, Long>
