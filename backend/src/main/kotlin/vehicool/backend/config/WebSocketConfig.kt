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

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(controladoraChat, "/ws/chat/{roomId}")
            .setAllowedOrigins("*")
    }
}
