package com.example.ej4v1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ej4v1.R.id.radio
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boton : Button = findViewById(R.id.area)
        val boton2 : Button = findViewById(R.id.longitud)
        val ediText : EditText = findViewById(radio)

        boton.setOnClickListener {
            if (!ediText.text.isEmpty()){
            val miIntent = Intent(this,
                ResultadoArea::class.java).apply {
                putExtra("radio" , ediText.text.toString())
            }
            startActivity(miIntent)

            }else {
                val miIntent = Intent(this,
                    Vacio::class.java)
                startActivity(miIntent)
            }
        }

        boton2.setOnClickListener {
            if (!ediText.text.isEmpty()){
            val miIntent = Intent(this,
                ResulLongitud::class.java).apply {
                putExtra("radio" , ediText.text.toString())
            }
            startActivity(miIntent)
        }else{
                val miIntent = Intent(this,
                    Vacio::class.java)
                startActivity(miIntent)
        }
        }

    }
}