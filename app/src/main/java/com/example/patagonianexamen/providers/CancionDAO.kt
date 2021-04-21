package com.example.patagonianexamen.providers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.patagonianexamen.data.Cancion
import kotlinx.coroutines.flow.Flow

@Dao
interface CancionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCancionRoom(cancion: Cancion)

    @Query("SELECT * FROM cancion_table ORDER BY id DESC LIMIT 1")
    fun getUltimaCancion(): Flow<Cancion>
}