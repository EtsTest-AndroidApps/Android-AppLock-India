package com.webers.applock.ui.settings

import com.webers.applock.data.AppData
import com.webers.applock.data.database.lockedapps.LockedAppEntity
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class IsAllAppsLockedStateCreator : BiFunction<List<AppData>, List<LockedAppEntity>, Boolean> {

    override fun apply(installedApps: List<AppData>, lockedApps: List<LockedAppEntity>): Boolean {
        return installedApps.size == lockedApps.size
    }

    companion object {

        fun create(
            appDataListObservable: Observable<List<AppData>>,
            lockedAppsObservable: Observable<List<LockedAppEntity>>
        ): Observable<Boolean> {
            return Observable.combineLatest(
                appDataListObservable,
                lockedAppsObservable,
                IsAllAppsLockedStateCreator()
            )
        }
    }

}