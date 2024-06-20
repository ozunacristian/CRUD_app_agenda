package com.ouxer.crudadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ouxer.crudadmin.databinding.ActivityCreateBinding
import com.ouxer.crudadmin.databinding.ActivityMainBinding

class CreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding
    private lateinit var refBaseDeDatos: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Probamos de usar el boton de "atras" para terminar la actividad acutual(CreateActivity) y lanzar la vista MainActivity
        binding.btnAtras.setOnClickListener {
            val intent = Intent(this@CreateActivity, MainActivity::class.java)
            startActivity(intent) // Inicia la pantalla MainActivity
            finish() // finaliza la pantalla actual (la cierra)
        }

        binding.btnGuardar.setOnClickListener {
            val fechaEstablecida = binding.tvFecha.text.toString()
            refBaseDeDatos = FirebaseDatabase.getInstance().getReference("Fecha Establecida")
            val datoFecha = DatoFecha(fechaEstablecida)
            refBaseDeDatos.child(fechaEstablecida).setValue(datoFecha).addOnSuccessListener {
                binding.tvFecha.text.clear() // esto limpia el text view

                // muestra un pequeño aviso de que se subió correctamente
                Toast.makeText(this, "Fecha guardada.", Toast.LENGTH_SHORT).show()

                // cambio de vista
                val intent = Intent(this@CreateActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                // Mensaje en caso de falla
                Toast.makeText(this, "Operación fallida.", Toast.LENGTH_SHORT).show()

            }
        }

    }
}