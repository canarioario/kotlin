package com.example.firebasekotlincrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("videogame").child(key)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val videogame:Videogame? = dataSnapshot.getValue(Videogame::class.java)
                if (videogame != null) {
                    nameEditText.text = Editable.Factory.getInstance().newEditable(videogame.name)
                    correoEditText.text = Editable.Factory.getInstance().newEditable(videogame.correo)
                    telefonoEditText.text = Editable.Factory.getInstance().newEditable(videogame.telefono)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        saveButton.setOnClickListener { v ->

            val name : String = nameEditText.text.toString()
            val correo : String = correoEditText.text.toString()
            val telefono: String = telefonoEditText.text.toString()


            myRef.child("name").setValue(name)
            myRef.child("correo").setValue(correo)
            myRef.child("telefono").setValue(telefono)


            finish()
        }
    }

}