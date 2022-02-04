package com.example.ej4v1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.math.PI
import kotlin.math.pow

class ResulLongitud : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resul_longitud)

        val dato = intent.getStringExtra("radio")
        val textView : TextView = findViewById(R.id.resultado2)
        val resul = (2 * PI * Integer.parseInt(dato))
        textView.text = "La circuferecia con radio $dato tiene una longitud de $resul"
    }
}