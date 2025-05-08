package vehicool.backend.servicio.impl

import vehicool.backend.genericas.GenericServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import vehicool.backend.DTO.salida.FacturaDTO
import vehicool.backend.DTO.entrada.FacturaInputDTO
import vehicool.backend.entities.Factura
import vehicool.backend.mapeadores.facturaToDTO
import vehicool.backend.mapeadores.toFactura
import vehicool.backend.repositorio.RepositorioFactura
import vehicool.backend.repositorio.RepositorioReparacion
import vehicool.backend.repositorio.RepositorioUsuario
import vehicool.backend.servicio.api.FacturaServiceAPI

@Service
class FacturaServiceImpl : GenericServiceImpl<Factura, Long>(), FacturaServiceAPI {

    @Autowired
    lateinit var repositorioFactura: RepositorioFactura

    @Autowired
    lateinit var usuarioRepositorio: RepositorioUsuario

    @Autowired
    lateinit var reparacionRepositorio: RepositorioReparacion

    override val dao: CrudRepository<Factura, Long>
        get() {
            return repositorioFactura
        }

    override fun obtenerTodosDTO(): MutableList<FacturaDTO> {
        return dao.findAll().map { it.facturaToDTO() }.toMutableList()
    }

    override fun obtenerPorIdDTO(id: Long): FacturaDTO? {
        return dao.findById(id).orElse(null)?.facturaToDTO()
    }

    override fun obtenerPorIdUsuarioDTO(id: Long): List<FacturaDTO> {
        return repositorioFactura.findAllByUsuarioId(id).map { it.facturaToDTO() }
    }

    override fun crearFactura(dto: FacturaInputDTO): FacturaDTO? {

        val usuario = usuarioRepositorio.findById(dto.usuarioId).orElseThrow {
            RuntimeException("Usuario no encontrado")
        }
        val reparacion =reparacionRepositorio.findById(dto.reparacionId).orElseThrow {
            RuntimeException("Reparacion no encontrada")
        }

        return dao.save(dto.toFactura(usuario, reparacion)).facturaToDTO()
    }
}
