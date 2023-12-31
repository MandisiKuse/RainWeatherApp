package com.example.rainweatherapp.models

import com.example.rainweatherapp.models.Coord
import java.io.Serializable


data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
) : Serializable