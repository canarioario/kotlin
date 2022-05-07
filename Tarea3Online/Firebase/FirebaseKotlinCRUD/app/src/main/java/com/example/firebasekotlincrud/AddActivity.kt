package com.example.firebasekotlincrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val myRef = database.getReference("videogame")

        val name=nameEditText.text
        val correo=correoEditText.text
        val telefono=telefonoEditText.text


        saveButton.setOnClickListener { v ->
            val videogame = Videogame(name.toString(), correo.toString(), telefono.toString())
            myRef.child(myRef.push().key.toString()).setValue(videogame)
            finish()
        }
    }
}