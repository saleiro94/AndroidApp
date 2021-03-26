package com.example.giteste

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.giteste.entity.Nota

class Adicionar_notas : AppCompatActivity() {

    private lateinit var editruaView: EditText
    private lateinit var editproblemaView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_notas)
        editruaView = findViewById(R.id.edit_rua)
        editproblemaView = findViewById(R.id.edit_problema)
        var id: Int =intent.getIntExtra("ID",0)

        val editRua = intent.getStringExtra("Rua")
        val editProblema = intent.getStringExtra("Problema")
        findViewById<EditText>(R.id.edit_rua).setText(editRua)
        findViewById<EditText>(R.id.edit_problema).setText(editProblema)
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editruaView.text) || TextUtils.isEmpty(editproblemaView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val rua= editruaView.text.toString()
                val problema = editproblemaView.text.toString()

                replyIntent.putExtra(RUA, rua)
                replyIntent.putExtra(PROBLEMA, problema)
                replyIntent.putExtra(ID, id)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val RUA= "com.example.android.wordlistsql.RUA"
        const val PROBLEMA = "com.example.android.wordlistsql.PROBLEMA"
        const val ID = "com.example.android.wordlistsql.ID"
    }
}



