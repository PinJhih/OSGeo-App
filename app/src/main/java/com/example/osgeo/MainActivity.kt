package com.example.osgeo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var info = ArrayList<ProjectInfo>()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        for (i in 0 until 5) {
            val projectInfo = ProjectInfo()
            projectInfo.name = names.getString(i)!!
            projectInfo.tagline = tagline.getString(i)!!
            projectInfo.logo_id = logos.getResourceId(i, 0)
            info.add(projectInfo)

            adapter.notifyDataSetChanged()
        }

        names.recycle()
        tagline.recycle()
        logos.recycle()
    }

    fun login() {
        val intent = Intent(this, LoginActivity::class.java)
        val item = arrayOf("Github", "OSGeo Trac")
        AlertDialog.Builder(this)
            .setTitle("Login by...")
            .setItems(item) { _, i ->
                intent.putExtra("login_by", item[i])
                startActivity(intent)
            }
            .show()
    }
}


data class ProjectInfo(
    var name: String = "",
    var tagline: String = "",
    var logo_id: Int = 0
)