package vehicool.backend.controlador
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.stereotype.Component
import org.springframework.web.socket.*
import vehicool.backend.DTO.salida.ChatDto
import vehicool.backend.servicio.impl.ChatServiceImpl

@Component
class ControladorChat(
    private val chatService: ChatServiceImpl
) : WebSocketHandler {

    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

    private val sessionsByRepairId = mutableMapOf<String, MutableSet<WebSocketSession>>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val repairId = session.uri?.path?.substringAfterLast("/")
        if (repairId != null) {
            sessionsByRepairId.computeIfAbsent(repairId) { mutableSetOf() }.add(session)
        }
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        val payload = message.payload.toString()
        val repairId = session.uri?.path?.substringAfterLast("/") ?: return

        val chatDto: ChatDto = try {
            objectMapper.readValue(payload, ChatDto::class.java)
        } catch (e: Exception) {
            println("Error al deserializar el mensaje: ${e.message}")
            return
        }

        chatService.guardarMensaje(chatDto)

        sessionsByRepairId[repairId]?.forEach { s ->
            if (s.isOpen) {
                val chatDtoJson = objectMapper.writeValueAsString(chatDto)
                s.sendMessage(TextMessage(chatDtoJson))
            }
        }
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        println("Error de transporte en la conexión WebSocket: ${exception.message}")
        exception.printStackTrace()
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val repairId = session.uri?.path?.substringAfterLast("/")
        sessionsByRepairId[repairId]?.remove(session)
    }

    override fun supportsPartialMessages(): Boolean = false
}
