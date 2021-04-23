package com.example.giteste

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun bnotas(view: View){
        val button = findViewById<Button>(R.id.notas)
        button.setOnClickListener{
            val intent = Intent(this, Notas::class.java)
            startActivity(intent)
        }
    }
    fun blogin(view: View){
        val button = findViewById<Button>(R.id.blogin)

        button.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}