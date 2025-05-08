package vehicool.backend.entities
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Reparacion(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @JsonFormat(pattern = "yyyy-MM-dd")
    val fechaEntrada: LocalDate = LocalDate.now(),
    val estado: String = "",
    @Lob
    @Column(columnDefinition = "TEXT")
    val servicios: String = "",

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    var vehiculo: Vehiculo,

    @OneToOne
    @JoinColumn(name = "factura_id")
    var factura: Factura? = null
)
