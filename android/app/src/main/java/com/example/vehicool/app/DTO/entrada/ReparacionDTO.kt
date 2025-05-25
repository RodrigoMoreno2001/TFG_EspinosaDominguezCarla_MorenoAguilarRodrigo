package vehicool.backend.DTO.entrada

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class ReparacionDTO(
    val id: Long? = null,
    val fechaEntrada: LocalDate = LocalDate.now(),
    val estado: String = "",
    var servicios: String? = null,
    val motivos: String,
    val vehiculo: VehiculoDTO,
    val factura: Long?
): Parcelable
