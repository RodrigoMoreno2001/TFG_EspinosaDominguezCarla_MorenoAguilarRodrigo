package vehicool.backend.controlador
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vehicool.backend.DTO.salida.VehiculoDTO
import vehicool.backend.entities.Vehiculo
import vehicool.backend.repositorio.RepositorioVehiculo
import vehicool.backend.servicio.api.VehiculoServiceAPI

@RestController
@RequestMapping("/api/vehiculos")
class ControladorVehiculo(private val repositorioVehiculo: RepositorioVehiculo) {

    @Autowired
    lateinit var vehiculoServiceAPI: VehiculoServiceAPI

    @GetMapping("/todos")
    fun getAll(): MutableList<VehiculoDTO>? {
        return vehiculoServiceAPI.obtenerTodosDTO()
    }
    @GetMapping("/{id}")
    fun getVehiculo(@PathVariable id: Long): VehiculoDTO? {
        return vehiculoServiceAPI.obtenerPorIdDTO(id)
    }

    @PostMapping("/")
    fun crearVehiculo(@RequestBody vehiculo: Vehiculo): ResponseEntity<VehiculoDTO> {
        var dto = vehiculoServiceAPI.crearCoche(vehiculo)
        return ResponseEntity<VehiculoDTO>(dto, HttpStatus.OK)
    }

    @PostMapping("/eliminar/{id}")
    fun eliminarVehiculo(@PathVariable id: Long): ResponseEntity<Void> {
        vehiculoServiceAPI.eliminarCoche(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/usuarios/{id}")
    fun getVehiculoPorUsuario(@PathVariable id: Long):  List<VehiculoDTO> {
        return vehiculoServiceAPI.obtenerPorIdUsuarioDTO(id)
    }
}
