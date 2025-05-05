package vehicool.backend.entities
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val mensaje: String = "",
    val hora: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    val emisor: Usuario,

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    val receptor: Usuario
)
