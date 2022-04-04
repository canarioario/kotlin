package info.jeovani.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_principal.*

class Principal : AppCompatActivity() {

    private lateinit var preferencias: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        preferencias = getSharedPreferences(Preferencias.DATOS, Context.MODE_PRIVATE)

        pBtGuardar.setOnClickListener {
            preferencias.edit().putString(Preferencias.NOMBRE, pEtNombre.text.toString()).apply()
            preferencias.edit().putString(Preferencias.APELLIDOS, pEtApellidos.text.toString()).apply()
            preferencias.edit().putInt(Preferencias.EDAD, pEtEdad.text.toString().toInt()).apply()
        }

        pBtObtener.setOnClickListener {
            pTvNombre.text = preferencias.getString(Preferencias.NOMBRE, "N/D")
            pTApellidos.text = preferencias.getString(Preferencias.APELLIDOS, "N/D")
            pTvEdad.text = preferencias.getInt(Preferencias.EDAD, 0).toString()
        }

        visualizar.setOnClickListener {
            pTvNombre.text = preferencias.getString(Preferencias.NOMBRE, "N/D")
            pTApellidos.text = preferencias.getString(Preferencias.APELLIDOS, "N/D")
            pTvEdad.text = preferencias.getInt(Preferencias.EDAD, 0).toString()
        }

        pBtBorrar.setOnClickListener{
            preferencias.edit().clear().apply()
            preferencias.edit().clear().apply()
            preferencias.edit().clear().apply()
        }
    }
}
