package com.example.giteste

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giteste.adapter.NotaAdapter
import com.example.giteste.entity.Nota
import com.example.giteste.viewModel.NotaViewModel
import com.example.giteste.viewModel.WordViewModelFactory


class Notas : AppCompatActivity() {


    private val newWordActivityRequestCode = 1
    private val NotaViewModel:NotaViewModel by viewModels {
        WordViewModelFactory((application as NotasApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)
        setSupportActionBar(findViewById(R.id.toolbar))
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = NotaAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        NotaViewModel.allWords.observe(this){words ->
            words.let { adapter.submitList(it) }
        }
        //NotaViewModel.allwords.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
         //   words.let { adapter.submitList(it) }
        //}


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, Adicionar_notas::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)



        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(Adicionar_notas.EXTRA_REPLY)?.let { reply ->
                val word =Nota(id = null,reply.toString().substringBefore("]"),reply.toString().substringAfter("]"))
                NotaViewModel.insert(word)
                Toast.makeText(
                    applicationContext,
                    reply,
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "erro",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}







