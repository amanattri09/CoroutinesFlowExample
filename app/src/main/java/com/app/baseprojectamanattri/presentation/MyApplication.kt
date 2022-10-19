package com.app.baseprojectamanattri.presentation

import android.app.Application
import android.content.Intent
import dagger.hilt.android.HiltAndroidApp

/*test comment */
@HiltAndroidApp
class MyApplication :Application(){

    private var isRestarting: Boolean = false

    fun onLogout() {
        restartApp()
    }

    private fun restartApp() {
        if (!isRestarting) {
            isRestarting = true
            val intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

}