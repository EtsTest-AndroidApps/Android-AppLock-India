package com.webers.applock.service.logger

import android.util.Log

object ServiceLogger {
    private const val TAG = "AppLockerService"
    fun log(message: String) {
        Log.v(TAG, message)
    }
}