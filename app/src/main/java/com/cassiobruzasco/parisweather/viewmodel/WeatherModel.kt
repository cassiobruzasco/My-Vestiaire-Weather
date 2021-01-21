package com.cassiobruzasco.parisweather.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cassiobruzasco.data.api.WeatherResponseItem

// This model maps my observables and my possible states
class WeatherModel {

    val weatherOb = MutableLiveData<WeatherResponseItem>()
    val weatherStateOb = MutableLiveData<WeatherState>()

    sealed class WeatherState {
        data class Loading(val isLoading: Boolean) : WeatherState()
        data class GenericError(val message: String?) : WeatherState()
        data class CityNotFound(val message: String?) : WeatherState()
        data class NotANumber(val message: String?) : WeatherState()
    }
}