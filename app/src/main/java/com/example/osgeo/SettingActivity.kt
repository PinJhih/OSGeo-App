package com.example.osgeo

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var settings: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE)
        if (settings.getBoolean("dark_mode", false))
            switch_dark_mode.isChecked = true
        setting()

        switch_dark_mode.setOnClickListener {
            val edit = settings.edit()
            edit.putBoolean("dark_mode", switch_dark_mode.isChecked)
            edit.commit()

            setting()
        }
    }

    private fun setting() {
        val backgroundColor: Int
        val textColor: Int

        if (settings.getBoolean("dark_mode", false)) {
            backgroundColor = Color.parseColor("#000000")
            textColor = Color.parseColor("#ffffff")
        } else {
            backgroundColor = Color.parseColor("#ffffff")
            textColor = Color.parseColor("#000000")
        }

        layout_setting.setBackgroundColor(backgroundColor)
        switch_dark_mode.setTextColor(textColor)
        tv_language.setTextColor(textColor)
    }
}
