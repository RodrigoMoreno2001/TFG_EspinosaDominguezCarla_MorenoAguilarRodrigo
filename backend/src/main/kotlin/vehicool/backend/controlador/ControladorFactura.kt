package vehicool.backend.controlador
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import vehicool.backend.DTO.FacturaDTO
import vehicool.backend.repositorio.RepositorioFactura
import vehicool.backend.repositorio.RepositorioVehiculo
import vehicool.backend.servicio.api.FacturaServiceAPI

@RestController
@RequestMapping("/api/factura")
class ControladorFactura(private val repositorioFactura: RepositorioFactura) {

    @Autowired
    lateinit var facturaServiceAPI: FacturaServiceAPI

    @Autowired
    lateinit var vehiculoRepo: RepositorioVehiculo

    @Autowired
    lateinit var facturaRepo: RepositorioFactura

    @GetMapping("/todos")
    fun getAll(): MutableList<FacturaDTO>? {
        return facturaServiceAPI.obtenerTodosDTO()
    }
    @GetMapping("/{id}")
    fun getFactura(@PathVariable id: Long): FacturaDTO? {
        return facturaServiceAPI.obtenerPorIdDTO(id)
    }
/*
    @PostMapping("/")
    fun crear(@RequestBody dto: FacturaInputDTO): ResponseEntity<FacturaDTO?> {

        val creada = facturaServiceAPI.crearFactura(factura)
        return ResponseEntity.ok(creada)
    }*/
}
