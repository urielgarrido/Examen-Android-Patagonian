package com.example.patagonianexamen.navigation

import com.example.patagonianexamen.data.CancionEntityRoom
import com.example.patagonianexamen.data.Lyrics

interface MainNavigator {
    fun goToSearchFragment()
    fun goToLyrics(lyrics: Lyrics)
    fun goToHistorial()
}