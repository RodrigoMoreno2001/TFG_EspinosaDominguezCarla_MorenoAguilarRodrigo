package com.example.vehicool.app.vista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.vehicool.R
import com.example.vehicool.app.utils.SessionManager
import vehicool.backend.DTO.entrada.ReparacionDTO

class FragmentDetallesReparacionesMecanico : Fragment() {

    private var reparacion: ReparacionDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reparacion = arguments?.getParcelable("reparacion")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_detalles_reparaciones_mecanico, container, false)

        val nombre = vista.findViewById<TextView>(R.id.nombre)
        val matricula = vista.findViewById<TextView>(R.id.matricula)
        val modelo = vista.findViewById<TextView>(R.id.modelo)
        val fecha = vista.findViewById<TextView>(R.id.fecha)
        val motivos = vista.findViewById<TextView>(R.id.motivos)
        val abrirChat = vista.findViewById<Button>(R.id.abrirChat)
        val anadirServicio = vista.findViewById<Button>(R.id.anadirServicio)

        // recupera el nombre del usuario persistente

        nombre.text= SessionManager(requireContext()).getNombre()
        matricula.text=reparacion?.vehiculo?.matricula
        modelo.text=reparacion?.vehiculo?.modelo
        fecha.text=reparacion?.fechaEntrada.toString()
        motivos.text=reparacion?.motivos

        abrirChat.setOnClickListener {
            verChat(reparacion?.id!!)
        }
        anadirServicio.setOnClickListener {
            val fragment = HacerFactura()
            val bundle = Bundle().apply {
                putParcelable("reparacion", reparacion)
            }
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        return vista
    }

    private fun verChat(reparacionId: Long){
        val fragment = FragmentChat()

        val bundle = Bundle().apply {
            putLong("reparacionId", reparacionId)
        }
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}