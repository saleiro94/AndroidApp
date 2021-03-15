package com.example.giteste

import android.app.Application
import com.example.giteste.Repository.NotaRepository
import com.example.giteste.db.NotaDB

class NotasApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { NotaDB.getDatabase(this) }
    val repository by lazy { NotaRepository(database.NotaDao()) }
}
