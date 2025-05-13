package vehicool.backend.entities
import jakarta.persistence.*

@Entity
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    val nombre: String = "",
    @Column(unique = true)
    val correo: String = "",
    val contrasena: String = "",
    val privilegios: String = "",

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL])
    val vehiculos: List<Vehiculo> = emptyList(),

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL])
    val facturas: List<Factura> = emptyList(),

    @OneToMany(mappedBy = "emisor", cascade = [CascadeType.ALL])
    val chatsEnviados: List<MensajeChat> = emptyList(),

    @OneToMany(mappedBy = "receptor", cascade = [CascadeType.ALL])
    val chatsRecibidos: List<MensajeChat> = emptyList()
)
