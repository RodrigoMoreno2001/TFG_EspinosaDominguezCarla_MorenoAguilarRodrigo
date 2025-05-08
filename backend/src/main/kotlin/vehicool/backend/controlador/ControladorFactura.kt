package vehicool.backend.controlador
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vehicool.backend.DTO.salida.FacturaDTO
import vehicool.backend.DTO.entrada.FacturaInputDTO
import vehicool.backend.repositorio.RepositorioFactura
import vehicool.backend.servicio.api.FacturaServiceAPI

@RestController
@RequestMapping("/api/factura")
class ControladorFactura(private val repositorioFactura: RepositorioFactura) {

    @Autowired
    lateinit var facturaServiceAPI: FacturaServiceAPI


    @GetMapping("/todos")
    fun getAll(): MutableList<FacturaDTO>? {
        return facturaServiceAPI.obtenerTodosDTO()
    }

    @GetMapping("/{id}")
    fun getFactura(@PathVariable id: Long): FacturaDTO? {
        return facturaServiceAPI.obtenerPorIdDTO(id)
    }
    @GetMapping("/usuario/{id}")
    fun getFacturaPorUsuario(@PathVariable id: Long):  List<FacturaDTO> {
        return facturaServiceAPI.obtenerPorIdUsuarioDTO(id)
    }

    @PostMapping("/")
    fun crear(@RequestBody dto: FacturaInputDTO): ResponseEntity<FacturaDTO?> {
        val creada = facturaServiceAPI.crearFactura(dto)
        return ResponseEntity.ok(creada)
    }
}
