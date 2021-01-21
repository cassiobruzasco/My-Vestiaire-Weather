package com.cassiobruzasco.parisweather.viewmodel

import androidx.lifecycle.viewModelScope
import com.cassiobruzasco.data.api.CityNotFoundException
import com.cassiobruzasco.data.api.NumberException
import com.cassiobruzasco.data.api.WeatherRepository
import com.cassiobruzasco.util.DateUtil
import kotlinx.coroutines.launch

/**
 * The Weather View Model
 * This class handle all data before showing at the view layer
 * @param repo Weather Repository
 * @param dateUtil to format dates
 *
 */
class WeatherViewModel(
    private val repo: WeatherRepository,
    val dateUtil: DateUtil
): BaseViewModel() {

    val model = WeatherModel()

    /**
     * The main function to get the weather forecast
     * this function will get the main object that contains all weather data from daily forecast
     *
     * @param location city name
     * @param numberOfDays number of days to forecast
     *
     */
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

    /**
     * Handle possible erros that might happend when typing a city name wrong or not putting a number at int field
     *
     * @param error The throwable error
     *
     */
    private fun handleGetWeatherError(error: Throwable?) {
        model.weatherStateOb.value = WeatherModel.WeatherState.Loading(false)
        when (error) {
            is CityNotFoundException -> model.weatherStateOb.value = WeatherModel.WeatherState.CityNotFound(error.message)
            is NumberException -> model.weatherStateOb.value = WeatherModel.WeatherState.NotANumber(error.message)
            else -> model.weatherStateOb.value = WeatherModel.WeatherState.GenericError(error?.message)
        }
    }
}