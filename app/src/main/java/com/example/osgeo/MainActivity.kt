package com.example.osgeo

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter
    private lateinit var settings: SharedPreferences
    private lateinit var profile: SharedPreferences
    private var info = ArrayList<ProjectInfo>()
    private var isLogin = false
    private var darkMode = false

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        profile = getSharedPreferences("profile", Activity.MODE_PRIVATE)
        isLogin = profile.getBoolean("is_logged_in", false)
        darkMode = settings.getBoolean("dark_mode", false)

        adapter = RecyclerAdapter(this, darkMode, info)
        recyclerView.adapter = adapter
        setRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE)
        profile = getSharedPreferences("profile", Activity.MODE_PRIVATE)
        isLogin = profile.getBoolean("is_logged_in", false)
        darkMode = settings.getBoolean("dark_mode", false)


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        adapter = RecyclerAdapter(this, darkMode, info)
        recyclerView.adapter = adapter

        setRecyclerView()

        btn_seting.setOnClickListener {
            val i = Intent(this, SettingActivity::class.java)
            startActivityForResult(i, 1)
        }
    }

    private fun setRecyclerView() {

        val names = resources.obtainTypedArray(R.array.project_names)
        val tagline = resources.obtainTypedArray(R.array.project_tagline)
        val logos = resources.obtainTypedArray(R.array.project_logo_id)
        val url = resources.obtainTypedArray(R.array.project_url)
        info.clear()

        for (i in 0 until 5) {
            val projectInfo = ProjectInfo()
            projectInfo.name = names.getString(i)!!
            projectInfo.tagline = tagline.getString(i)!!
            projectInfo.logo_id = logos.getResourceId(i, 0)
            projectInfo.url = url.getString(i)!!
            info.add(projectInfo)

            adapter.notifyDataSetChanged()
        }

        names.recycle()
        tagline.recycle()
        logos.recycle()
        url.recycle()
    }

    fun login(url: String) {
        val intent = Intent(this, LoginActivity::class.java)
        val item = arrayOf("Github", "OSGeo Trac")
        if (isLogin)
            AlertDialog.Builder(this)
                .setTitle("View on the web?")
                .setPositiveButton("Yse") { _, _ ->
                    val i = Intent(this, ViewWebActivity::class.java)
                    i.putExtra("url", url)
                    startActivity(i)
                }
                .show()
        else
            AlertDialog.Builder(this)
                .setTitle("Login by...")
                .setItems(item) { _, i ->
                    intent.putExtra("login_by", item[i])
                    startActivityForResult(intent, 1)
                }
                .show()
    }
}

data class ProjectInfo(
    var name: String = "",
    var tagline: String = "",
    var logo_id: Int = 0,
    var url: String = ""
)
