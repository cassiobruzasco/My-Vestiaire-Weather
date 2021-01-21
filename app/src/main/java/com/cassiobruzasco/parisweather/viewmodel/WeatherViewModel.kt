package com.cassiobruzasco.parisweather.viewmodel

import androidx.lifecycle.viewModelScope
import com.cassiobruzasco.data.api.CityNotFoundException
import com.cassiobruzasco.data.api.NumberException
import com.cassiobruzasco.data.api.WeatherRepository
import com.cassiobruzasco.util.DateUtil
import kotlinx.coroutines.launch

// This is my View Model class, here I will handle all data before showing at the View layer
class WeatherViewModel(
    private val repo: WeatherRepository,
    val dateUtil: DateUtil
): BaseViewModel() {

    val model = WeatherModel()

    // Main function, this function will get my main object that contains all weather data for daily forecast
    fun getWeather(location: String, numberOfDays: Int) {
        model.weatherStateOb.value = WeatherModel.WeatherState.Loading(true)
        model.weatherLoadedOb.value = false
        viewModelScope.launch {
            val responseData = repo.getWeather(location, numberOfDays)
            val responseDataModel = handleResponse(responseData, ::handleGetWeatherError)
            responseDataModel?.let {
                model.weatherOb.value = it
            }
            model.weatherLoadedOb.value = true
            model.weatherStateOb.value = WeatherModel.WeatherState.Loading(false)
        }
    }

    // Handle possible errors that might happen when typing a city name wrong or inputting a string at number field
    private fun handleGetWeatherError(error: Throwable?) {
        model.weatherStateOb.value = WeatherModel.WeatherState.Loading(false)
        when (error) {
            is CityNotFoundException -> model.weatherStateOb.value = WeatherModel.WeatherState.CityNotFound(error.message)
            is NumberException -> model.weatherStateOb.value = WeatherModel.WeatherState.NotANumber(error.message)
            else -> model.weatherStateOb.value = WeatherModel.WeatherState.GenericError(error?.message)
        }
    }
}