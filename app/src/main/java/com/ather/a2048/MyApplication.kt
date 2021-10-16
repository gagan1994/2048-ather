package com.ather.a2048

import android.app.Application

class MyApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPrefUtils.init(this)
    }
}