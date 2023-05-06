package com.zly.flowapp2.app

import android.app.Application

class App : Application() {

    init {
        context = this
    }
    companion object {
        @JvmStatic
        lateinit var context: App
    }

}