package com.cassiobruzasco.data.api

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * This is the base repository class that easily makes this app scalable for more repositories
 */
abstract class BaseRepository {

    /**
     * Handles the response from upper layer, if it has an error code it will decode and throw the
     * exception with the message with it.
     */
    protected suspend fun <T, R> handleResponse(
        errorBodyType: Class<R>? = null,
        call: suspend () -> Response<T>
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val parsedBody = response.body() ?: throw NullPointerException()
                    Result.success(parsedBody)
                } else {
                    val errorBody = try {
                        Gson().fromJson(response.errorBody()?.string(), errorBodyType)
                    } catch (e: JsonSyntaxException) {
                        null
                    }
                    when (response.code()) {
                        400 -> throw NumberException(errorBody = errorBody)
                        404 -> throw CityNotFoundException(errorBody = errorBody)
                        else -> {
                            throw GenericException(errorBody = errorBody)
                        }
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}

/**
 * Possible exceptions
 * NumberException for wrong format of days
 * CityNotFoundException for misspelled city name
 * GenericException all others exception will end up as generic
 */
class NumberException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
class CityNotFoundException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
class GenericException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
