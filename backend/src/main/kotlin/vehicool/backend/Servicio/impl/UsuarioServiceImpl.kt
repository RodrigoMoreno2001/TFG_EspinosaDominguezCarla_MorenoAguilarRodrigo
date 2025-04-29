package vehicool.backend.Servicio.impl

import vehicool.backend.Genericas.GenericServiceImpl
import vehicool.backend.Servicio.api.UsuarioServiceAPI
import vehicool.backend.entities.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import vehicool.backend.repositorio.RepositorioUsuario

@Service
class UsuarioServiceImpl : GenericServiceImpl<Usuario, Long>(), UsuarioServiceAPI {
    @Autowired
    lateinit var usuarioRepository: RepositorioUsuario

    override val dao: CrudRepository<Usuario, Long>
        get() {
            return usuarioRepository
        }
}