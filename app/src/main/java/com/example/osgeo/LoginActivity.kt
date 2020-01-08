package com.example.osgeo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        intent?.extras?.let{
            if(it.getString("login_by") == "Github")
                img_logo.setImageResource(R.drawable.github_logo)
            else
                img_logo.setImageResource(R.drawable.icon)
        }
    }
}
