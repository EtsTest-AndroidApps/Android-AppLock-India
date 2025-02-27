package com.webers.applock.service.stateprovider

import android.app.Service
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AppBackgroundObservable @Inject constructor(val context: Context) {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun get(): Flowable<String> {
        return Flowable.interval(100, TimeUnit.MILLISECONDS)
            .map {

                var backgroundApp: String? = null

                val mUsageStatsManager =
                    context.getSystemService(Service.USAGE_STATS_SERVICE) as UsageStatsManager
                val time = System.currentTimeMillis()

                val usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 3600, time)
                val event = UsageEvents.Event()
                while (usageEvents.hasNextEvent()) {
                    usageEvents.getNextEvent(event)
                    if (event.eventType == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                        backgroundApp = event.packageName
                    }
                }
                backgroundApp!!
            }.distinctUntilChanged()
    }

}