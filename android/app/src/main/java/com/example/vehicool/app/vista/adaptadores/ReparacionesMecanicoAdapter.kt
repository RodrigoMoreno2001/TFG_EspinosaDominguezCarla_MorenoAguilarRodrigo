package com.example.vehicool.app.vista.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vehicool.backend.DTO.entrada.ReparacionDTO
import com.example.vehicool.R


class ReparacionesMecanicoAdapter(
    private val reparaciones: List<ReparacionDTO>,
    private val onVerMasClick: (ReparacionDTO) -> Unit,
    private val cambiarEstado: (ReparacionDTO) -> Unit
) : RecyclerView.Adapter<ReparacionesMecanicoAdapter.ReparacionViewHolder>() {

    inner class ReparacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val matricula: TextView = itemView.findViewById(R.id.matricula)
        val modelo: TextView = itemView.findViewById(R.id.modelo)
        val fecha: TextView = itemView.findViewById(R.id.fecha)
        val estado: TextView = itemView.findViewById(R.id.estado)
        val boton: Button = itemView.findViewById(R.id.boton)
        val btnEditar: ImageView = itemView.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReparacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_reparacion_mecanico, parent, false)
        return ReparacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReparacionViewHolder, position: Int) {
        val reparacion = reparaciones[position]

        holder.matricula.text = reparacion.vehiculo.matricula
        holder.modelo.text = reparacion.vehiculo.modelo
        holder.fecha.text = reparacion.fechaEntrada.toString()
        holder.estado.text = reparacion.estado

        holder.boton.setOnClickListener {
            onVerMasClick(reparacion)
        }
        holder.btnEditar.setOnClickListener {
            cambiarEstado(reparacion)
        }
    }

    override fun getItemCount(): Int = reparaciones.size
}
