package com.example.patagonianexamen.navigation

import com.example.patagonianexamen.data.Cancion
import com.example.patagonianexamen.data.Lyrics

interface MainNavigator {
    fun goToSearchFragment()
    fun goToLyrics(lyrics: Lyrics)
    fun goToHistorial(listaCanciones: List<Cancion>)
}