package com.example.ej4v1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.math.PI

class ResultadoArea : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_area)

        val dato = intent.getStringExtra("radio")
        val textView : TextView = findViewById(R.id.resultado3)
        val resul = (Integer.parseInt(dato) * Integer.parseInt(dato)) * PI
        textView.text = "La circuferecia con radio $dato tiene un area de $resul"
    }
}