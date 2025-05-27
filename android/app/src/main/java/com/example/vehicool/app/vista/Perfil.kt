package com.example.vehicool.app.vista

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.vehicool.R
import com.example.vehicool.app.utils.SessionManager


class Perfil : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_perfil, container, false)

        val correo = vista.findViewById<TextView>(R.id.correoCliente)
        val nombre = vista.findViewById<TextView>(R.id.nombreCliente)
        val cerrarSesion = vista.findViewById<Button>(R.id.CerrarSesion)
        val session = SessionManager(requireContext())
        correo.text = session.getCorreo()
        nombre.text = session.getNombre()

        cerrarSesion.setOnClickListener {
            session.limpiarSession()
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return vista
    }

}