package com.example.vehicool.app.vista.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicool.R

/**
 * Adaptador para mostrar los servicios de una factura en un RecyclerView.
 */

class ServicioFacturaAdapter (
    private val servicios: List<String>
) : RecyclerView.Adapter<ServicioFacturaAdapter.ServicioViewHolder>() {

    class ServicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.tvNombre)
        val cantidadTextView: TextView = itemView.findViewById(R.id.tvUnidades)
        val precioTextView: TextView = itemView.findViewById(R.id.tvPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cv_servicio_factura, parent, false)

        return ServicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {

        // los servicios vienen separados asi "nombre:precio:cantidad"

        val itemsServicio = servicios[position].split(":")
        holder.nombreTextView.text = itemsServicio[0]
        holder.precioTextView.text = "${itemsServicio[1]}€"
        holder.cantidadTextView.text = itemsServicio[2]
    }

    override fun getItemCount(): Int = servicios.size
}