package com.example.itribez_android.Adapters

import com.example.itribez_android.Models.Post
import com.example.itribez_android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView



class MyProfilePostAdapter(private val mContext: Context, private  val mPost:List<Post>): RecyclerView.Adapter<MyProfilePostAdapter.ViewHolder>() {

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var postedImg: ImageView
        init
        {
            postedImg = itemView.findViewById(R.id.my_posted_picture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(mContext).inflate(R.layout.myprofile_post_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPost.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post=mPost[position]


        }
}