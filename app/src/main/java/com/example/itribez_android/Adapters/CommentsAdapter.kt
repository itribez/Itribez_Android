package com.example.itribez_android.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.R
import com.example.itribez_android.dataclasses.DataClassComments

class CommentsAdapter(var list: ArrayList<DataClassComments>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottomsheet_comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userItem = list[position]
        holder.imgViewProfileComments.setImageResource(userItem.imgprofileComment)
        holder.txtViewNameComment.text = userItem.nameComment
        holder.txtViewChatComment.text = userItem.commentChat
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewProfileComments = itemView.findViewById<AppCompatImageView>(R.id.imgProfileComment)
        var txtViewNameComment = itemView.findViewById<AppCompatTextView>(R.id.txtViewNameComment)
        var txtViewChatComment = itemView.findViewById<AppCompatTextView>(R.id.txtViewChatComment)
    }
}