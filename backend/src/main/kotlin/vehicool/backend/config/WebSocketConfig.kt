package vehicool.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import vehicool.backend.controlador.ControladorChat

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val controladoraChat: ControladorChat
) : WebSocketConfigurer {
    
    // se registra el handler en la ruta "/ws/chat/{roomId}", roomId es el identificador del chat
    
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(controladoraChat, "/ws/chat/{roomId}")
            .setAllowedOrigins("*") // esto permite conexiones de cualquier origen (es peligroso)
    }
}
