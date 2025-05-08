package vehicool.backend.DTO

import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import vehicool.backend.entities.Factura
import vehicool.backend.entities.Vehiculo
import java.time.LocalDate

data class ReparacionDTO(
    val fechaEntrada: LocalDate = LocalDate.now(),
    val estado: String = "",
    val servicios: String = "",
    val vehiculo: Long,
    val factura: Long?
)
