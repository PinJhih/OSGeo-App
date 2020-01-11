package com.example.osgeo

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var settings: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE)
        val edit = settings.edit()

        //set view
        if (settings.getBoolean("dark_mode", false))
            switch_dark_mode.isChecked = true
        setting(
            settings.getBoolean("dark_mode", false),
            settings.getString("language", "English")!!
        )

        //set spinner
        val item = arrayOf("English", "繁體中文(臺灣)")
        val spinnerAdapter =
            android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, item)
        spinner.adapter = spinnerAdapter

        switch_dark_mode.setOnClickListener {
            edit.putBoolean("dark_mode", switch_dark_mode.isChecked)
            edit.commit()

            setting(
                settings.getBoolean("dark_mode", false),
                settings.getString("language", "English")!!
            )
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                edit.putString("language", item[position])
                edit.commit()
                setting(
                    settings.getBoolean("dark_mode", false),
                    settings.getString("language", "English")!!
                )
            }
        }
    }

    private fun setting(darkMode: Boolean, language: String) {
        val backgroundColor: Int
        val textColor: Int

        if (darkMode) {
            backgroundColor = Color.parseColor("#000000")
            textColor = Color.parseColor("#ffffff")
        } else {
            backgroundColor = Color.parseColor("#ffffff")
            textColor = Color.parseColor("#000000")
        }

        layout_setting.setBackgroundColor(backgroundColor)
        switch_dark_mode.setTextColor(textColor)
        tv_language.setTextColor(textColor)

        if (language == "English") {
            tv_language.text = resources.getString(R.string.language_english)
            switch_dark_mode.text = resources.getString(R.string.dark_mode_english)
        } else {
            tv_language.text = resources.getString(R.string.language_chinese_tw)
            switch_dark_mode.text = resources.getString(R.string.dark_mode_chinese_tw)
        }
    }
}
