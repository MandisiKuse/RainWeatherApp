package com.example.rainweatherapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class Wind(
    val deg: Int,
    val speed: Double
) : Serializable