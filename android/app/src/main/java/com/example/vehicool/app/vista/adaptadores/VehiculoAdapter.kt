package com.example.vehicool.app.vista.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R
import vehicool.backend.DTO.entrada.VehiculoDTO

/**
 * Adaptador para mostrar una lista de vehículos en un RecyclerView.
 *
 * @param listaVehiculos Lista de vehículos que se mostrarán en la interfaz.
 * @param onItemClick Función que se ejecuta al pulsar sobre un vehículo.
 */

class VehiculoAdapter(
    private val listaVehiculos: List<VehiculoDTO>,
    private val onItemClick: (VehiculoDTO) -> Unit
    ) : RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder>() {

    class VehiculoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modeloText: TextView = itemView.findViewById(R.id.modelo)
        val matriculaText: TextView = itemView.findViewById(R.id.matricula)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_vehiculo, parent, false)
        return VehiculoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: VehiculoViewHolder, position: Int) {
        val vehiculo = listaVehiculos[position]
        holder.modeloText.text = vehiculo.modelo
        holder.matriculaText.text = vehiculo.matricula

        holder.itemView.setOnClickListener {
            onItemClick(vehiculo)
        }
    }

    override fun getItemCount(): Int = listaVehiculos.size
}
