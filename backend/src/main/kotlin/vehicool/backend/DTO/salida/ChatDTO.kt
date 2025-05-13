package vehicool.backend.DTO.salida

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ChatDto @JsonCreator constructor(
    @JsonProperty("emisorId") val emisorId: Long,
    @JsonProperty("receptorId") val receptorId: Long,
    @JsonProperty("reparacionId") val reparacionId: Long,
    @JsonProperty("mensaje") val mensaje: String,
)
