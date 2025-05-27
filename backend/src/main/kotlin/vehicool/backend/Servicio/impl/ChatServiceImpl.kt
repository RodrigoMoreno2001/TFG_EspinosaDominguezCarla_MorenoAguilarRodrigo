package vehicool.backend.servicio.impl

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import vehicool.backend.DTO.salida.ChatDto
import vehicool.backend.entities.MensajeChat
import vehicool.backend.genericas.GenericServiceImpl
import vehicool.backend.repositorio.RepositorioChat
import vehicool.backend.repositorio.RepositorioReparacion
import vehicool.backend.repositorio.RepositorioUsuario
import vehicool.backend.servicio.api.ChatServiceAPI

/**
 * Servicio del chat. Siempre trabajamos con DTOs para enviar/recibir datos,
 * nunca con entidades directamente en los controladores.
 */

@Service
class ChatServiceImpl(
    private val repositorioChat: RepositorioChat,
    private val repositorioUsuario: RepositorioUsuario,
    private val repositorioReparacion: RepositorioReparacion
) : GenericServiceImpl<MensajeChat, Long>(), ChatServiceAPI {

    override val dao: CrudRepository<MensajeChat, Long>
        get() {
            return repositorioChat
        }

    override fun obtenerPorIdReparacion(id: Long): List<ChatDto> {
        val mensajes = repositorioChat.findAllByReparacionIdOrderByHoraAsc(id)
        return mensajes.map {
            ChatDto(
                emisorId = it.emisor.id,
                receptorId = it.receptor.id,
                reparacionId = it.reparacion.id!!,
                mensaje = it.mensaje,
            )
        }
    }

    override fun guardarMensaje(chatDto: ChatDto): MensajeChat {
        val emisor = repositorioUsuario.findById(chatDto.emisorId)
            .orElseThrow { IllegalArgumentException("Emisor no encontrado") }

        val receptor = repositorioUsuario.findById(chatDto.receptorId)
            .orElseThrow { IllegalArgumentException("Receptor no encontrado") }

        println(chatDto.reparacionId)
        val reparacion = repositorioReparacion.findById(chatDto.reparacionId)
            .orElseThrow { IllegalArgumentException("Reparación no encontrada") }

        val mensaje = MensajeChat(
            mensaje = chatDto.mensaje,
            emisor = emisor,
            receptor = receptor,
            reparacion = reparacion
        )

        return repositorioChat.save(mensaje)
    }
}
