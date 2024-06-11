package com.tesji.controlaviar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Mas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mas)

        val btnsalirdemas : Button = findViewById(R.id.buttonsalirdemas)

        btnsalirdemas.setOnClickListener()
        {
            val i = Intent(this, Inicio2::class.java)
            startActivity(i)
        }
    }
}