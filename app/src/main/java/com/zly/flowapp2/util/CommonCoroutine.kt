package com.zly.flowapp2.util


import kotlinx.coroutines.*
import timber.log.Timber

private val TAG = "coroutine_exh"

private val coroutineDefault = CoroutineScope(SupervisorJob() + Dispatchers.Default)
fun launchCoroutine(error: ((ex: Throwable) -> Unit)? = null, block: suspend CoroutineScope.() -> Unit): Job {
    val exceptionHandler = getExceptionHandler(error)
    val defaultJob = coroutineDefault.launch(exceptionHandler) {
        block.invoke(this)
    }
    return defaultJob
}

private val coroutineIO = CoroutineScope(SupervisorJob() + Dispatchers.IO)
fun launchCoroutineByIO(error: ((ex: Throwable) -> Unit)? = null, block: suspend CoroutineScope.() -> Unit): Job {
    val exceptionHandler = getExceptionHandler(error)
    val ioJob = coroutineIO.launch(exceptionHandler) {
        block.invoke(this)
    }
    return ioJob
}

private val coroutineMain = CoroutineScope(SupervisorJob() + Dispatchers.Main)
fun launchCoroutineByMain(error: ((ex: Throwable) -> Unit)? = null, block: suspend CoroutineScope.() -> Unit): Job {
    val exceptionHandler = getExceptionHandler(error)
    val mainJob = coroutineMain.launch(exceptionHandler) {
        block.invoke(this)
    }
    return mainJob
}

fun CoroutineScope.launchCoroutine(error: ((ex: Throwable) -> Unit)? = null, block: suspend CoroutineScope.() -> Unit): Job {
    val exceptionHandler = getExceptionHandler(error)
    val defaultJob = this.launch(exceptionHandler) {
        block.invoke(this)
    }
    return defaultJob
}

fun CoroutineScope.launchCoroutineByDefault(error: ((ex: Throwable) -> Unit)? = null, block: suspend CoroutineScope.() -> Unit): Job {
    val exceptionHandler = getExceptionHandler(error)
    val defaultJob = this.launch(exceptionHandler + Dispatchers.Default) {
        block.invoke(this)
    }
    return defaultJob
}

fun CoroutineScope.launchCoroutineByIO(error: ((ex: Throwable) -> Unit)? = null, block: suspend CoroutineScope.() -> Unit): Job {
    val exceptionHandler = getExceptionHandler(error)
    val ioJob = this.launch(exceptionHandler+Dispatchers.IO) {
        block.invoke(this)
    }
    return ioJob
}

private fun getExceptionHandler(error: ((ex: Throwable) -> Unit)?): CoroutineExceptionHandler {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is CancellationException -> throw  exception
            else -> {
                Timber.tag(TAG).d("Caught-launchCoroutineByIO ${exception.stackTraceToString()}")
                error?.invoke(exception)
            }
        }
    }
    return exceptionHandler
}