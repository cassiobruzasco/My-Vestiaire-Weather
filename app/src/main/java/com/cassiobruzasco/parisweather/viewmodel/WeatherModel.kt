package com.cassiobruzasco.parisweather.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cassiobruzasco.data.api.WeatherResponseItem

/**
 * Model to map all observables and my possible states
 *
 */
class WeatherModel {

    val weatherOb = MutableLiveData<WeatherResponseItem>()
    val weatherStateOb = MutableLiveData<WeatherState>()
    val weatherLoadedOb = MutableLiveData<Boolean>().apply { value = false }

    sealed class WeatherState {
        data class Loading(val isLoading: Boolean) : WeatherState()
        data class GenericError(val message: String?) : WeatherState()
        data class CityNotFound(val message: String?) : WeatherState()
        data class NotANumber(val message: String?) : WeatherState()
    }
}