package com.example.vehicool.app.vista

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R
import com.example.vehicool.app.DTO.salida.FacturaOutputDTO
import com.example.vehicool.app.DTO.salida.ReparacionOutputDTO
import com.example.vehicool.app.api.RetrofitClient
import com.example.vehicool.app.utils.SessionManager
import com.example.vehicool.app.vista.adaptadores.ServiciosAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.entrada.FacturaDTO
import vehicool.backend.DTO.entrada.ReparacionDTO
import java.time.LocalDate

class HacerFactura : Fragment() {

    private var reparacion: ReparacionDTO? = null
    private lateinit var serviciosAdapter: ServiciosAdapter
    private var listaServicios = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reparacion = arguments?.getParcelable("reparacion")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_hacer_factura, container, false)

        val serviciotxt = vista.findViewById<EditText>(R.id.Serviciotxt)
        val cantidadtxt = vista.findViewById<EditText>(R.id.Cantidadtxt)
        val precioUnidadtxt = vista.findViewById<EditText>(R.id.PrecioUnidadtxt)

        val anadirServiciobtn = vista.findViewById<Button>(R.id.AnadirServiciobtn)

        val completarFactura = vista.findViewById<Button>(R.id.CompletarFactura)

        val recyclerView = vista.findViewById<RecyclerView>(R.id.recyclerView)

        anadirServiciobtn.setOnClickListener {
            anadirServicio(serviciotxt.text.toString(),cantidadtxt.text.toString(),precioUnidadtxt.text.toString())
            serviciotxt.text.clear()
            cantidadtxt.text.clear()
            precioUnidadtxt.text.clear()
        }

        completarFactura.setOnClickListener {
            val reparacion=requireNotNull(reparacion)
            crearFactura(reparacion)
        }

        listaServicios = reparacion?.servicios?.removeSuffix(";")?.split(";")?.filter { it.isNotBlank() && it.count { c -> c == ':' } == 2 }!!.toMutableList()

        serviciosAdapter = ServiciosAdapter(listaServicios,{posicion -> eliminarServicio(posicion)})
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = serviciosAdapter

        return vista
    }

    private fun anadirServicio(servicio: String, cantidad: String,precio: String) {

        val nuevoServicio = "$servicio:$precio:$cantidad;"
        reparacion?.servicios += nuevoServicio

        val reparacionModificada = ReparacionOutputDTO(
            id = reparacion?.id!!,
            fechaEntrada = reparacion?.fechaEntrada!!,
            estado = reparacion?.estado!!,
            servicios = reparacion?.servicios!!,
            motivos = reparacion?.motivos!!,
            vehiculoId = reparacion?.vehiculo?.id!!,
            facturaId = reparacion?.factura
        )
        pushearServicio(reparacionModificada)
        listaServicios.add(nuevoServicio)
        serviciosAdapter.notifyItemInserted(listaServicios.size - 1)
    }

    private fun eliminarServicio(posicion: Int) {
        if (posicion < 0 || posicion >= listaServicios.size) return

        listaServicios.removeAt(posicion)
        serviciosAdapter.notifyItemRemoved(posicion)

        reparacion?.servicios = listaServicios.joinToString(";") + ";"
        val reparacionModificada = ReparacionOutputDTO(
            id = reparacion?.id!!,
            fechaEntrada = reparacion?.fechaEntrada!!,
            estado = reparacion?.estado!!,
            servicios = reparacion?.servicios!!,
            motivos = reparacion?.motivos!!,
            vehiculoId = reparacion?.vehiculo?.id!!,
            facturaId = reparacion?.factura
        )
        pushearServicio(reparacionModificada)
    }

    private fun pushearServicio(nuevaReparacion: ReparacionOutputDTO){
        RetrofitClient.reparacionService.crearReparacion(nuevaReparacion).enqueue(object : Callback<ReparacionDTO> {
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
    }

    private fun crearFactura(reparacion: ReparacionDTO) {

        val total = reparacion.servicios?.split(";")?.mapNotNull { servicio ->
            val partes = servicio.split(":")
            if (partes.size == 3) {
                val precio = partes[1].toDoubleOrNull()
                val cantidad = partes[2].toIntOrNull()
                if (precio != null && cantidad != null) precio * cantidad else null
            } else {
                null
            }
        }?.sum() ?: 0.0

        val nuevaFactura = FacturaOutputDTO(
            fecha = LocalDate.now().toString(),
            importeTotal = total,
            usuarioId = SessionManager(requireContext()).getId(),
            reparacionId = reparacion.id!!
        )
        RetrofitClient.facturaService.crearFactura(nuevaFactura).enqueue(object : Callback<FacturaDTO> {
            override fun onResponse(call: Call<FacturaDTO>, response: Response<FacturaDTO>) {
                if (response.isSuccessful && response.body() != null) {
                    val idFactura = response.body()?.id
                    val reparacionCompletada = ReparacionOutputDTO(
                        id = reparacion.id,
                        fechaEntrada = reparacion.fechaEntrada,
                        estado = "Pago pendiente",
                        servicios = reparacion.servicios,
                        motivos = reparacion.motivos,
                        vehiculoId = reparacion.vehiculo.id!!,
                        facturaId = idFactura
                    )
                    pushearServicio(reparacionCompletada)
                } else {
                    Toast.makeText(requireContext(), "Hubo un error en la solicitud", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FacturaDTO>, t: Throwable) {
                Log.e("LOGIN", "Error de conexión", t)
                Toast.makeText(requireContext(), "Error al conectar a la API", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

