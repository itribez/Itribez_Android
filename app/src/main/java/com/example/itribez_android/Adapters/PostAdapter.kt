package com.example.itribez_android.Adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.Api.Responses.PostResponse
import com.example.itribez_android.R
import com.example.itribez_android.dataclasses.DataClassPosts
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import okhttp3.internal.immutableListOf
import kotlin.coroutines.coroutineContext

class PostAdapter(var list: ArrayList<PostResponse.PostItem>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    var onItemClick: ((PostResponse) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_posts, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userItem = list[position]
        //  holder.postImage.setImageResource(userItem.postImage)
        // holder.caption.text = userItem.data[0].content
        /*holder.postImage.setImageResource(R.drawable.img5)
        holder.caption.text = userItem.caption
        holder.publisher.text = userItem.publisher
        holder.username.text = userItem.publisher
        //
        holder.comments.setOnClickListener {
            onItemClick?.invoke(userItem)
        }*/
        holder.bind(userItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllNew(arrayListOf: java.util.ArrayList<PostResponse.PostItem>) {
        list.clear()
        list.addAll(arrayListOf)
        //notifyItemRangeInserted(list.size - arrayListOf.size, arrayListOf.size)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postImage = itemView.findViewById<AppCompatImageView>(R.id.post_image_home)

        /*
                var likeButton= itemView.findViewById<Button>(R.id.post_image_like_btn)
                var saveButton= itemView.findViewById<Button>(R.id.post_save_comment_btn)
                var commentButton= itemView.findViewById<Button>(R.id.post_image_comment_btn)
                var likes=itemView.findViewById<TextView>(R.id.likes)
                var comments=itemView.findViewById<TextView>(R.id.comments)
        */
        var username = itemView.findViewById<TextView>(R.id.publisher_user_name_post)
        var publisher = itemView.findViewById<TextView>(R.id.publisher)
        var caption = itemView.findViewById<TextView>(R.id.caption)
        var comments = itemView.findViewById<ImageView>(R.id.post_image_comment_btn)
        @SuppressLint("SuspiciousIndentation")
        fun bind(postItem: PostResponse.PostItem) {
            //  val postItem = PostResponse.PostItem
            username.text = postItem.user
            // publisher.text = postItem.user
            caption.text = postItem.content

            // Load and display the image from the photo URL using Picasso
            if (postItem.photo != null) {
                Picasso.get()
                    .load(postItem.photo)
                    .placeholder(R.drawable.placeholder) // Use a placeholder image
                    //.error(R.drawable.error_image) // Use an error image
                    .into(postImage)
            } else {
                // Handle the case where there is no photo URL
                // You can hide the ImageView or set a default image
                postImage.visibility = View.GONE
            }
        }
    }
}