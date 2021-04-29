package com.example.giteste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class deleteUpdate : AppCompatActivity() {

    private lateinit var editponto: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_update)


        editponto = findViewById(R.id.UpdatePontoEdit)

        var id: String =intent.getStringExtra("Problema")

        val editRua = intent.getStringExtra("tudo")

        findViewById<EditText>(R.id.UpdatePontoEdit).setText(editRua)
    }
    fun blogin(view: View){
        val button = findViewById<Button>(R.id.DeletePonto)

        button.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.Acidente -> {
                    if (checked) {
                        // Put some meat on the sandwich
                    } else {
                        // Remove the meat
                    }
                }
                R.id.Obras -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }
                // TODO: Veggie sandwich
            }
        }
    }

}