package vehicool.backend.repositorio
import org.springframework.data.jpa.repository.Query
import vehicool.backend.entities.Usuario
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RepositorioUsuario : CrudRepository<Usuario, Long>{

    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.contrasena = :contrasena")

    fun autenticar(@Param("correo") correo: String, @Param("contrasena") contrasena: String): Usuario?
    fun existsByCorreo(correo: String): Boolean
}


