package com.rasyidin.moneyverse.domain

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ResultState<A: Any> {
    class Loading<T: Any> : ResultState<T>()
    class Idle<T: Any> : ResultState<T>()
    data class Empty<T: Any>(val message: String? = "") : ResultState<T>()
    data class Success<T: Any>(val data: T?) : ResultState<T>()
    data class Error<T: Any>(val throwable: Throwable, val message: String? = "") : ResultState<T>()
}

inline fun <T : Any, U : Any> mapResult(
    resultState: ResultState<out T>,
    mapper: T?.() -> U?
): ResultState<U> {
    return when (resultState) {
        is ResultState.Error -> ResultState.Error(resultState.throwable, resultState.message)
        is ResultState.Idle -> ResultState.Idle()
        is ResultState.Loading -> ResultState.Loading()
        is ResultState.Empty -> ResultState.Empty(resultState.message)
        is ResultState.Success -> {
            val data = resultState.data
            val mapData = mapper.invoke(data)
            ResultState.Success(mapData)
        }
    }
}

fun <T : Any> ResultState<T>.onFailure(result: (Throwable, String?) -> Unit) {
    if (this is ResultState.Error) {
        result.invoke(this.throwable, this.message)
    }
}

fun <T : Any> ResultState<T>.onEmpty(result: (String?) -> Unit) {
    if (this is ResultState.Empty) {
        result.invoke(this.message)
    }
}

fun <T : Any> ResultState<T>.onSuccess(result: (T?) -> Unit) {
    if (this is ResultState.Success) {
        result.invoke(this.data)
    }
}

fun <T : Any> ResultState<T>.onLoading(result: () -> Unit) {
    if (this is ResultState.Loading) {
        result.invoke()
    }
}

fun <T : Any> ResultState<T>.onIdle(result: () -> Unit) {
    if (this is ResultState.Idle) {
        result.invoke()
    }
}

fun <T : Any> idleStateFlow(): MutableStateFlow<ResultState<T>> = run {
    MutableStateFlow(ResultState.Idle())
}

fun <T : Any> idleChannel(): Channel<ResultState<T>> = run {
    Channel()
}