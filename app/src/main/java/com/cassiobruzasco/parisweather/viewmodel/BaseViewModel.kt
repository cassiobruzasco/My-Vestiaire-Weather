package com.cassiobruzasco.parisweather.viewmodel

import androidx.lifecycle.ViewModel

// Creating a base view model makes this project easily scalable
abstract class BaseViewModel : ViewModel() {

    fun <T> handleResponse(result: Result<T>, onFailure: (e: Throwable?) -> Unit): T? {
        return if (result.isFailure) {
            onFailure(result.exceptionOrNull())
            null
        } else {
            result.getOrNull()
        }
    }

    open fun initialize() {

    }

}