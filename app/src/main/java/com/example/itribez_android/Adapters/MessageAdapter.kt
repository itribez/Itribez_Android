package com.example.itribez_android.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.R
import com.example.itribez_android.dataclasses.DataClassMessages
import com.example.itribez_android.dataclasses.DataClassPosts

class MessageAdapter(var list: ArrayList<DataClassMessages>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_messages, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userItem = list[position]
        holder.imgViewProfileMsg.setImageResource(userItem.imgProfileMsg)
        holder.txtViewNameMsg.text = userItem.nameMsg
        holder.txtViewChat.text = userItem.chat
        holder.txtViewTime.text = userItem.time
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewProfileMsg = itemView.findViewById<AppCompatImageView>(R.id.imgProfileMsg)
        var txtViewNameMsg = itemView.findViewById<AppCompatTextView>(R.id.txtViewNameMsg)
        var txtViewChat = itemView.findViewById<AppCompatTextView>(R.id.txtViewChat)
        var txtViewTime = itemView.findViewById<TextView>(R.id.txtViewTime)
    }
}