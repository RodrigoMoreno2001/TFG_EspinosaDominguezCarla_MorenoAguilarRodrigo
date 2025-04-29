package vehicool.backend.controlador

import vehicool.backend.Servicio.api.UsuarioServiceAPI
import vehicool.backend.entities.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vehicool.backend.repositorio.RepositorioUsuario

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(private val repositorioUsuario: RepositorioUsuario) {

    @Autowired
    lateinit var usuarioServiceAPI: UsuarioServiceAPI

    @GetMapping("/todos")
    fun getAll(): MutableList<Usuario>? {
        return usuarioServiceAPI.all
    }
    @GetMapping("/{id}")
    fun getUsuario(@PathVariable id: Long): Usuario? {
        return usuarioServiceAPI.get(id)
    }
    @PostMapping("/")
    fun crearUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        var obj = usuarioServiceAPI.save(usuario)
        return ResponseEntity<Usuario>(usuario, HttpStatus.OK)
    }
}
