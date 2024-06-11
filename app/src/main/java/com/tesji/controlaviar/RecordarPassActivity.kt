package com.tesji.controlaviar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecordarPassActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordar_pass)

        val txtcorreocambio : TextView = findViewById(R.id.txtcorreo)
        val btncambiar : Button = findViewById(R.id.btncambiar)
        val btnregresar : Button = findViewById(R.id.btnregresar2)

        btnregresar.setOnClickListener()
        {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        btncambiar.setOnClickListener()
        {
            if (txtcorreocambio.text.toString().trim().isEmpty()){
                txtcorreocambio.setError("Debe colocar un correo electronico")
                return@setOnClickListener
            }else{
                sendPasswordResert(txtcorreocambio.text.toString())
            }
        }

        //Inicializar la variable
        firebaseAuth= Firebase.auth

    }


    private fun sendPasswordResert(email: String)
    {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(){task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(baseContext,"CORREO DE CAMBIO DE CONTRASEÑA ENVIADO",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(baseContext,"NO SE COMPLETO EL PROCESO",Toast.LENGTH_SHORT).show()

                }
            }
    }
}