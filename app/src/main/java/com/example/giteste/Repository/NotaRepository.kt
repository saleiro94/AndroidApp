package com.example.giteste.Repository

import androidx.annotation.WorkerThread
import com.example.giteste.dao.NotaDao
import com.example.giteste.entity.Nota
import kotlinx.coroutines.flow.Flow

class NotaRepository(private val NotaDao: NotaDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Nota>> = NotaDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(nota: Nota) {
        NotaDao.insert(nota)
    }
}
