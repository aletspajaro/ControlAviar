package com.tesji.controlaviar

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.tesji.controlaviar.databinding.ActivityInicio2Binding
import com.tesji.controlaviar.databinding.ActivityMainBinding

class Inicio2 : AppCompatActivity() {

    lateinit var binding: ActivityInicio2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio2)

        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        binding = ActivityInicio2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnSALIR : Button = findViewById(R.id.btnSALIR)
        val btnconsultar : Button = findViewById(R.id.btn_consultar)
        val tvConsultar : TextView = findViewById(R.id.tvConsulta)
        val btnINSERTAR : Button = findViewById(R.id.btn_insertar)
        val btnmas : Button = findViewById(R.id.btnmas)

        binding.btnConsultar.setOnClickListener()
        {
            var datosList = mutableListOf<String>()
            //var datos = ""
            db.collection("Birds")
                .get()
                .addOnSuccessListener { resultado ->
                    for (documento in resultado){
                        //------------------------//
                        val data = documento.data
                        val id = documento.id
                        val formattedData = "$id:\n " +
                                "  Nombre: ${data["Nombre"]}\n " +
                                "  Especie: ${data["Especie"]}\n " +
                                "  Sexo: ${data["Sexo"]}\n " +
                                "  Etapa: ${data["Etapa"]}"
                        datosList.add(formattedData)
                    }
                    binding.tvConsulta.text = datosList.joinToString ("\n")
                }
                .addOnFailureListener { exception ->
                    binding.tvConsulta.text = "No se ah podido conectar"
                }
        }

        binding.btnInsertar.setOnClickListener {
            guardarDatos(db)
        }

        binding.btnModificar.setOnClickListener {
            guardarDatos(db)
        }

        binding.btnBorrar.setOnClickListener {
            if (binding.etId.text.isNotBlank()
            ) {

                db.collection("Birds")
                    .document(binding.etId.text.toString())
                    .delete()
                    .addOnSuccessListener { _ ->
                        binding.tvConsulta.text = ""
                        Toast.makeText(baseContext, "Se Borro correctamente", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener { _ ->
                        binding.tvConsulta.text = "No se ah podido Borrar"
                    }
            } else{
                Toast.makeText(baseContext, " !! Debe insertar ID", Toast.LENGTH_SHORT).show()
            }
        }

        btnSALIR.setOnClickListener()
        {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        btnmas.setOnClickListener()
        {
            val i = Intent(this, Mas::class.java)
            startActivity(i)
        }


    }

    private fun guardarDatos(db: FirebaseFirestore) {
        if (binding.etNombre.text.isNotBlank() &&
            binding.etEspecie.text.isNotBlank() &&
            binding.etSexo.text.isNotBlank() &&
            binding.etEtapa.text.isNotBlank() &&
            binding.etId.text.isNotBlank()
        ) {

            val dato = hashMapOf(
                //"id" to binding.etId.text,
                "Nombre" to binding.etNombre.text.toString(),
                "Especie" to binding.etEspecie.text.toString(),
                "Sexo" to binding.etSexo.text.toString(),
                "Etapa" to binding.etEtapa.text.toString(),
            )
            db.collection("Birds")
                .document(binding.etId.text.toString())
                .set(dato)
                .addOnSuccessListener { _ ->
                    binding.tvConsulta.text = ""
                    Toast.makeText(baseContext, "Registro Actualizado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { _ ->
                    binding.tvConsulta.text = "No se ah podido añadir"
                }
        }else{
                Toast.makeText(baseContext, " ! Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            }

        }
    }
