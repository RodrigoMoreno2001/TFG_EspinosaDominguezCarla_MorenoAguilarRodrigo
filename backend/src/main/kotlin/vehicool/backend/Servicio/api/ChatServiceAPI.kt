package vehicool.backend.servicio.api

import GenericServiceAPI
import vehicool.backend.DTO.salida.FacturaDTO
import vehicool.backend.DTO.entrada.FacturaInputDTO
import vehicool.backend.DTO.salida.ChatDto
import vehicool.backend.entities.Factura
import vehicool.backend.entities.MensajeChat

interface ChatServiceAPI : GenericServiceAPI<MensajeChat, Long>{
    fun obtenerPorIdReparacion(id: Long):  List<ChatDto>
    fun guardarMensaje(chatDto: ChatDto): MensajeChat
}
