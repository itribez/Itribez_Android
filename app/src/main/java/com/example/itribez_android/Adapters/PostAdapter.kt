package com.example.itribez_android.Adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.Api.Responses.PostResponse
import com.example.itribez_android.OnLikeClickListener
import com.example.itribez_android.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PostAdapter(var list: ArrayList<PostResponse.PostItem>,private val likeClickListener: OnLikeClickListener) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    var onItemClick: ((PostResponse.PostItem) -> Unit)? = null
    var selectedPostPosition: Int = -1
    var onCommentClick: ((PostResponse.PostItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_posts, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userItem = list[position]
        holder.bind(userItem)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postImage = itemView.findViewById<AppCompatImageView>(R.id.post_image_home)
        var username = itemView.findViewById<TextView>(R.id.publisher_user_name_post)
        var publisher = itemView.findViewById<TextView>(R.id.publisher)
        var caption = itemView.findViewById<TextView>(R.id.caption)
        var comments = itemView.findViewById<ImageView>(R.id.post_image_comment_btn)
            var likebtn = itemView.findViewById<CheckBox>(R.id.post_image_like_btn)
        var likeCount = itemView.findViewById<TextView>(R.id.likes)
        @SuppressLint("SuspiciousIndentation")
        fun bind(postItem: PostResponse.PostItem) {
            username.text = postItem.user
            caption.text = postItem.content
            comments.setOnClickListener {
                onCommentClick?.invoke(postItem)
                selectedPostPosition = position
            }
            likeCount.text = postItem.likeCount.toString()
            likebtn.isChecked = postItem.isLiked == true

            likebtn.setOnCheckedChangeListener { buttonView, isChecked ->
                likeClickListener.onLikeClick(postItem, isChecked)
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(postItem)
            }
            // Load and display the image from the photo URL using Picasso
            if (postItem.photo == null) {
                val imageUrl = "https://res.cloudinary.com/dzv1vpd2v/image/upload/v1699369631/onslhw0ct4uxcfoxcqzs.jpg"
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder) // Use a placeholder image
//                    .error(R.drawable.error_image) // Use an error image or any other error handling
                    .into(postImage, object : Callback {
                        override fun onSuccess() {
                            Log.d("Image","Images is getting")
                        }

                        override fun onError(e: Exception?) {
                            Log.d("error","Image not get ${e?.message}")
//                            postImage.setImageResource(R.drawable.error_image_placeholder)
                        }
                    })
            } else {
                // Handle the case where there is no photo URL
                // You can hide the ImageView or set a default image
                postImage.visibility = View.GONE
            }
        }
    }
}