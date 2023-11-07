package com.example.itribez_android.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.Api.Responses.NotificationResponseModel
import com.example.itribez_android.dataclasses.DataClassNotification
import com.example.itribez_android.R

class NotificationAdapter(var list: List<NotificationResponseModel.Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = list[position]

        holder.txtViewFirstName.text = "First Name: ${notification.sender.firstName}"
        holder.txtViewLastName.text = "Last Name: ${notification.sender.lastName}"
        holder.txtViewType.text = "Type: ${notification.type}"
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtViewType: TextView = itemView.findViewById(R.id.txtViewType)
        val txtViewFirstName: TextView = itemView.findViewById(R.id.txtViewFirstName)
        val txtViewLastName: TextView = itemView.findViewById(R.id.txtViewLastName)
    }
}