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
            override fun onEditClick(position: Int ,rua: String, problema: String){

                val intent = Intent(this@Notas,Adicionar_notas::class.java)
                intent.putExtra("ID",position)
                intent.putExtra("Rua",rua)
                intent.putExtra("Problema",problema)

                startActivityForResult(intent,2)
            }

            override fun onDeleteClick(position: Int){
            NotaViewModel.deleteNota(position)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
          val rua = intentData?.getStringExtra(Adicionar_notas.RUA)
            val problema= intentData?.getStringExtra(Adicionar_notas.PROBLEMA)
            val id = intentData?.getIntExtra(Adicionar_notas.ID,0)
            if(rua != null && problema != null && id != null) {

         val nota =  Nota(id, rua, problema)
                NotaViewModel.updateNota(nota)

            }
            }else if(requestCode == 2){
            Toast.makeText(this, getString(R.string.Campos_vazios), Toast.LENGTH_SHORT).show()
        }
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val rua = intentData?.getStringExtra(Adicionar_notas.RUA)
            val problema= intentData?.getStringExtra(Adicionar_notas.PROBLEMA)
             if(rua != null && problema != null) {
                 val nota = Nota(id = null, rua, problema)


                 NotaViewModel.insert(nota)


            }
        }else if(requestCode == 1){
            Toast.makeText(this, "Erro: Campos Vazios", Toast.LENGTH_SHORT).show()
        }
    }

}







