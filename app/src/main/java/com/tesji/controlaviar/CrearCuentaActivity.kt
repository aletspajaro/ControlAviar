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

class CrearCuentaActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        val txtnombre_nuevo : TextView = findViewById(R.id.edtnombre)
        val txtemail_nuevo : TextView = findViewById(R.id.edtemailnuevo)
        val txtpassword1 : TextView = findViewById(R.id.edtpasswordnuevo1)
        val txtpassword2 : TextView = findViewById(R.id.edtpasswordnuevo2)
        val btnCrear : Button = findViewById(R.id.btncrearnueva)

        val btnregresar : Button = findViewById(R.id.btnregresarC)

        btnregresar.setOnClickListener()
        {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        btnCrear.setOnClickListener()
        {
            if (txtnombre_nuevo.text.toString().trim().isEmpty()) {
                txtnombre_nuevo.setError("Debe colocar un nombre")
                return@setOnClickListener
            }
                if (txtemail_nuevo.text.toString().trim().isEmpty()) {
                    txtemail_nuevo.setError("Debe colocar un correo electronico")
                    return@setOnClickListener
                }
                    if (txtpassword1.text.toString().trim().isEmpty()) {
                        txtpassword1.setError("Debe colocar una contraseña")
                        return@setOnClickListener
                    }
                        if (txtpassword2.text.toString().trim().isEmpty()) {
                            txtpassword2.setError("Debe confirmar su contraseña")
                            return@setOnClickListener

            }else{
                var pass1 = txtpassword1.text.toString()
                var pass2 = txtpassword2.text.toString()
                if (pass1.equals(pass2))
                {
                    createAccount(txtemail_nuevo.text.toString(),txtpassword1.text.toString())
                }
                else
                {
                    Toast.makeText(baseContext, "Error: la contraseña no coincide",Toast.LENGTH_SHORT).show()
                    txtpassword1.requestFocus()
                }
            }

        }

        //Inicializar la variable
        firebaseAuth= Firebase.auth
    }
    private fun createAccount(email: String, password:String)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful)
                {
                    sendEmailVerification()
                    Toast.makeText(baseContext, "Cuenta Creada correctamente se requiere verificacion ",Toast.LENGTH_SHORT)
                        .show()
                }
                else
                {
                    Toast.makeText(baseContext, "ALGO SALIO MAL" + task.exception, Toast.LENGTH_SHORT)
                        .show()
                }

                }
    }
    private fun sendEmailVerification()
    {
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this){ task ->
           if (task.isSuccessful)
           {

           }
           else
           {

           }
        }
    }
}