package com.example.patagonianexamen.repository

import androidx.annotation.WorkerThread
import com.example.patagonianexamen.data.CancionEntityRoom
import com.example.patagonianexamen.data.Lyrics
import com.example.patagonianexamen.providers.CancionDAO
import com.example.patagonianexamen.providers.LyricsRest
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RepositoryImpl(private val cancionDao: CancionDAO? = null): Repository {

    override suspend fun obtenerLyricFromApi(artist: String, song: String): Lyrics? {
        val creatApi = LyricsRest.createAPI
        val response = creatApi.getLyrics(artist, song)
        return response.body()
    }

    override fun obtenerCancionFromRoom(): Flow<CancionEntityRoom>? {
        if (cancionDao != null) {
            return cancionDao.getUltimaCancion()
        }
        return null
    }

    override fun obtenerListCancionesFromRoom(): Flow<List<CancionEntityRoom>>? {
        if (cancionDao != null){
            return cancionDao.getListCancionesFromRoom()
        }
        return null
    }

    @WorkerThread
    override suspend fun insertarCancionRoom(cancionEntityRoom: CancionEntityRoom) {
        cancionDao?.insertCancionRoom(cancionEntityRoom)
    }
}