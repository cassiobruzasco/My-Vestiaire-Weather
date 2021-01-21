package com.cassiobruzasco.data.api

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

// This is my base repository class that easily makes this app scalable for more repositories
abstract class BaseRepository {

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

class NumberException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
class CityNotFoundException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
class GenericException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
