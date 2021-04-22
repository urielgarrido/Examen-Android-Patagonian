package com.example.patagonianexamen.repository

import com.example.patagonianexamen.data.CancionEntityRoom
import com.example.patagonianexamen.data.Lyrics
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun obtenerLyricFromApi(artist: String, song: String) : Lyrics?
    fun obtenerCancionFromRoom(): Flow<CancionEntityRoom>?
    fun obtenerListCancionesFromRoom(): Flow<List<CancionEntityRoom>>?
    suspend fun insertarCancionRoom(cancionEntityRoom: CancionEntityRoom)

}