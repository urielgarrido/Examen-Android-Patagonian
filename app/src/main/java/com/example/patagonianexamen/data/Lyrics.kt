package com.example.patagonianexamen.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lyrics(
    @SerializedName("lyrics") var lyrics: String,
    @SerializedName("error") var error: String? = null
): Parcelable{

}
