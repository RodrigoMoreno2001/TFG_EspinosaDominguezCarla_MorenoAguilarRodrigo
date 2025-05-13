package vehicool.backend.controlador

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vehicool.backend.DTO.salida.ChatDto
import vehicool.backend.servicio.impl.ChatServiceImpl

@RestController
@RequestMapping("/api/chat")
class ControladorRestChat(
    private val chatService: ChatServiceImpl
) {

    @GetMapping("/{reparacionId}")
    fun obtenerHistorial(@PathVariable reparacionId: Long): ResponseEntity<List<ChatDto>> {
        val historial = chatService.obtenerPorIdReparacion(reparacionId)
        return ResponseEntity.ok(historial)
    }
}
