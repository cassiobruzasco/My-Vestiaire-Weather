package com.cassiobruzasco.parisweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cassiobruzasco.di.repositories
import com.cassiobruzasco.di.utils
import com.cassiobruzasco.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * The Main Activity
 * I like to handle UI with fragments
 * so basically I use the main activity just as a base activity for future fragment stacks
 */
class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val modules = mutableListOf(
            repositories,
            viewModels,
            utils
        )

        startKoin {
            androidContext(applicationContext)
            modules(modules)
        }
    }
}