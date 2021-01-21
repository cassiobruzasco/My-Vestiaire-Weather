package com.cassiobruzasco.parisweather.viewmodel

import com.cassiobruzasco.parisweather.repository.FakeWeatherRepository
import com.cassiobruzasco.util.DateUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup(){
        viewModel = WeatherViewModel(FakeWeatherRepository(), DateUtil())
    }

    @Test
    fun `insert correct city name, returns populated object`() {
        val result = viewModel.getWeather("Paris", 1)
        assertThat(result).isEqualTo(FakeWeatherRepository.exampleResult)
    }
}