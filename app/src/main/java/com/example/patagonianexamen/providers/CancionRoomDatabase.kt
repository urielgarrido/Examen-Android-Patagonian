package com.example.patagonianexamen.providers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.patagonianexamen.data.CancionEntityRoom

@Database(entities = [CancionEntityRoom::class], version = 1, exportSchema = false)
abstract class CancionRoomDatabase: RoomDatabase() {

    abstract fun cancionDao(): CancionDAO

    companion object {

        @Volatile
        private var INSTANCE: CancionRoomDatabase? = null

        fun getDatabase(context: Context): CancionRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                CancionRoomDatabase::class.java,
                "cancion_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}