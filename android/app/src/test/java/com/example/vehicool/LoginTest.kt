package com.example.vehicool

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginTest {

    @Test
    fun validarCorreo() {
        val resultado = validarCorreo("usuario@example.com")
        assertTrue(resultado)
    }
    @Test
    fun validarCorreoFalso() {
        val resultadoFalso = validarCorreo("usuario@example::com")
        assertFalse(resultadoFalso)
    }

    fun validarCorreo(correo: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return regex.matches(correo)
    }
}