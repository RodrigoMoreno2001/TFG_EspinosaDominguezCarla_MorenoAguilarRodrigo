package com.example.vehicool

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import com.example.vehicool.app.api.RetrofitClient
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.vehicool.app.DTO.salida.AutenticarDTO
import vehicool.backend.DTO.FacturaDTO
import vehicool.backend.DTO.UsuarioDTO
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class APITestInstrumented {

    @Test
    fun conexionAPI() {
        val latch = CountDownLatch(1)

        // Aqui deben ir credenciales correctas dado
        // a que es una prueba real y no con Mockito

        val dto = AutenticarDTO("usuario@domino.exe", "1234")

        RetrofitClient.usuarioService.autenticar(dto).enqueue(object : Callback<UsuarioDTO> {
            override fun onResponse(call: Call<UsuarioDTO>, response: Response<UsuarioDTO>) {
                assertTrue(response.isSuccessful)
                latch.countDown()
            }

            override fun onFailure(call: Call<UsuarioDTO>, t: Throwable) {
                assertTrue("Fallo de conexión: $t", false)
                latch.countDown()
            }
        })

        val isCompleted = latch.await(30, TimeUnit.SECONDS)
        assertTrue("La llamada a la API tardó demasiado", isCompleted)
    }

    @Test
    fun verFactura(){
        val latch = CountDownLatch(1)
        RetrofitClient.facturaService.getFactura(1).enqueue(object : Callback<FacturaDTO> {
            override fun onResponse(call: Call<FacturaDTO>, response: Response<FacturaDTO>) {
                assertTrue(response.isSuccessful)
                latch.countDown()
            }

            override fun onFailure(call: Call<FacturaDTO>, t: Throwable) {
                assertTrue("Fallo de conexión: $t", false)
                latch.countDown()
            }
        })
    }
}
