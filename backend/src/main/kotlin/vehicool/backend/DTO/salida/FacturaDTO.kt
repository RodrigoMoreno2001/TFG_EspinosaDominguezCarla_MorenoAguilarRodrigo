package vehicool.backend.DTO

import jakarta.persistence.*
import vehicool.backend.entities.Reparacion
import vehicool.backend.entities.Usuario
import java.time.LocalDate

data class FacturaDTO(
    val id: Long? = 0,
    val fecha: LocalDate = LocalDate.now(),
    val servicios: String,
    val importeTotal: Double = 0.0,
    val usuario: Long,
    val reparacion: Long? = null
)
