package com.zly.flowapp2.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

open class ViewModelWithExceptionHandler : ViewModel() {

    companion object {
        const val LAUNCH_TAG_DEFAULT = "default"
        const val TAG = "App"
    }

    val exceptionLiveData = MutableLiveData<Any>()


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            //cancel应该直接抛出，否则协程无法正常cancel
            is CancellationException -> {
                Timber.tag(TAG).d("coroutine cancel!")
                throw exception
            }

            else -> {
                Timber.tag(TAG).d("Caught ${exception.stackTraceToString()}")
                exceptionLiveData.postValue(exception)
            }
        }
    }

    fun launchCoroutineInIO(tag: String = LAUNCH_TAG_DEFAULT, error: ((Exception) -> Unit)? = null, block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                e.printStackTrace()
                exceptionLiveData.postValue(e)
                withContext(Dispatchers.Main) {
                    error?.invoke(e)
                }
            }
        }
    }

    fun launchInCoroutine(tag: String = LAUNCH_TAG_DEFAULT, error: ((Exception) -> Unit)? = null, block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler + Dispatchers.Default) {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                e.printStackTrace()
                exceptionLiveData.postValue(e)
                withContext(Dispatchers.Main) {
                    error?.invoke(e)
                }
            }
        }
    }

    fun launchCoroutineInMain(tag: String = LAUNCH_TAG_DEFAULT, error: ((Exception) -> Unit)? = null, block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler) {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                exceptionLiveData.postValue(e)
                withContext(Dispatchers.Main) {
                    error?.invoke(e)
                }
            }
        }
    }

}