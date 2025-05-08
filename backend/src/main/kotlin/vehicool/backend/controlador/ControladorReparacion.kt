package vehicool.backend.controlador
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vehicool.backend.DTO.salida.ReparacionDTO
import vehicool.backend.DTO.entrada.ReparacionInputDTO
import vehicool.backend.repositorio.RepositorioFactura
import vehicool.backend.repositorio.RepositorioReparacion
import vehicool.backend.repositorio.RepositorioVehiculo
import vehicool.backend.servicio.api.ReparacionServiceAPI

@RestController
@RequestMapping("/api/reparacion")
class ControladorReparacion(private val repositorioReparacion: RepositorioReparacion) {

    @Autowired
    lateinit var reparacionServiceAPI: ReparacionServiceAPI

    @Autowired
    lateinit var vehiculoRepo: RepositorioVehiculo

    @Autowired
    lateinit var facturaRepo: RepositorioFactura

    @GetMapping("/todos")
    fun getAll(): MutableList<ReparacionDTO>? {
        return reparacionServiceAPI.obtenerTodosDTO()
    }
    @GetMapping("/{id}")
    fun getReparacion(@PathVariable id: Long): ReparacionDTO? {
        return reparacionServiceAPI.obtenerPorIdDTO(id)
    }

    @PostMapping("/")
    fun crearReparacion(@RequestBody dto: ReparacionInputDTO): ResponseEntity<ReparacionDTO?> {

        val creada = reparacionServiceAPI.crearReparacion(dto)
        return ResponseEntity.ok(creada)
    }
}
