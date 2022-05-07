package com.ncabanes.tarjetasd

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ncabanes.tarjetasd.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val BotonGuardar : Button = findViewById(R.id.btGuardar);

        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED
        ) || (ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED
        )) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                123
            )
        }

        binding.btGuardar.setOnClickListener {
            Guardar(binding.nombre.text.toString(),
                    binding.telefono.text.toString().toInt(),
                    binding.email.text.toString()

            )
          //  binding.tvContenido.text = Cargar()
        }
    }

    fun Guardar(nombre: String, telefono: Int, email: String) {
        try {
            val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
            val miCarpeta = File(rutaSD, "datos")
            if (!miCarpeta.exists()) {
                miCarpeta.mkdir()
            }
            val ficheroFisico = File(miCarpeta, "datos.txt")
            ficheroFisico.appendText("$nombre  ")
            ficheroFisico.appendText(" $telefono  ")
            ficheroFisico.appendText(" $email\n")
        }
        catch (e: Exception) {
            Toast.makeText(this,
                "No se ha podido guardar",
                Toast.LENGTH_LONG).show()
        }
    }

    fun Cargar() : String {
        var texto = ""
        try {
            val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
            val miCarpeta = File(rutaSD, "datos")
            val ficheroFisico = File(miCarpeta, "datos.txt")
            val fichero = BufferedReader(
                InputStreamReader(FileInputStream(ficheroFisico))
            )
            texto = fichero.use(BufferedReader::readText)
        }
        catch (e : Exception) {
            // Nada
        }
        return texto
    }
}