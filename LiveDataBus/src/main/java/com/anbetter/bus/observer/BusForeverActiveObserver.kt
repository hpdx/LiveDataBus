package com.anbetter.bus.observer

import androidx.lifecycle.BusLiveData
import androidx.lifecycle.Observer

/**
 *
 * <p>
 * Created by android_ls on 2021/1/9 15:09.
 *
 * @author android_ls
 * @version 1.0
 */
class BusForeverActiveObserver<T>(
    observer: Observer<in T>,
    liveData: BusLiveData<T>
) : BusObserver<T>(observer, liveData) {

}