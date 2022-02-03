package com.example.ej3v1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var datos = ArrayList<Cursos>()
    var adaptador : AdapterCurso? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        datos.add(Cursos(
            "Cursos de Natacion",
            "32 Horas",
            R.drawable.ic_baseline_pool_24
        ))
        datos.add(Cursos(
                    "Curso de Baloncesto",
                    "96 Horas",
                    R.drawable.ic_baseline_sports_basketball_24
                ))
        datos.add(Cursos(
            "Cursos de Ateltismo",
            "67 Horas",
            R.drawable.ic_baseline_directions_run_24
        ))
        datos.add(Cursos(
            "Cursos de Balonmano",
            "35 Horas",
            R.drawable.ic_baseline_sports_handball_24
        ))
        datos.add(Cursos(
            "Cursos de Ciclismo",
            "72 Horas",
            R.drawable.ic_baseline_directions_bike_24
        ))
        datos.add(Cursos(
            "Cursos de Tennis",
            "122 Horas",
            R.drawable.ic_baseline_sports_tennis_24
        ))

                adaptador = AdapterCurso (this, datos)
                miLista.adapter = adaptador

    }
    override fun onStart() {
        super.onStart()
        miLista.onItemClickListener =
        object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long)
            {
                Snackbar.make(  Linear,
                    "Seleccionado: ${datos[position].nombre}",
                    Snackbar.LENGTH_LONG).setAction("Confirmar") {
                    Linear.setBackgroundColor(Color.CYAN)
                } .show()
            }

        }}

}