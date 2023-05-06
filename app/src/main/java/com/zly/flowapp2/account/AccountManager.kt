package com.zly.flowapp2.account

import com.zly.flowapp2.database.AppDatabase
import com.zly.flowapp2.database.bean.DaoTestBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

object AccountManager {

    //此种方式不合理
    val testFlow: Flow<DaoTestBean?> by lazy {
        AppDatabase.getInstance().testDao().getTestFlow()
    }


    private val coroutineIO = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    //接收初始状态及后续改变,类似于livedata
    val testStateFlow: StateFlow<DaoTestBean?> =
        AppDatabase.getInstance().testDao().getTestFlow().stateIn(coroutineIO, SharingStarted.Lazily, null)
    //只接收后续改变，类似于eventbus
    val testSharedFlow = testStateFlow.shareIn(coroutineIO, SharingStarted.Lazily)





}