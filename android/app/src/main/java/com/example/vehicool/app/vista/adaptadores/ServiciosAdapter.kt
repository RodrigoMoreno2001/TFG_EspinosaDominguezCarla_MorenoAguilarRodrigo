package com.example.vehicool.app.vista.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R

/**
 * Adaptador para mostrar una lista de servicios añadidos por el mecánico.
 *
 * @param servicios Lista de Strings que representan los servicios con formato "nombre:precio:cantidad".
 * @param onBorrar Función de borrar, pasando la posición del elemento.
 */

class ServiciosAdapter(
    private val servicios: List<String>,
    private val onBorrar: (Int) -> Unit
) : RecyclerView.Adapter<ServiciosAdapter.ServicioViewHolder>() {

    inner class ServicioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.nombreServicio)
        val precioTextView: TextView = view.findViewById(R.id.precioServicio)
        val cantidadTextView: TextView = view.findViewById(R.id.cantidadServicio)
        val borrarServicio: TextView = view.findViewById(R.id.borrarServicio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_servicio, parent, false)
        return ServicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {
        val itemsServicio = servicios[position].split(":")
        holder.nombreTextView.text = itemsServicio[0]
        holder.precioTextView.text = "${itemsServicio[1]}€"
        holder.cantidadTextView.text = "x ${itemsServicio[2]}"
        holder.borrarServicio.setOnClickListener {
            onBorrar(position)
        }
    }

    override fun getItemCount(): Int = servicios.size
}
