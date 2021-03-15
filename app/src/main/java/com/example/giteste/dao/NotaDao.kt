package com.example.giteste.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giteste.entity.Nota
import kotlinx.coroutines.flow.Flow


@Dao
interface NotaDao {
    @Query("SELECT * FROM nota_table ORDER BY id ASC")
    fun getAlphabetizedWords(): Flow<List<Nota>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nota: Nota)

    @Query("DELETE FROM nota_table")
    suspend fun deleteAll()

}