package com.example.itribez_android.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.dataclasses.DataClassNotification
import com.example.itribez_android.R

class NotificationAdapter(var list: ArrayList<DataClassNotification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userItem = list[position]
        holder.imgViewProfile.setImageResource(userItem.imgProfile)
        holder.txtViewName.text = userItem.name
        holder.txtViewInfo.text = userItem.info
        holder.btnFollow.text = userItem.follow
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewProfile = itemView.findViewById<AppCompatImageView>(R.id.imgProfile)
        var txtViewName = itemView.findViewById<AppCompatTextView>(R.id.txtViewName)
        var txtViewInfo = itemView.findViewById<AppCompatTextView>(R.id.txtViewInformation)
        var btnFollow = itemView.findViewById<TextView>(R.id.btnFollow)
    }
}