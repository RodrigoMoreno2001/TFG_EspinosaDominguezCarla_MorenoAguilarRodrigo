package vehicool.backend.controlador
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vehicool.backend.DTO.ReparacionDTO
import vehicool.backend.DTO.ReparacionInputDTO
import vehicool.backend.entities.Reparacion
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
    fun crear(@RequestBody dto: ReparacionInputDTO): ResponseEntity<ReparacionDTO?> {
        val vehiculo = vehiculoRepo.findById(dto.vehiculoId).orElseThrow {
            RuntimeException("Vehículo no encontrado")
        }

        val factura = dto.facturaId?.let {
            facturaRepo.findById(it).orElse(null)
        }

        val reparacion = Reparacion(
            estado = dto.estado,
            servicios = dto.servicios,
            vehiculo = vehiculo,
            factura = factura
        )

        if(dto.id!=null) reparacion.id = dto.id

        val creada = reparacionServiceAPI.crearReparacion(reparacion)
        return ResponseEntity.ok(creada)
    }
}
