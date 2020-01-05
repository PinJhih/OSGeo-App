package com.example.osgeo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var n = 0
        tv_num.text = "$n"
        
        btn_add.setOnClickListener {
            n++
            tv_num.text = "$n"
        }
    }
}
