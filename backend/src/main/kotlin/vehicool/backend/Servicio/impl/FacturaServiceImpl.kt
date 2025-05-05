package vehicool.backend.servicio.impl

import vehicool.backend.genericas.GenericServiceImpl
import vehicool.backend.servicio.api.ReparacionServiceAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import vehicool.backend.DTO.FacturaDTO
import vehicool.backend.DTO.ReparacionDTO
import vehicool.backend.entities.Factura
import vehicool.backend.entities.Reparacion
import vehicool.backend.mapeadores.facturaToDTO
import vehicool.backend.mapeadores.reparacionToDTO
import vehicool.backend.repositorio.RepositorioFactura
import vehicool.backend.repositorio.RepositorioReparacion
import vehicool.backend.repositorio.RepositorioVehiculo
import vehicool.backend.servicio.api.FacturaServiceAPI

@Service
class FacturaServiceImpl : GenericServiceImpl<Factura, Long>(), FacturaServiceAPI {

    @Autowired
    lateinit var repositorioFactura: RepositorioFactura

    @Autowired
    lateinit var reparacionRepository: RepositorioReparacion

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

    override fun crearFactura(factura: Factura): FacturaDTO? {
        val reparacionId = factura.reparacion?.id
        val reparacion = reparacionId?.let {
            reparacionRepository.findById(it).orElseThrow {
                RuntimeException("Reparación no encontrada con id $reparacionId")
            }
        }

        factura.reparacion = reparacion

        return dao.save(factura).facturaToDTO()
    }
}
