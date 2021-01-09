package com.anbetter.livedata.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.anbetter.bus.LiveDataBus


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LiveDataBus.get<String>(Events.POST_LIKE).observe(this, {
            Log.i("XLog", "result = $it")
        })

        findViewById<Button>(R.id.btn_send).setOnClickListener {
            LiveDataBus.get<String>(Events.POST_LIKE).postValue("I Love You")
        }


        findViewById<Button>(R.id.btn_observe).setOnClickListener {


        }

        findViewById<Button>(R.id.btn_remove_observe).setOnClickListener {


        }






        findViewById<Button>(R.id.btn_next).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

    }
}