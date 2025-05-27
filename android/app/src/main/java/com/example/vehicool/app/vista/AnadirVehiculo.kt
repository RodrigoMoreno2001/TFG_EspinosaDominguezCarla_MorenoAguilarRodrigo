package com.example.vehicool.app.vista

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.vehicool.R
import com.example.vehicool.app.DTO.salida.VehiculoOutputDTO
import com.example.vehicool.app.api.RetrofitClient
import com.example.vehicool.app.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.entrada.VehiculoDTO


class AnadirVehiculo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.fragment_anadir_vehiculo, container, false)

        val modeloEditText = vista.findViewById<EditText>(R.id.modelotxt)
        val matriculaEditText = vista.findViewById<EditText>(R.id.Matriculatxt)
        val anoEditText = vista.findViewById<EditText>(R.id.Anotxt)

        val anadirVehiculoBtn = vista.findViewById<Button>(R.id.AnadirVehiculobtn)

        anadirVehiculoBtn.setOnClickListener {

            val modelo = modeloEditText.text.toString().trim()
            val matricula = matriculaEditText.text.toString().trim()
            val anyoStr = anoEditText.text.toString().trim()
            // evita que los campos estén vacíos
            if (modelo.isEmpty() || matricula.isEmpty() || anyoStr.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idUsuario = SessionManager(requireContext()).getId() // se recoge el id del usuario autenticado

            // construye un objeto OutputDTO, utilizado para enviar datos al backend

            val nuevoVehiculo = VehiculoOutputDTO(
                matricula = matricula,
                modelo = modelo,
                anyo = anyoStr.toInt(),
                usuarioId = idUsuario
            )

            // envía datos al backend para persistir un vehiculo en BBDD através de la API

            // Se podría usar coroutines para una mejor estructura, pero se ha optado por callbacks para una implementación más simple.

            RetrofitClient.vehiculoService.crearVehiculo(nuevoVehiculo)
                .enqueue(object : Callback<VehiculoDTO> {
                    override fun onResponse(call: Call<VehiculoDTO>, response: Response<VehiculoDTO>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Vehiculo creado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Error al crear el vehiculo", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<VehiculoDTO>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        return vista
    }
}