package com.webers.applock.ui.callblocker.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webers.applock.repository.CallBlockerRepository
import com.webers.applock.ui.RxAwareViewModel
import com.webers.applock.util.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CallLogViewModel @Inject constructor(val callBlockerRepository: CallBlockerRepository) :
    RxAwareViewModel() {

    private val callLogsViewStateLiveData = MutableLiveData<CallLogViewState>()

    init {
        callLogsViewStateLiveData.value = CallLogViewState.empty()

        disposables += callBlockerRepository.getCallLogs()
            .map {
                val itemViewStateList = arrayListOf<CallLogItemViewState>()
                it.forEach { itemViewStateList.add(CallLogItemViewState(it)) }
                CallLogViewState(itemViewStateList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                callLogsViewStateLiveData.value = it
            }
    }

    fun getViewStateLiveData(): LiveData<CallLogViewState> = callLogsViewStateLiveData

}