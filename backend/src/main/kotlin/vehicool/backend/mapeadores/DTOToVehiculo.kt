package vehicool.backend.mapeadores

import vehicool.backend.DTO.entrada.VehiculoInputDTO
import vehicool.backend.entities.Usuario
import vehicool.backend.entities.Vehiculo

fun VehiculoInputDTO.toVehiculo(usuario: Usuario): Vehiculo {
    return Vehiculo(
        this.id,
        this.matricula,
        this.modelo,
        this.anyo,
        usuario,
    );
}
