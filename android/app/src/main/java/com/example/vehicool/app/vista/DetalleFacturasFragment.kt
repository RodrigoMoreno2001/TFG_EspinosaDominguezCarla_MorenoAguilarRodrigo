package com.example.vehicool.app.vista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R
import com.example.vehicool.app.api.RetrofitClient
import com.example.vehicool.app.utils.SessionManager
import com.example.vehicool.app.vista.adaptadores.ServicioFacturaAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.entrada.FacturaDTO
import vehicool.backend.DTO.entrada.ReparacionDTO

class DetalleFacturasFragment : Fragment() {

    private lateinit var servicioRecycler: RecyclerView
    private lateinit var servicioAdapter: ServicioFacturaAdapter
    private var reparacion: ReparacionDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reparacion = arguments?.getParcelable("reparacion")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_detalle_facturas, container, false)

        servicioRecycler = vista.findViewById(R.id.servicioRecycler)

        servicioAdapter = ServicioFacturaAdapter(reparacion?.servicios!!.split(";").filter { it.isNotBlank() })
        servicioRecycler.layoutManager = LinearLayoutManager(requireContext())
        servicioRecycler.adapter = servicioAdapter
        val fechaFactura = vista.findViewById<TextView>(R.id.fechaFactura)
        val nombre = vista.findViewById<TextView>(R.id.nombre)
        val precioTotal = vista.findViewById<TextView>(R.id.precioTotal)
        nombre.text= SessionManager(requireContext()).getNombre()
        datosFactura(precioTotal, fechaFactura)
        return vista
    }

    private fun datosFactura(precioTotal: TextView, fechaFactura: TextView) {
        RetrofitClient.facturaService.getFactura(reparacion?.factura!!)
            .enqueue(object : Callback<FacturaDTO> {
                override fun onResponse(call: Call<FacturaDTO>, response: Response<FacturaDTO>) {
                    if (response.isSuccessful && response.body() != null) {
                        val factura = response.body()!!
                        precioTotal.text = "${factura.importeTotal}€"
                        fechaFactura.text = "${factura.fecha}"
                    }
                }
                override fun onFailure(call: Call<FacturaDTO>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show()
                }
            })
    }

}