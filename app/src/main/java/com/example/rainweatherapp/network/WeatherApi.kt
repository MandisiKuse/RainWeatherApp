package com.example.rainweatherapp.network

import com.example.rainweatherapp.models.Root
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/forecast")
    fun getRoot(@Query("q") city: String, @Query("appid")apiKey: String,@Query("units")unit: String): Single<Root>
}