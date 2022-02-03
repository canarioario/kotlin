package com.example.ej3v1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.elemento_de_lista.view.*
import java.text.FieldPosition

class AdapterCurso :BaseAdapter{
var contexto : Context? = null
    var listaDatos = ArrayList<Cursos>()

    constructor(
        contexto : Context,
        lista : ArrayList<Cursos>
    ) : super(){
        this.contexto = contexto
        this.listaDatos = lista

    }
    override fun getView(position: Int,
                         conVertView: View?,
                         parent: ViewGroup?
    ): View {
        val dato = listaDatos[position]
        var inflador = LayoutInflater.from(contexto)
        var viewDato = inflador.inflate(R.layout.elemento_de_lista, null)
        viewDato.nombre.text = dato.nombre
        viewDato.duracion.text = dato.duracion
        viewDato.ivCursos.setImageResource(dato.imagen)

        return viewDato
    }
    override fun getCount(): Int {
        return listaDatos.size
    }

    override fun getItem(position: Int): Any {
        return listaDatos[position]
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }



}