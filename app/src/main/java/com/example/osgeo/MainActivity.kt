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
    private lateinit var profile:SharedPreferences
    private var info = ArrayList<ProjectInfo>()
    private var isLoggedin = false

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        profile = getSharedPreferences("profile", Activity.MODE_PRIVATE)
        isLoggedin = profile.getBoolean("is_logged_in", true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profile = getSharedPreferences("profile", Activity.MODE_PRIVATE)
        isLoggedin = profile.getBoolean("is_logged_in", false)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        adapter = RecyclerAdapter(this, info)
        recyclerView.adapter = adapter

        setRecyclerView()
    }

    private fun setRecyclerView() {

        val names = resources.obtainTypedArray(R.array.project_names)
        val tagline = resources.obtainTypedArray(R.array.project_tagline)
        val logos = resources.obtainTypedArray(R.array.project_logo_id)
        val url = resources.obtainTypedArray(R.array.project_url)

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
        if (isLoggedin)
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
                    startActivityForResult(intent,1)
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