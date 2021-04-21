package com.example.patagonianexamen.providers

import com.example.patagonianexamen.repository.LyricsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LyricsRest {
    private val BASE_URL: String = "https://api.lyrics.ovh/v1/"

    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    val createAPI: LyricsAPI = retrofit.create(LyricsAPI::class.java)
}