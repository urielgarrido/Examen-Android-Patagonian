package com.example.patagonianexamen.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cancion_table")
data class Cancion(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "song") val song: String) {

}
