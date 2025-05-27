package vehicool.backend.controlador
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vehicool.backend.DTO.salida.ReparacionDTO
import vehicool.backend.DTO.entrada.ReparacionInputDTO
import vehicool.backend.DTO.salida.FacturaDTO
import vehicool.backend.entities.Reparacion
import vehicool.backend.repositorio.RepositorioFactura
import vehicool.backend.repositorio.RepositorioReparacion
import vehicool.backend.repositorio.RepositorioVehiculo
import vehicool.backend.servicio.api.ReparacionServiceAPI

// Esta clase está definida como controlador Rest con url ("/api/reparacion")

@RestController
@RequestMapping("/api/reparacion")
class ControladorReparacion(private val repositorioReparacion: RepositorioReparacion) {

    // inyectar servicio

    @Autowired
    lateinit var reparacionServiceAPI: ReparacionServiceAPI

    @GetMapping("/todos")
    fun getAll(): MutableList<ReparacionDTO>? {
        return reparacionServiceAPI.obtenerTodosDTO()
    }
    @GetMapping("/{id}")
    fun getReparacion(@PathVariable id: Long): ReparacionDTO? {
        return reparacionServiceAPI.obtenerPorIdDTO(id)
    }

    @GetMapping("/usuario/{id}")
    fun getPorUsuario(@PathVariable id: Long):  List<ReparacionDTO> {
        return reparacionServiceAPI.obtenerPorIdUsuario(id)
    }

    @PostMapping("/")
    fun crearReparacion(@RequestBody dto: ReparacionInputDTO): ResponseEntity<ReparacionDTO?> {
        val creada = reparacionServiceAPI.crearReparacion(dto)
        return ResponseEntity.ok(creada)
    }
}
