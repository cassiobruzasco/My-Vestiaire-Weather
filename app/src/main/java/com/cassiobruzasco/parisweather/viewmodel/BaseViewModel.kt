package com.cassiobruzasco.parisweather.viewmodel

import androidx.lifecycle.ViewModel

/**
 * Base view model to handle response for one or more view models
 *
 */
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