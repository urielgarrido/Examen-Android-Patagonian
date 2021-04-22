package com.example.patagonianexamen.repository

import com.example.patagonianexamen.data.Lyrics
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface LyricsAPI {

    @GET("{artist}/{title}")
    @Headers("Content-Type:application/json")
    suspend fun getLyrics(@Path("artist") artist: String,@Path("title") title: String): Response<Lyrics>
}