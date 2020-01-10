package com.example.osgeo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val context: Context,
    private val projectInfo: ArrayList<ProjectInfo>
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val item = v.findViewById<ConstraintLayout>(R.id.layout_item)!!
        val name = v.findViewById<TextView>(R.id.tv_project_name)!!
        val tagline = v.findViewById<TextView>(R.id.tv_project_tagline)!!
        val logo = v.findViewById<ImageView>(R.id.img_project_logo)!!
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_projects, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = projectInfo.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.name.text = projectInfo[position].name
        holder.tagline.text = projectInfo[position].tagline
        holder.logo.setImageResource(projectInfo[position].logo_id)

        holder.item.setOnClickListener {
            (context as MainActivity).login(projectInfo[position].url)
        }
    }
}