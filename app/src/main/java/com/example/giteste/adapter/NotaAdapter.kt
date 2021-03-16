package com.example.giteste.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.giteste.Adicionar_notas
import com.example.giteste.Notas
import com.example.giteste.NotasApplication
import com.example.giteste.R
import com.example.giteste.dao.NotaDao
import com.example.giteste.entity.Nota
import com.example.giteste.viewModel.NotaViewModel
import com.example.giteste.viewModel.WordViewModelFactory

class NotaAdapter : ListAdapter<Nota, NotaAdapter.WordViewHolder>(WordsComparator()) {
    private lateinit var onItemClickListener : onItemclick
    public fun setOnItemClick(newOnItemClickListener: NotaAdapter.onItemclick){
        onItemClickListener= newOnItemClickListener
    }

    interface onItemclick{
     fun onEditClick(position: Int)

     fun onDeleteClick(position: Int)
 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        val current = getItem(position)

        holder.bind(current.rua, current.problema,current.id)
       //older.deleteItemView.setOnClickListener {
       //
       //
       // val intent = Intent(it.getContext(), Notas::class.java)
       //   val id = current.id
       //   intent.putExtra("id", id.toString())
       //   it.getContext().startActivity(intent)
       //
       //


    }

    class WordViewHolder(itemView: View, onItemclick: onItemclick) : RecyclerView.ViewHolder(itemView) {
        private val ruaItemView: TextView = itemView.findViewById(R.id.rua)
        private val problemItemView: TextView = itemView.findViewById(R.id.problema)
        val deleteItemView : ImageButton = itemView.findViewById(R.id.delete)
        val upadateItemView : ImageButton = itemView.findViewById(R.id.update)

         var idItem : Int? = 0


        init {
            deleteItemView.setOnClickListener { v: View ->

                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION && idItem != null){

                    onItemclick.onDeleteClick(idItem!!)
                }
                Toast.makeText(
                       v.context,
                        " deele",
                        Toast.LENGTH_LONG
                ).show()
            }

            upadateItemView.setOnClickListener { v: View ->
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    onItemclick.onDeleteClick(position)
                }
                Toast.makeText(
                        v.context,
                        " update",
                        Toast.LENGTH_LONG
                ).show()
            }

        }
        fun bind(text: String?, text1: String?, id : Int?) {
            ruaItemView.text = text
            problemItemView.text = text1
            idItem = id


        }

        companion object {
            fun create(parent: ViewGroup, onItemClickListener: onItemclick): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler, parent, false)
                return WordViewHolder(view,onItemClickListener)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Nota>() {
        override fun areItemsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

