package vehicool.backend.entities


import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val correo: String = "",
    @Column
    val nombre: String = "",
    @Column
    val pass: String = "",

    val telefono: String = ""
)
