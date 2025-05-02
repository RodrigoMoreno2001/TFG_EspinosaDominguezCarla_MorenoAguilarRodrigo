package com.example.vehicool.app.vista


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vehicool.R
import com.example.vehicool.app.DTO.Usuario
import com.example.vehicool.app.api.RetrofitClient
import com.example.vehicool.app.vista.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vehicool.backend.DTO.UsuarioDTO


class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val registrarse = findViewById<Button>(R.id.Registrarse)

        val correo = findViewById<EditText>(R.id.correo)
        val contrasena = findViewById<EditText>(R.id.contrasena)
        val repetirContrasena = findViewById<EditText>(R.id.repetirContrasena)
        val nombre = findViewById<EditText>(R.id.nombre)

        registrarse.setOnClickListener {

            if(contrasena.equals(repetirContrasena)){
                Toast.makeText(this@Registro, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario = Usuario(
                nombre = nombre.text.toString(),
                correo = correo.text.toString(),
                contrasena = contrasena.text.toString()
            )

            RetrofitClient.usuarioService.crearUsuario(usuario)
                .enqueue(object : Callback<UsuarioDTO> {
                    override fun onResponse(call: Call<UsuarioDTO>, response: Response<UsuarioDTO>) {
                        if (response.isSuccessful) {
                            val usuarioCreado = response.body()
                            Toast.makeText(this@Registro, "Usuario creado", Toast.LENGTH_SHORT).show()
                            Log.d("API", "Usuario creado: $usuarioCreado")
                        } else {
                            Toast.makeText(this@Registro, "Error al crear usuario", Toast.LENGTH_SHORT).show()
                            Log.e("API", "Error al crear usuario: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<UsuarioDTO>, t: Throwable) {
                        Toast.makeText(this@Registro, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        Log.e("API", "Error en red: ${t.message}")
                    }
                })
        }
    }
}