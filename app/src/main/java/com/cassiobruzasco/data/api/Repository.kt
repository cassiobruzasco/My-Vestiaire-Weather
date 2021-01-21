package com.cassiobruzasco.data.api

// Base interface for my repositories to handle the result from calls
interface Repository {

    fun <T> handleResult(
        result: Result<T>,
        onFailure: (e: Throwable?) -> Unit = {}
    ): T? {
        return if (result.isFailure) {
            onFailure(result.exceptionOrNull())
            null
        } else {
            result.getOrNull()
        }
    }

}