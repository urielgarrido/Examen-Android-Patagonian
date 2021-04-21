package com.example.patagonianexamen.repository

import androidx.annotation.WorkerThread
import com.example.patagonianexamen.data.Cancion
import com.example.patagonianexamen.data.Lyrics
import com.example.patagonianexamen.providers.CancionDAO
import com.example.patagonianexamen.providers.LyricsRest
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val lyricRest: LyricsRest? = null, private val cancionDao: CancionDAO? = null): Repository {

    override fun obtenerLyricFromApi(artist: String, song: String): Lyrics? {
        val response = lyricRest?.createAPI?.getLyrics(artist, song)?.execute()
        return response?.body()
    }

    override fun obtenerCancionFromRoom(): Flow<Cancion>? {
        if (cancionDao != null) {
            return cancionDao.getUltimaCancion()
        }
        return null
    }

    @WorkerThread
    override suspend fun insertarCancionRoom(cancion: Cancion) {
        cancionDao?.insertCancionRoom(cancion)
    }
}