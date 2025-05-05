package vehicool.backend.servicio.impl

import vehicool.backend.genericas.GenericServiceImpl
import vehicool.backend.servicio.api.ReparacionServiceAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import vehicool.backend.DTO.ReparacionDTO
import vehicool.backend.entities.Reparacion
import vehicool.backend.mapeadores.reparacionToDTO
import vehicool.backend.repositorio.RepositorioFactura
import vehicool.backend.repositorio.RepositorioReparacion
import vehicool.backend.repositorio.RepositorioVehiculo

@Service
class ReparacionesServiceImpl : GenericServiceImpl<Reparacion, Long>(), ReparacionServiceAPI {
    @Autowired
    lateinit var repositorioReparacion: RepositorioReparacion
    @Autowired
    lateinit var repositorioVehiculo: RepositorioVehiculo
    @Autowired
    lateinit var repositorioFactura: RepositorioFactura

    override val dao: CrudRepository<Reparacion, Long>
        get() {
            return repositorioReparacion
        }
    override fun obtenerTodosDTO(): MutableList<ReparacionDTO> {
        return dao.findAll().map { it.reparacionToDTO() }.toMutableList()
    }

    override fun obtenerPorIdDTO(id: Long): ReparacionDTO? {
        return dao.findById(id).orElse(null)?.reparacionToDTO()
    }

    override fun crearReparacion(reparacion: Reparacion): ReparacionDTO? {

        val vehiculoId = reparacion.vehiculo.id

        val facturaId = reparacion.factura?.id

        val vehiculo = repositorioVehiculo.findById(vehiculoId)
            .orElseThrow { RuntimeException("Vehículo no encontrado con id $vehiculoId") }

        val factura = facturaId?.let {
            repositorioFactura.findById(it)
                .orElseThrow { RuntimeException("Factura no encontrada con id $facturaId") }
        }

        reparacion.vehiculo = vehiculo

        if(facturaId!=null){
            reparacion.factura = factura
        }

        return dao.save(reparacion).reparacionToDTO()
    }

}