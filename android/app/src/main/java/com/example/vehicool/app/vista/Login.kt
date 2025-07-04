package com.example.vehicool.app.vista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vehicool.R
import com.example.vehicool.app.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.vehicool.app.DTO.salida.AutenticarDTO
import com.example.vehicool.app.utils.SessionManager
import vehicool.backend.DTO.entrada.UsuarioDTO

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Si el usuario está logueado, salta directamente a la pantalla de inicio
        if(SessionManager(this@Login).isUsuarioLogueado()){
            val intent = Intent(this@Login, AppActivity::class.java)
            startActivity(intent)
            finish()
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val correoInput = findViewById<EditText>(R.id.correo)
        val contrasenaInput = findViewById<EditText>(R.id.contrasena)
        val accederBtn = findViewById<Button>(R.id.Acceder)
        val registro = findViewById<TextView>(R.id.Registro)

        registro.setOnClickListener {
            val intent = Intent(this@Login, Registro::class.java)
            startActivity(intent)
        }

        accederBtn.setOnClickListener {
            val correo = correoInput.text.toString()
            val contrasena = contrasenaInput.text.toString()
            if(!validarCorreo(correo)){
                Toast.makeText(this@Login, "Este correo es inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val dto = AutenticarDTO(correo, contrasena)
            inicioSesion(dto)
        }
    }
    // Función para validar formato del correo por expresiones regulares
    fun validarCorreo(correo: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return regex.matches(correo)
    }

    /**
     * Función para autenticar al usuario con el backend.
     * Recibe un AutenticarDTO con correo y contraseña.
     * En caso de éxito, guarda la sesión y abre la pantalla principal.
     */

    fun inicioSesion(dto: AutenticarDTO) {
        RetrofitClient.usuarioService.autenticar(dto).enqueue(object : Callback<UsuarioDTO> {
            override fun onResponse(call: Call<UsuarioDTO>, response: Response<UsuarioDTO>) {
                if (response.isSuccessful && response.body() != null) {

                    val usuario = response.body()
                    val session = SessionManager(this@Login)

                    session.persistirUsuario(usuario?.id ?: -1, usuario?.nombre ?: "", usuario?.privilegios ?: "", usuario?.correo ?: "")
                    Toast.makeText(this@Login, "Bienvenido ${usuario?.nombre}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@Login, AppActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsuarioDTO>, t: Throwable) {
                Log.e("LOGIN", "Error de conexión", t)
                Toast.makeText(this@Login, "Error al conectar a la API", Toast.LENGTH_SHORT).show()
            }
        })
    }

}