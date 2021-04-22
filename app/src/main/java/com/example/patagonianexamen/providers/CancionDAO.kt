package com.example.patagonianexamen.providers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.patagonianexamen.data.CancionEntityRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface CancionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCancionRoom(cancionEntityRoom: CancionEntityRoom)

    @Query("SELECT * FROM cancion_table ORDER BY id DESC LIMIT 1")
    fun getUltimaCancion(): Flow<CancionEntityRoom>

    @Query("SELECT * FROM cancion_table")
    fun getListCancionesFromRoom() : Flow<List<CancionEntityRoom>>
}