package com.example.giteste.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.giteste.R
import com.example.giteste.entity.Nota

class NotaAdapter : ListAdapter<Nota, NotaAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)

        holder.bind(current.rua,current.problema)


    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ruaItemView: TextView = itemView.findViewById(R.id.rua)
        private val problemItemView: TextView = itemView.findViewById(R.id.problema)

        fun bind(text: String?,text1: String?) {
            ruaItemView.text = text
            problemItemView.text = text1
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler, parent, false)
                return WordViewHolder(view)
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
