package com.example.vehicool.app.vista

import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R
import com.example.vehicool.app.DTO.salida.ReparacionOutputDTO
import com.example.vehicool.app.api.RetrofitClient
import com.example.vehicool.app.utils.SessionManager
import com.example.vehicool.app.vista.adaptadores.ReparacionesMecanicoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.entrada.ReparacionDTO


class MecanicoInicio : Fragment() {
    private val reparacionesActivas = mutableListOf<ReparacionDTO>()
    private val adapter = ReparacionesMecanicoAdapter(
        reparacionesActivas,
        onVerMasClick = { reparacion ->
            verDetalles(reparacion)
        },
        cambiarEstado = { reparacion ->
            seleccionarEstado(reparacion)
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_mecanico_inicio, container, false)

        vista.findViewById<TextView>(R.id.nombreCliente).text = SessionManager(requireContext()).getNombre()

        val reparacionesRV = vista.findViewById<RecyclerView>(R.id.reparacionesRV)
        reparacionesRV.layoutManager= LinearLayoutManager(requireContext())
        reparacionesRV.adapter = adapter
        obtenerReparaciones()
        return vista
    }

    private fun verDetalles(reparacionDTO: ReparacionDTO){
        val fragment = FragmentDetallesReparacionesMecanico()

        val bundle = Bundle().apply {
            putParcelable("reparacion", reparacionDTO)
        }
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun seleccionarEstado(reparacion: ReparacionDTO) {
        val opciones = arrayOf("En proceso", "Pago pendiente", "Cancelado", "Completado")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Estado de la reparacion:")
            .setItems(opciones) { _, which ->
                val seleccion = opciones[which]
                reparacion.estado=seleccion
                actualizarReparacion(reparacion)
            }
            .create()
            .show()
    }

    private fun obtenerReparaciones(){

        RetrofitClient.reparacionService.getReparaciones()
            .enqueue(object : Callback<List<ReparacionDTO>> {
                override fun onResponse(call: Call<List<ReparacionDTO>>, response: Response<List<ReparacionDTO>>) {
                    if (response.isSuccessful) {
                        reparacionesActivas.clear()
                        response.body()?.let {
                            reparacionesActivas.addAll(it.filter { it.estado != "Cancelado" && it.estado != "Completado" })
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Error al acceder", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ReparacionDTO>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun actualizarReparacion(reparacion: ReparacionDTO){
        val reparacionModificada = ReparacionOutputDTO(
            id = reparacion.id,
            fechaEntrada = reparacion.fechaEntrada,
            estado = reparacion.estado,
            servicios = reparacion.servicios,
            motivos = reparacion.motivos,
            vehiculoId = reparacion.vehiculo.id!!,
            facturaId = reparacion.factura
        )

        RetrofitClient.reparacionService.crearReparacion(reparacionModificada).enqueue(object : Callback<ReparacionDTO> {
            override fun onResponse(call: Call<ReparacionDTO>, response: Response<ReparacionDTO>) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(requireContext(), "Solicitud procesada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Hubo un error en la solicitud", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ReparacionDTO>, t: Throwable) {
                Log.e("LOGIN", "Error de conexión", t)
                Toast.makeText(requireContext(), "Error al conectar a la API", Toast.LENGTH_SHORT).show()
            }
        })
        val index = reparacionesActivas.indexOfFirst { it.id == reparacion.id }
        if (index != -1) {
            reparacionesActivas[index] = reparacion
            adapter.notifyItemChanged(index)
        }
    }
}