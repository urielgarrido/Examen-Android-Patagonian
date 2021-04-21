package com.example.patagonianexamen.repository

import com.example.patagonianexamen.data.Lyrics
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface LyricsAPI {

    @GET("{artist}/{title}")
    @Headers("Content-Type:application/json")
    fun getLyrics(@Path("artist") artist: String,@Path("title") title: String): Call<Lyrics?>
}