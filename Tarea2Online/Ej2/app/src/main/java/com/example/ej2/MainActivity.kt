package com.example.ej2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sumar.setOnClickListener {
            if (!num1.text.isEmpty() && !num2.text.isEmpty()){
                resultado.text = "Resultado: "+ (num1.text.toString().toInt() + num2.text.toString().toInt()).toString()
                Snackbar.make(  Linear,
                    "Operacion realizada con exito",
                    Snackbar.LENGTH_LONG).setAction("Confirmar") {
                    Linear.setBackgroundColor(Color.CYAN)
                } .show()
            }else{
                resultado.text = "Resultado: 0"
                Toast.makeText(applicationContext,
                    "Error: Campos vacios",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}