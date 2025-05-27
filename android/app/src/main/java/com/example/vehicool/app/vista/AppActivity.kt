package com.example.vehicool.app.vista

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.vehicool.R
import com.example.vehicool.app.utils.SessionManager

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val botonCitas: ImageButton = findViewById(R.id.botonCitas)
        val botonHome: ImageButton = findViewById(R.id.botonHome)
        val botonPerfil: ImageButton = findViewById(R.id.botonperfil)
        // Se obtiene el tipo de usuario (Usuario o Mecanico)
        val privilegios = SessionManager(this).getPrivilegios()

        // Al iniciar la actividad, se muestra el fragmento de inicio según el tipo de usuario

        if (privilegios!="Mecanico") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, InicioCliente())
                .commit()
        }else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MecanicoInicio())
                .commit()
        }

        botonHome.setOnClickListener {
            if (privilegios!="Mecanico") {
                cambiarFragment(InicioCliente())
            }else{
                cambiarFragment(MecanicoInicio())
            }
        }

        botonCitas.setOnClickListener {

            if (privilegios!="Mecanico") {
                cambiarFragment(Reparaciones())
            }else{
                Toast.makeText(this@AppActivity, "Esta función no se encuentra disponible para mecánicos", Toast.LENGTH_LONG).show()
            }
        }

        botonPerfil.setOnClickListener {
            cambiarFragment(Perfil())
        }

    }
    private fun cambiarFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}