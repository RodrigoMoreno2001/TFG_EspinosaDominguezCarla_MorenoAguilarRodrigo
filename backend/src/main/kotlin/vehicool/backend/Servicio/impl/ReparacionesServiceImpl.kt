package vehicool.backend.servicio.impl

import vehicool.backend.genericas.GenericServiceImpl
import vehicool.backend.servicio.api.ReparacionServiceAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import vehicool.backend.DTO.salida.ReparacionDTO
import vehicool.backend.DTO.entrada.ReparacionInputDTO
import vehicool.backend.entities.Reparacion
import vehicool.backend.mapeadores.reparacionToDTO
import vehicool.backend.repositorio.RepositorioFactura
import vehicool.backend.repositorio.RepositorioReparacion
import vehicool.backend.repositorio.RepositorioVehiculo
/**
 * Servicio de reparaciones. Siempre devolvemos DTOs y recibimos InputDTOs del cliente.
 */
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

    override fun crearReparacion(dto: ReparacionInputDTO): ReparacionDTO? {

        val vehiculo = repositorioVehiculo.findById(dto.vehiculoId).orElseThrow {
            RuntimeException("Vehículo no encontrado")
        }

        val factura = dto.facturaId?.let {
            repositorioFactura.findById(it).orElse(null)
        }

        val reparacion = Reparacion(
            id = dto.id,
            fechaEntrada = dto.fechaEntrada,
            estado = dto.estado,
            servicios = dto.servicios,
            motivos = dto.motivos,
            vehiculo = vehiculo,
            factura = factura
        )

        return dao.save(reparacion).reparacionToDTO()
    }

    override fun obtenerPorIdUsuario(id: Long): List<ReparacionDTO> {
        return repositorioReparacion.findAllByVehiculo_Usuario_Id(id).map { it.reparacionToDTO() }
    }


}