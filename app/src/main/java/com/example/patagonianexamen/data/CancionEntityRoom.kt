package com.example.patagonianexamen.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cancion_table")
data class CancionEntityRoom(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "song") val song: String) {

    fun convertToCancionClass(): Cancion{
        return Cancion(this.artist, this.song)
    }
}
