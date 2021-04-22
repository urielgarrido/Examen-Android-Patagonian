package com.example.patagonianexamen

import android.app.Application
import com.example.patagonianexamen.providers.CancionRoomDatabase
import com.example.patagonianexamen.providers.LyricsRest
import com.example.patagonianexamen.repository.RepositoryImpl

class MainApplication: Application() {

    val database by lazy { CancionRoomDatabase.getDatabase(this) }
    val repository by lazy { RepositoryImpl(database.cancionDao()) }
}