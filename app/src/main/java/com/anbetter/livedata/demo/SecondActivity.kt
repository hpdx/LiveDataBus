package com.anbetter.livedata.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.anbetter.bus.LiveDataBus

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        LiveDataBus.get<String>(Events.POST_LIKE).observe(this, {
            Log.i("XLog", "--SecondActivity-->result = $it")
        })

        findViewById<Button>(R.id.btn_send).setOnClickListener {
            LiveDataBus.get<String>(Events.POST_LIKE).postValue("小汽车，呵呵")
        }

    }

}