package studio.alphared.tableforone.network

/**
 * A generic class for a task's binary response.
 */
sealed class Response<out T>

class Success<out T>(val data: T) : Response<T>()

class Failure(val code: Int, val message: String) : Response<Nothing>()