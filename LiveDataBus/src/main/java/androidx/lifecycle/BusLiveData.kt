package androidx.lifecycle

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import com.anbetter.bus.observer.BusForeverActiveObserver
import com.anbetter.bus.observer.BusLifecycleObserver
import com.anbetter.bus.observer.BusObserver

/**
 *
 * <p>
 * Created by android_ls on 2021/1/9 11:18.
 *
 * @author android_ls
 * @version 1.0
 */
class BusLiveData<T>(private val key: Int) : MutableLiveData<T>() {

    private val mObservers = mutableMapOf<Observer<in T>, BusObserver<T>>()
    private val mHandler = Handler(Looper.getMainLooper())

    /**
     * 订阅
     */
    @MainThread
    override fun observe(
        @androidx.annotation.NonNull owner: LifecycleOwner,
        @androidx.annotation.NonNull observer: Observer<in T>
    ) {
        val observerWrapper = mObservers.getOrPut(observer) {
            BusLifecycleObserver(owner, observer, this).apply {
                mObservers[observer] = this
                owner.lifecycle.addObserver(this)
            }
        }
        super.observe(owner, observerWrapper)
    }

    /**
     * 订阅（粘性的消息）
     */
    @MainThread
    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, observer)
    }

    /**
     * 订阅被观察者，不受组件生命周期影响
     */
    @MainThread
    fun observeStickyForever(observer: Observer<T>) {
        super.observeForever(observer)
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        super.observeForever(observer)
        val observerWrapper = mObservers.getOrPut(observer ,{
            BusForeverActiveObserver(observer,this).apply {
                mObservers[observer] = this
            }
        })
        super.observeForever(observerWrapper)
    }

    /**
     * 取消订阅（粘性、非粘性）
     */
    @MainThread
    override fun removeObserver(@androidx.annotation.NonNull observer: Observer<in T>) {
        val observerWrapper: Observer<in T> = mObservers.remove(observer) ?: observer
        super.removeObserver(observerWrapper)
    }

    @MainThread
    override fun removeObservers(owner: LifecycleOwner) {
        mObservers.iterator().forEach {
            if (it.value.isAttachedTo(owner)) {
                mObservers.remove(it.key)
            }
        }
        super.removeObservers(owner)
    }

    override fun postValue(value: T) {
        mHandler.post {
            setValue(value)
        }
    }

    @MainThread
    override fun onInactive() {
        super.onInactive()
        if (!hasObservers()) {
            mObservers.remove(key)
        }
    }

    public override fun getVersion(): Int {
        return super.getVersion()
    }

}