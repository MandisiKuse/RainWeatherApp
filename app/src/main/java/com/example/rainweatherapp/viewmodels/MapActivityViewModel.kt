package com.example.rainweatherapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rainweatherapp.di.DaggerApiComponent
import com.example.rainweatherapp.models.City
import com.example.rainweatherapp.models.Root
import com.example.rainweatherapp.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapActivityViewModel: ViewModel() {

    val apiRootObject = MutableLiveData<Root>()
    val networkError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    private val disposable= CompositeDisposable()

    @Inject
    lateinit var  apiService: ApiService
    init
    {
     DaggerApiComponent.create().inject(this)
    }

    private fun getApiRootObject(city: String)
    {
        isLoading.value=true

        disposable.add(
            apiService.getRootData(city)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Root>(){

                    override fun onSuccess(value: Root?) {
                       apiRootObject.value=value
                        isLoading.value=false
                        networkError.value=false
                    }

                    override fun onError(e: Throwable?) {
                        isLoading.value= false
                        networkError.value=true
                    }

                })
        )

    }

 override   fun onCleared()
    {
        super.onCleared()
        disposable.clear()
    }

    fun refresh(city: String)
    {
        getApiRootObject(city)
    }



}