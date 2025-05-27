package vehicool.backend.config

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import vehicool.backend.entities.Usuario
import vehicool.backend.repositorio.RepositorioUsuario

@Component
class CreadorMecanico(
    private val usuarioRepository: RepositorioUsuario
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val correoMecanico = "mecanico@vehicool.com"
        if (!usuarioRepository.existsByCorreo(correoMecanico)) {
            val usuario = Usuario(
                nombre = "Mecánico",
                correo = correoMecanico,
                contrasena = "1234",
                privilegios = "Mecanico"
            )
            usuarioRepository.save(usuario)
        }
    }
}
