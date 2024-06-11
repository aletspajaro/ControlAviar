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

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btningresar : Button = findViewById(R.id.btningresar)
        val txtemail : TextView = findViewById(R.id.edtemail)
        val txtpass : TextView = findViewById(R.id.edtpassword)
        val btnCrear_cuentanueva : TextView = findViewById(R.id.btncrearcuenta)
        val btnolvidar : TextView = findViewById(R.id.btnolvidar)

        //Inicializar la variable
        firebaseAuth= Firebase.auth

        btningresar.setOnClickListener()
        {if (txtemail.text.toString().trim().isEmpty()){
            txtemail.setError("Debe colocar su correo electronico")
            return@setOnClickListener
        }
            if (txtpass.text.toString().trim().isEmpty()) {
            txtpass.setError("Debe ingresar contraseña")
            return@setOnClickListener

        }
            signIn(txtemail.text.toString(), txtpass.text.toString())
        }




        btnCrear_cuentanueva.setOnClickListener() {
            val i = Intent(this,CrearCuentaActivity::class.java)
            startActivity(i)
        }




        btnolvidar.setOnClickListener()
        {
            val i = Intent(this,RecordarPassActivity::class.java)
            startActivity(i)
        }



    }
    private fun signIn(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    val user = firebaseAuth.currentUser
                    val verifica = user?.isEmailVerified
                    if (verifica==true){


                    Toast.makeText(baseContext,"Autentificacion Exitosa", Toast.LENGTH_SHORT).show()
                    //AQUI VAMOS A IR A LA SEGUNDA ACTIVITY
                    val i = Intent(this, Inicio2::class.java)
                    startActivity(i)
                    }
                    else
                    {
                        Toast.makeText(baseContext,"No ha verificado su correo", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(baseContext,"Error de Email y/o Contraseña", Toast.LENGTH_SHORT).show()
                }
            }
    }
}