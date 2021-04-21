package com.example.patagonianexamen.repository

import com.example.patagonianexamen.data.Cancion
import com.example.patagonianexamen.data.Lyrics
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun obtenerLyricFromApi(artist: String, song: String) : Lyrics?
    fun obtenerCancionFromRoom(): Flow<Cancion>?
    suspend fun insertarCancionRoom(cancion: Cancion)

}