package com.bano.johan.cazarpatos


import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
class EncryptedSharedPreferencesManager :
    FileHandler {
    constructor(actividad: Activity) {
        this.masterKey = MasterKey.Builder(actividad)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        this.sharedPreferences = EncryptedSharedPreferences.create(
            actividad, // Contexto
            "secret_shared_prefs", // Nombre del archivo
            masterKey, // Llave maestra
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // Crear MasterKey para encriptación
    private val masterKey: MasterKey

    // Crear instancia de EncryptedSharedPreferences
    private val sharedPreferences: SharedPreferences

    // Guardar información en las SharedPreferences encriptadas
    override fun SaveInformation(datosAGrabar: Pair<String, String>)
    {
        val editor = sharedPreferences.edit()
        editor.putString(LOGIN_KEY, datosAGrabar.first)
        editor.putString(PASSWORD_KEY, datosAGrabar.second)
        editor.apply()
    }
    override fun ReadInformation(): Pair<String, String> {
        val email = sharedPreferences.getString(LOGIN_KEY,
            "").toString()
        val clave = sharedPreferences.getString(PASSWORD_KEY,
            "").toString()
        return email to clave
    }
}