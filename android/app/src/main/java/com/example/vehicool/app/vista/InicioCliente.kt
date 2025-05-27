package com.example.vehicool.app.vista

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R
import com.example.vehicool.app.api.RetrofitClient
import com.example.vehicool.app.utils.SessionManager
import com.example.vehicool.app.vista.adaptadores.VehiculoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.entrada.VehiculoDTO

class InicioCliente : Fragment() {

    private val vehiculos = mutableListOf<VehiculoDTO>()
    private val adapter = VehiculoAdapter(vehiculos){ vehiculo ->
        // cada vehiculo tendrá un listener y abrirá el fragment "SolicitarCita()"
        val fragment = SolicitarCita()
        val bundle = Bundle().apply {
            putParcelable("vehiculo", vehiculo)
        }
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vista=inflater.inflate(R.layout.fragment_inicio_cliente, container, false)
        val nombre = SessionManager(requireContext()).getNombre()
        val nombreText = vista.findViewById<TextView>(R.id.nombreCliente)
        val btnVehiculos = vista.findViewById<ImageButton>(R.id.btnVehiculos)
        val vehiculosRV = vista.findViewById<RecyclerView>(R.id.vehiculosRV)
        val misReparaciones = vista.findViewById<LinearLayout>(R.id.mis_reparaciones)

        vehiculosRV.layoutManager = LinearLayoutManager(context)

        vehiculosRV.adapter = adapter

        nombreText.text = "Hola, $nombre"

        obtenerVehiculos()

        btnVehiculos.setOnClickListener {
            cambiarFragment(AnadirVehiculo())
        }

        misReparaciones.setOnClickListener {
            cambiarFragment(Reparaciones())
        }

        return vista
    }
    private fun cambiarFragment(fragment : Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun obtenerVehiculos(){

        val id = SessionManager(requireContext()).getId()

        RetrofitClient.vehiculoService.getVehiculoPorIdUsuario(id)
            .enqueue(object : Callback<List<VehiculoDTO>> {
                override fun onResponse(call: Call<List<VehiculoDTO>>, response: Response<List<VehiculoDTO>>) {
                    if (response.isSuccessful) {
                        vehiculos.clear()
                        response.body()?.let {
                            vehiculos.addAll(it)
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Error al acceder a los vehiculos", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<VehiculoDTO>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show()
                }
            })
    }
}