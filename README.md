### LiveDataBus
消息总线：
1.实现组件间消息传递，具有唯一的可信事件源
2.以Int类型的key区分每一个事件，消息类型可以是任意Object
3.具有生命周期感知能力，不用手动注册和反注册
4.粘性、非粘性事件都需要支持


#### 事件订阅（非粘性）
```
LiveDataBus.get<String>(Events.POST_LIKE).observe(this, {
            Log.i("XLog", "result = $it")
        })
```

#### 事件订阅（粘性）
```
LiveDataBus.get<String>(Events.POST_LIKE).observeSticky(this, {
            Log.i("XLog", "result = $it")
        })
```

#### 发送消息
```
LiveDataBus.get<String>(Events.POST_LIKE).postValue("I Love You")
```


#### 支持任意类型的Object
```
 LiveDataBus.get<UserInfo>(Events.USER_DATA).observe(this, {
            Log.i("XLog", "result = $it")
        })

 val userInfo = UserInfo("李斯", 32)
 LiveDataBus.get<UserInfo>(Events.USER_DATA).postValue(userInfo)
```


#### 非粘性消息的订阅、取消订阅和发送
```
val observerResult = Observer<String> {
    Log.i("XLog", "result = $it")
}

// 订阅
LiveDataBus.get<String>(Events.POST_LIKE)
           .observe(this, observerResult)

// 发送消息
LiveDataBus.get<String>(Events.POST_LIKE).postValue("I Love You")

// 取消订阅（一般不用手动调用，支持感知组件的生命周期，在onDestroy()时自动取消订阅）
LiveDataBus.get<String>(Events.POST_LIKE)
           .removeObserver(observerResult)
```


#### 取消当前组件所有订阅消息
```
LiveDataBus.get<String>(Events.POST_LIKE)
           .removeObservers(this) // this->LifecycleOwner
```
