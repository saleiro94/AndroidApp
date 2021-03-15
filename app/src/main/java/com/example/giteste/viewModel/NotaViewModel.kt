package com.example.giteste.viewModel

import androidx.lifecycle.*
import com.example.giteste.Repository.NotaRepository
import com.example.giteste.entity.Nota

import kotlinx.coroutines.launch

class NotaViewModel (private val repository: NotaRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Nota>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(nota: Nota) = viewModelScope.launch {
        repository.insert(nota)
    }
}

class WordViewModelFactory(private val repository: NotaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
