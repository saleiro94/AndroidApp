package com.example.giteste.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.giteste.entity.Nota
import kotlinx.coroutines.flow.Flow


@Dao
interface NotaDao {
    @Query("SELECT * FROM nota_table ORDER BY id ASC")
    fun getAlphabetizedWords(): Flow<List<Nota>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nota: Nota)

    @Query("DELETE FROM nota_table WHERE id== :id")
    suspend fun deleteNota(id:Int)
    @Update
    suspend fun updateNota(nota: Nota)
}