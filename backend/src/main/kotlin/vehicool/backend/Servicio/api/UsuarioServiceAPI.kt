package vehicool.backend.servicio.api

import GenericServiceAPI
import vehicool.backend.DTO.salida.UsuarioDTO
import vehicool.backend.entities.Usuario

interface UsuarioServiceAPI : GenericServiceAPI<Usuario,Long>{
    fun autenticarUsuario(correo: String, contrasena: String): UsuarioDTO?
    fun obtenerTodosDTO(): MutableList<UsuarioDTO>?
    fun obtenerPorIdDTO(id: Long): UsuarioDTO?
    fun crearUsuario(usuario: Usuario): UsuarioDTO?
}
