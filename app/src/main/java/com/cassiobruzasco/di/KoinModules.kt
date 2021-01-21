package com.cassiobruzasco.di

import com.cassiobruzasco.data.api.WeatherRepository
import com.cassiobruzasco.data.api.WeatherRepositoryImpl
import com.cassiobruzasco.parisweather.viewmodel.WeatherViewModel
import com.cassiobruzasco.util.DateUtil
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Here I map all possible modules to inject dependencies at my project
val viewModels = module {
    viewModel { WeatherViewModel(get()) }
}

val repositories = module {
    single<WeatherRepository> { WeatherRepositoryImpl() }
}

val utils = module {
    single { DateUtil() }
}
