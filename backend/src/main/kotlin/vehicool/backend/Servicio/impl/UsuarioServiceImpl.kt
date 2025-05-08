package vehicool.backend.servicio.impl

import vehicool.backend.genericas.GenericServiceImpl
import vehicool.backend.servicio.api.UsuarioServiceAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import vehicool.backend.DTO.salida.UsuarioDTO
import vehicool.backend.entities.Usuario
import vehicool.backend.mapeadores.usuarioToDTO
import vehicool.backend.repositorio.RepositorioUsuario

@Service
class UsuarioServiceImpl : GenericServiceImpl<Usuario, Long>(), UsuarioServiceAPI {
    @Autowired
    lateinit var repositorioUsuario: RepositorioUsuario

    override val dao: CrudRepository<Usuario, Long>
        get() {
            return repositorioUsuario
        }

    override fun autenticarUsuario(correo: String, contrasena: String): UsuarioDTO? {
        return repositorioUsuario.autenticar(correo, contrasena)?.usuarioToDTO()
    }
    override fun obtenerTodosDTO(): MutableList<UsuarioDTO> {
        return dao.findAll().map { it.usuarioToDTO() }.toMutableList()
    }


    override fun obtenerPorIdDTO(id: Long): UsuarioDTO? {
        return dao.findById(id).orElse(null)?.usuarioToDTO()
    }

    override fun crearUsuario(usuario: Usuario): UsuarioDTO? {
        return dao.save(usuario).usuarioToDTO()
    }
}