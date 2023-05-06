package com.zly.flowapp2.main.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.zly.flowapp2.base.ViewModelWithExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class MainVM : ViewModelWithExceptionHandler() {

    private val _testAFlow = MutableStateFlow(0)
    val testAFlow: StateFlow<Int> = _testAFlow.asStateFlow()

    private val _showDialogFlow = MutableSharedFlow<Boolean>()
    val showDialogFlow: SharedFlow<Boolean> = _showDialogFlow

    fun createTestAData() {
        launchCoroutineInIO {
            createTestA()
        }
    }

    private suspend fun createTestA() {
        var para = _testAFlow.value
        para++
        _testAFlow.value = para
        delay(3000)
        createTestA()
    }

    fun needShowDialog() {
        launchCoroutineInIO {
            _showDialogFlow.emit(true)
        }
    }
}