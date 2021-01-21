package com.cassiobruzasco.di

import com.cassiobruzasco.data.api.WeatherRepository
import com.cassiobruzasco.data.api.WeatherRepositoryImpl
import com.cassiobruzasco.parisweather.viewmodel.WeatherViewModel
import com.cassiobruzasco.util.DateUtil
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * All possible modules to inject dependencies at this project
 */
val viewModels = module {
    viewModel { WeatherViewModel(get(), get()) }
}

val repositories = module {
    single<WeatherRepository> { WeatherRepositoryImpl() }
}

val utils = module {
    single { DateUtil() }
}
