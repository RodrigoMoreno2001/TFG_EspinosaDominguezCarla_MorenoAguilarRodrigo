package com.example.vehicool.app.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("session_usuario", Context.MODE_PRIVATE)

    companion object {
        private const val ID_USUARIO = "usuario_id"
        private const val NOMBRE_USUARIO = "usuario_nombre"
        private const val PRIVILEGIOS_USUARIO = "usuario_privilegios"
        private const val CORREO_USUARIO = "usuario_correo"
    }

    fun persistirUsuario(userId: Long, userName: String, privilegios: String, correo: String) {
        prefs.edit().apply {
            putLong(ID_USUARIO, userId)
            putString(NOMBRE_USUARIO, userName)
            putString(PRIVILEGIOS_USUARIO, privilegios)
            putString(CORREO_USUARIO, correo)
            apply()
        }
    }

    fun getId(): Long = prefs.getLong(ID_USUARIO, -1)

    fun getNombre(): String? = prefs.getString(NOMBRE_USUARIO, null)

    fun getPrivilegios(): String? = prefs.getString(PRIVILEGIOS_USUARIO, null)

    fun getCorreo(): String? = prefs.getString(CORREO_USUARIO, null)

    fun limpiarSession() {
        prefs.edit() { clear() }
    }
}
