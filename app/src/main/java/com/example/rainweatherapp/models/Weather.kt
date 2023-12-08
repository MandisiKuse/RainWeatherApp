package com.example.rainweatherapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) : Serializable