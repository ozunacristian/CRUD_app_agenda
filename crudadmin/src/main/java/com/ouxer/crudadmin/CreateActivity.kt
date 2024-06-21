package com.ouxer.crudadmin


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ouxer.crudadmin.databinding.ActivityCreateBinding


class CreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding
    private lateinit var refBaseDeDatos: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se sobreescribe la función del botón atras del sistema.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@CreateActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


        /*/ Probamos de usar el boton de "atras" para terminar la actividad acutual(CreateActivity) y lanzar la vista MainActivity
        binding.btnAtras.setOnClickListener {
            val intent = Intent(this@CreateActivity, MainActivity::class.java)
            startActivity(intent) // Inicia la pantalla MainActivity
            finish() // finaliza la pantalla actual (la cierra)
        }*/
        // ---------------- Spinners ----------------------------------
        val materias : Spinner = binding.spMaterias

        // ---------Harcodeo lista materia
        val listaMaterias = arrayOf("Materia", "Materia 01","Materia 02","Materia 03","Materia 04", "Materia 05")
        val adaptMaterias = ArrayAdapter<String>(this, R.layout.spinner_item, listaMaterias)

        materias.adapter = adaptMaterias

        // ------------------------------------------------------------

        binding.btnGuardar.setOnClickListener {

            val fechaEstablecida = binding.tvFecha.text.toString()
            refBaseDeDatos = FirebaseDatabase.getInstance().getReference("Fecha Establecida")
            val datoFecha = DatoFecha(fechaEstablecida)

            refBaseDeDatos.child(fechaEstablecida).setValue(datoFecha).addOnSuccessListener {
                binding.tvFecha.text.clear() // esto limpia el text view

                // muestra un pequeño aviso de que se subió correctamente
                Toast.makeText(this, "Fecha guardada.", Toast.LENGTH_LONG).show()

            }.addOnFailureListener {
                // Mensaje en caso de falla
                Toast.makeText(this, "Operación fallida.", Toast.LENGTH_LONG).show()

            }
        }

    }
}
