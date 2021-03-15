package com.example.giteste.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nota_table")
class Nota(@PrimaryKey(autoGenerate = true) val id: Int? = null,
           @ColumnInfo(name = "rua") val rua: String,
           @ColumnInfo(name = "problema") val problema: String)
