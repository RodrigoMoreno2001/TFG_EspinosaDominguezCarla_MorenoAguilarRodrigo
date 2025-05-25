package com.example.vehicool.app.vista

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        if (SessionManager(this).getPrivilegios()!="Mecanico") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, InicioCliente())
                .addToBackStack(null)
                .commit()
        }else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MecanicoInicio())
                .addToBackStack(null)
                .commit()
        }

        botonHome.setOnClickListener {

            if (SessionManager(this).getPrivilegios()!="Mecanico") {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, InicioCliente())
                    .addToBackStack(null)
                    .commit()
            }else{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MecanicoInicio())
                    .addToBackStack(null)
                    .commit()
            }
        }

    }
}