package vehicool.backend.entities
import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Factura(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    val fecha: LocalDate = LocalDate.now(),
    val importeTotal: Double = 0.0,

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario,

    @OneToOne(mappedBy = "factura", cascade = [CascadeType.ALL])
    var reparacion: Reparacion? = null
)
