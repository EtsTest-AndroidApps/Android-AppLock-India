package com.webers.applock
/*
    Edited and updated
    by Prasoon Kumar
 */

import android.content.Context
import androidx.multidex.MultiDex
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho
import com.google.android.gms.ads.MobileAds
import com.webers.applock.di.component.DaggerAppComponent
import com.webers.applock.service.ServiceStarter
import com.webers.applock.service.worker.WorkerStarter
import com.raqun.beaverlib.Beaver
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
@SuppressWarnings("unchecked")
class AppLockerApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this, getString(R.string.mobile_ad_id))
        Stetho.initializeWithDefaults(this)
        Beaver.build(this)
        ServiceStarter.startService(this)
        SoLoader.init(this, false)
        WorkerStarter.startServiceCheckerWorker()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}