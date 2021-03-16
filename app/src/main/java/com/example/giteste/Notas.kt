package com.example.giteste

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giteste.adapter.NotaAdapter
import com.example.giteste.entity.Nota
import com.example.giteste.viewModel.NotaViewModel
import com.example.giteste.viewModel.WordViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton


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

        NotaViewModel.allWords.observe(this){ words ->
            words.let { adapter.submitList(it) }
        }


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, Adicionar_notas::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)



        }
        adapter.setOnItemClick(object : NotaAdapter.onItemclick {
            override fun onEditClick(position: Int){


            }

            override fun onDeleteClick(position: Int){
            NotaViewModel.deleteNota(position)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(Adicionar_notas.EXTRA_REPLY)?.let { reply ->

                val word =Nota(id = null, reply.toString().substringBefore("]"), reply.toString().substringAfter("]"))
                Log.d("Valor", word.id.toString())

                NotaViewModel.insert(word)
                Toast.makeText(
                        applicationContext,
                        word.toString(),
                        Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                    applicationContext,
                    " word.toString",
                    Toast.LENGTH_LONG
            ).show()
        }
    }

}







