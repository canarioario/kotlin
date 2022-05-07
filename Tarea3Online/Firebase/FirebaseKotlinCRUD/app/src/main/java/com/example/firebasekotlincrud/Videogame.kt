package com.example.firebasekotlincrud
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Videogame(val name: String? = null, val correo: String? = null,  val telefono: String? = null,  @Exclude val key: String? = null) {
}