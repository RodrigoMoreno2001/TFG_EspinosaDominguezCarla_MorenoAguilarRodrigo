package com.example.vehicool.app.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("session_usuario", Context.MODE_PRIVATE)

    companion object {
        private const val ID_USUARIO = "usuario_id"
        private const val NOMBRE_USUARIO = "usuario_nombre"
    }

    fun persistirUsuario(userId: Long, userName: String) {
        prefs.edit().apply {
            putLong(ID_USUARIO, userId)
            putString(NOMBRE_USUARIO, userName)
            apply()
        }
    }

    fun getId(): Long = prefs.getLong(ID_USUARIO, -1)

    fun getNombre(): String? = prefs.getString(NOMBRE_USUARIO, null)

    fun limpiarSession() {
        prefs.edit().clear().apply()
    }
}
