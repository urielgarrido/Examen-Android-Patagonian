package com.example.patagonianexamen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.patagonianexamen.data.CancionEntityRoom
import com.example.patagonianexamen.data.Lyrics
import com.example.patagonianexamen.repository.Repository

class HistoryViewModel(
    private val repository: Repository
) : BaseViewModel() {

    val listCancionesFromRoom: LiveData<List<CancionEntityRoom>>? = repository.obtenerListCancionesFromRoom()?.asLiveData()

    suspend fun getLyricFromApi(artist: String, song: String): Lyrics?{
        _showLoading.postValue(true)
        return repository.obtenerLyricFromApi(artist, song).also {
            _showLoading.postValue(false)
        }
    }

}