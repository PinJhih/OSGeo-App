package com.example.osgeo

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val settings = getSharedPreferences("settings", Activity.MODE_PRIVATE)

        if(settings.getBoolean("dark_mode",false)) {
            layout_login.setBackgroundColor(Color.parseColor("#000000"))
            ed_email.setHintTextColor(Color.parseColor("#ffffff"))
            ed_password.setHintTextColor(Color.parseColor("#ffffff"))
        }


        intent?.extras?.let {
            if (it.getString("login_by") == "Github")
                img_logo.setImageResource(R.drawable.github_logo)
            else
                img_logo.setImageResource(R.drawable.icon)
        }

        btn_login.setOnClickListener {
            if (ed_email.text.isEmpty())
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            else if (ed_password.text.isEmpty())
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            else {
                val profile = getSharedPreferences("profile", Activity.MODE_PRIVATE).edit()
                profile.putBoolean("is_logged_in", true)

                profile.commit()

                Thread(Runnable {
                    Thread.sleep(1000)
                    finish()
                }).start()
            }
        }
    }
}
