package vehicool.backend.entities
import jakarta.persistence.*

@Entity
data class Vehiculo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val matricula: String = "",
    val modelo: String = "",
    val anyo: Int = 2001,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario,
)

