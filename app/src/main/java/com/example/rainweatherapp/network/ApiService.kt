package com.example.rainweatherapp.network

import com.example.rainweatherapp.di.DaggerApiComponent
import com.example.rainweatherapp.models.Root
import com.example.rainweatherapp.util.AppConstants
import io.reactivex.Single
import javax.inject.Inject

class ApiService {
    @Inject
    lateinit var api: WeatherApi

    init
    {
        DaggerApiComponent.create().inject(this)
    }

    fun getRootData(city: String): Single<Root>
    {
        return api.getRoot(city,AppConstants.API_KEY,AppConstants.UNIT)
    }

}