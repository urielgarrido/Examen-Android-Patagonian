package com.example.patagonianexamen.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.patagonianexamen.data.Cancion
import com.example.patagonianexamen.data.Lyrics
import com.example.patagonianexamen.repository.Repository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: Repository
) : BaseViewModel() {

    val ultimaCancion: LiveData<Cancion>? = repository.obtenerCancionFromRoom()?.asLiveData()

    fun insertCancionRoom(cancion: Cancion){
        viewModelScope.launch {
            repository.insertarCancionRoom(cancion)
        }
    }

    fun getLyricFromApi(artist: String, song: String): Lyrics?{
        return repository.obtenerLyricFromApi(artist, song)
    }

}