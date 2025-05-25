package vehicool.backend.DTO.entrada

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehiculoDTO(
    val id: Long? = null,
    val matricula: String = "",
    val modelo: String = "",
    val anyo: Int = 2001
) : Parcelable

