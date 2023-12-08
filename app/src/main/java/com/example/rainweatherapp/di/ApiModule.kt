package  com.example.rainweatherapp.di

import com.example.rainweatherapp.network.ApiService
import com.example.rainweatherapp.network.WeatherApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    @Provides
    fun providesWeatherApi(): WeatherApi
    {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherApi::class.java)

    }

    @Provides
    fun providesApiService(): ApiService
    {
        return ApiService()
    }
}