package com.example.vehicool.app.vista

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R
import com.example.vehicool.app.DTO.salida.ReparacionOutputDTO
import com.example.vehicool.app.api.RetrofitClient
import com.example.vehicool.app.vista.adaptadores.ServicioFacturaAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.entrada.FacturaDTO
import vehicool.backend.DTO.entrada.ReparacionDTO

class ConfirmarPago : Fragment() {

    private var reparacion: ReparacionDTO? = null
    private lateinit var servicioAdapter: ServicioFacturaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reparacion = arguments?.getParcelable("reparacion")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_confirmar_pago, container, false)

        val precio = vista.findViewById<TextView>(R.id.precio)
        val pagarBtn = vista.findViewById<TextView>(R.id.pagar)
        val recyclerServicios = vista.findViewById<RecyclerView>(R.id.recyclerServicios)
        servicioAdapter = ServicioFacturaAdapter(reparacion?.servicios!!.split(";").filter { it.isNotBlank() })

        recyclerServicios.layoutManager = LinearLayoutManager(requireContext())
        recyclerServicios.adapter = servicioAdapter

        obtenerPrecio(precio)

        pagarBtn.setOnClickListener {

            val reparacion = requireNotNull(reparacion)

            val reparacionCompletada = ReparacionOutputDTO(
                id = reparacion.id,
                fechaEntrada = reparacion.fechaEntrada,
                estado = "Completado",
                servicios = reparacion.servicios,
                motivos = reparacion.motivos,
                vehiculoId = reparacion.vehiculo.id!!,
                facturaId = reparacion.factura
            )
            RetrofitClient.reparacionService.crearReparacion(reparacionCompletada).enqueue(object : Callback<ReparacionDTO> {
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
        return vista
    }

    private fun obtenerPrecio(precio: TextView) {
        RetrofitClient.facturaService.getFactura(reparacion?.factura!!)
            .enqueue(object : Callback<FacturaDTO> {
                override fun onResponse(call: Call<FacturaDTO>, response: Response<FacturaDTO>) {
                    if (response.isSuccessful && response.body() != null) {
                        val factura = response.body()!!
                        precio.text = "${factura.importeTotal}€"
                    }
                }
                override fun onFailure(call: Call<FacturaDTO>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show()
                }
            })
    }
}