package vehicool.backend.entities
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class MensajeChat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val mensaje: String = "",
    val hora: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    val emisor: Usuario,

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    val receptor: Usuario,

    @ManyToOne
    @JoinColumn(name = "reparacion_id")
    val reparacion: Reparacion
)
