package com.example.itribez_android.Adapters


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

import com.example.itribez_android.MainActivity
import com.example.itribez_android.Models.Post
import com.example.itribez_android.R

import de.hdodenhof.circleimageview.CircleImageView


class PostAdapter
    (private val mContext:Context,private  val mPost:List<Post>):RecyclerView.Adapter<PostAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(mContext).inflate(R.layout.item_posts,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return  mPost.size
    }

    //code for events
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post=mPost[position]
        val postid=post.getPostId()
    }

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var profileImage:CircleImageView
        var postImage:ImageView
        var likeButton:ImageView
        var commentButton:ImageView
        var saveButton:ImageView
        var likes:TextView
        var comments:TextView
        var username:TextView
        var publisher:TextView
        var caption:TextView


        init {
            profileImage=itemView.findViewById(R.id.publisher_profile_image_post)
            postImage=itemView.findViewById(R.id.post_image_home)
            likeButton=itemView.findViewById(R.id.post_image_like_btn)
            saveButton=itemView.findViewById(R.id.post_save_comment_btn)
            commentButton=itemView.findViewById(R.id.post_image_comment_btn)
            likes=itemView.findViewById(R.id.likes)
            comments=itemView.findViewById(R.id.comments)
            username=itemView.findViewById(R.id.publisher_user_name_post)
            publisher=itemView.findViewById(R.id.publisher)
            caption=itemView.findViewById(R.id.caption)

        }

    }


}