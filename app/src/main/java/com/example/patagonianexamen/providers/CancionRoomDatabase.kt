package com.example.patagonianexamen.providers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.patagonianexamen.data.Cancion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Cancion::class), version = 1, exportSchema = false)
public abstract class CancionRoomDatabase: RoomDatabase() {

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