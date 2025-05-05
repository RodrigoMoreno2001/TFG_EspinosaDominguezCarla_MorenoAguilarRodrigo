package vehicool.backend.controlador

import vehicool.backend.servicio.api.UsuarioServiceAPI
import vehicool.backend.entities.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vehicool.backend.DTO.AutenticarDTO
import vehicool.backend.DTO.UsuarioDTO
import vehicool.backend.repositorio.RepositorioUsuario

@RestController
@RequestMapping("/api/usuarios")
class ControladorUsuario(private val repositorioUsuario: RepositorioUsuario) {

    @Autowired
    lateinit var usuarioServiceAPI: UsuarioServiceAPI

    @GetMapping("/todos")
    fun getAll(): MutableList<UsuarioDTO>? {
        return usuarioServiceAPI.obtenerTodosDTO()
    }
    @GetMapping("/{id}")
    fun getUsuario(@PathVariable id: Long): UsuarioDTO? {
        return usuarioServiceAPI.obtenerPorIdDTO(id)
    }

    @PostMapping("/autenticar")
    fun autenticar(@RequestBody credenciales: AutenticarDTO): ResponseEntity<UsuarioDTO> {

        val dto = usuarioServiceAPI.autenticarUsuario(credenciales.correo, credenciales.contrasena)

        return if (dto != null) {
            ResponseEntity.ok(dto)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("/")
    fun crearUsuario(@RequestBody usuario: Usuario): ResponseEntity<UsuarioDTO> {
        var dto = usuarioServiceAPI.crearUsuario(usuario)
        return ResponseEntity.ok(dto)
    }
}
