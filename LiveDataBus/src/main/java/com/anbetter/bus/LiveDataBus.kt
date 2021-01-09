package com.anbetter.bus

import androidx.lifecycle.BusLiveData

/**
 * 直接使用LiveData存在的问题：
 * 1、订阅者会收到订阅之前发布的消息
 * 2、有可能会丢失事件，如果在多线程中同一个时刻，多次调用了postValue()方法，只有最后次调用的值会得到更新。
 *
 * 消息总线：
 * 1、实现组件间消息传递，具有唯一的可信事件源
 * 2、以Int类型的key区分每一个事件，消息类型可以是任意Object
 * 3、具有生命周期感知能力，不用手动注册和反注册
 * 4、粘性、非粘性事件都需要支持
 *
 * <p>
 * Created by android_ls on 2021/1/9 11:14.
 *
 * @author android_ls
 * @version 1.0
 */
object LiveDataBus {

    private val liveDataMap by lazy { mutableMapOf<Int, BusLiveData<*>>() }

    @Synchronized
    fun <T> get(key: Int): BusLiveData<T> {
        return liveDataMap.getOrPut(key) {
            BusLiveData<T>(key)
        } as BusLiveData<T>
    }

}