package com.example.vehicool.app.vista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R
import com.example.vehicool.app.api.RetrofitClient
import com.example.vehicool.app.utils.SessionManager
import com.example.vehicool.app.vista.adaptadores.ReparacionesAdapter
import com.skydoves.expandablelayout.ExpandableLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.entrada.ReparacionDTO
import vehicool.backend.DTO.entrada.VehiculoDTO

class Reparaciones : Fragment() {

    private val reparacionesActivas = mutableListOf<ReparacionDTO>()
    private val reparacionesFinalizadas = mutableListOf<ReparacionDTO>()
    private val adapterActivas = ReparacionesAdapter(reparacionesActivas) { reparacion ->
        verDetalles(reparacion)
    }
    private val adapterFinalizadas = ReparacionesAdapter(reparacionesFinalizadas) { reparacion ->
        verDetalles(reparacion)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_reparaciones, container, false)

        val reparacionesActivas = vista.findViewById<ExpandableLayout>(R.id.reparacionesActivas)
        val reparacionesFinalizadas = vista.findViewById<ExpandableLayout>(R.id.reparacionesFinalizadas)

        val recyclerActivas = reparacionesActivas.secondLayout.findViewById<RecyclerView>(R.id.rv_reparaciones)
        val recyclerFinalizadas = reparacionesFinalizadas.secondLayout.findViewById<RecyclerView>(R.id.rv_reparaciones)

        recyclerActivas.layoutManager = LinearLayoutManager(requireContext())
        recyclerActivas.adapter = adapterActivas
        recyclerFinalizadas.layoutManager = LinearLayoutManager(requireContext())
        recyclerFinalizadas.adapter = adapterFinalizadas

        reparacionesActivas.setOnClickListener {
            if(reparacionesActivas.isExpanded) reparacionesActivas.collapse()
            else reparacionesActivas.expand()
        }
        reparacionesFinalizadas.setOnClickListener {
            if(reparacionesFinalizadas.isExpanded) reparacionesFinalizadas.collapse()
            else reparacionesFinalizadas.expand()
        }

        obtenerReparaciones()

        return vista
    }
    private fun verDetalles(reparacionDTO: ReparacionDTO){
        val fragment = DetallesReparacion()

        val bundle = Bundle().apply {
            putParcelable("reparacion", reparacionDTO)
        }
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun obtenerReparaciones(){
        val id = SessionManager(requireContext()).getId()

        RetrofitClient.reparacionService.getPorUsuario(id)
            .enqueue(object : Callback<List<ReparacionDTO>> {
                override fun onResponse(call: Call<List<ReparacionDTO>>, response: Response<List<ReparacionDTO>>) {
                    if (response.isSuccessful) {
                        reparacionesActivas.clear()
                        reparacionesFinalizadas.clear()
                        response.body()?.let {
                            reparacionesActivas.addAll(it.filter { it.estado != "Cancelado" && it.estado != "Completado" })
                            reparacionesFinalizadas.addAll(it.filter { it.estado == "Cancelado" || it.estado == "Completado" })
                        }
                        adapterActivas.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Error al acceder a los vehiculos", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ReparacionDTO>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show()
                }
            })
    }

}