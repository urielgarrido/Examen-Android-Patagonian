package com.example.patagonianexamen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.patagonianexamen.data.CancionEntityRoom
import com.example.patagonianexamen.data.Lyrics
import com.example.patagonianexamen.repository.Repository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: Repository
) : BaseViewModel() {

    val ultimaCancionEntityRoom: LiveData<CancionEntityRoom>? = repository.obtenerCancionFromRoom()?.asLiveData()

    fun insertCancionRoom(cancionEntityRoom: CancionEntityRoom){
        viewModelScope.launch {
            repository.insertarCancionRoom(cancionEntityRoom)
        }
    }

    suspend fun getLyricFromApi(artist: String, song: String): Lyrics?{
        _showLoading.postValue(true)
        return repository.obtenerLyricFromApi(artist, song).also {
            _showLoading.postValue(false)
        }
    }

    fun isOnline(online: Boolean) {
        _isNetOn.postValue(online)
    }

}