package com.example.vehicool.app.vista

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.vehicool.R
import com.example.vehicool.app.DTO.salida.ReparacionOutputDTO
import com.example.vehicool.app.api.RetrofitClient
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.entrada.ReparacionDTO
import vehicool.backend.DTO.entrada.VehiculoDTO
import java.time.LocalDate

class SolicitarCita : Fragment() {
    private var vehiculo: VehiculoDTO? = null
    private var fecha: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vehiculo = arguments?.getParcelable("vehiculo")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista=inflater.inflate(R.layout.fragment_solicitar_cita, container, false)

        val modelo= vista.findViewById<TextView>(R.id.modelo)
        val matricula= vista.findViewById<TextView>(R.id.matricula)
        val motivos= vista.findViewById<EditText>(R.id.motivos)
        val fechatxt= vista.findViewById<TextView>(R.id.fecha)
        val seleccionarFecha = vista.findViewById<MaterialButton>(R.id.seleccionarFecha)
        val anadirVehiculobtn = vista.findViewById<MaterialButton>(R.id.AnadirVehiculobtn)

        modelo.text=vehiculo?.modelo.toString()
        matricula.text=vehiculo?.matricula.toString()


        val setterFecha = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            fecha = LocalDate.of(year, month + 1, dayOfMonth)
            fechatxt.text = "Fecha: $fecha"
        }

        seleccionarFecha.setOnClickListener {
            val hoy = LocalDate.now()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                setterFecha,
                hoy.year,
                hoy.monthValue - 1,
                hoy.dayOfMonth
            )
            datePickerDialog.show()
        }
        anadirVehiculobtn.setOnClickListener {

            crearReparacion(motivos.text.toString())
        }
        return vista
    }
    private fun crearReparacion(motivos: String) {
        if(fecha==null ){
            Toast.makeText(requireContext(), "Selecciona una fecha válida", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevaReparacion = ReparacionOutputDTO(
            fechaEntrada = fecha!!,
            estado = "En proceso",
            servicios ="",
            motivos = motivos,
            vehiculoId = vehiculo!!.id!!,
        )

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
}