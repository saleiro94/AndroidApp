package com.example.giteste

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText

class UpdateNota : AppCompatActivity() {
    private lateinit var editruaView: EditText
    private lateinit var editproblemaView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_notas)
        editruaView = findViewById(R.id.edit_rua)
        editproblemaView = findViewById(R.id.edit_problema)
        var ss: String = intent.getStringExtra("id").toString()

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editruaView.text) || TextUtils.isEmpty(editproblemaView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val rua= editruaView.text.toString()
                val problema = editproblemaView.text.toString()

                Log.d("Valor", ss)
                replyIntent.putExtra(EXTRA_REPLY, "$rua "+" ] "+" $problema"+" [ "+" $ss")
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
