package  com.example.rainweatherapp.di

import com.example.rainweatherapp.di.ApiModule
import com.example.rainweatherapp.network.ApiService
import com.example.rainweatherapp.viewmodels.MapActivityViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: ApiService)
    fun inject(mapActivityViewModel: MapActivityViewModel)
}