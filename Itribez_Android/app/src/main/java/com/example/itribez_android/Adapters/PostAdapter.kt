package com.example.itribez_android.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.OnLikeClickListener
import com.example.itribez_android.R
import com.example.itribez_android.dataclasses.DataClassPosts


class PostAdapter(var list: ArrayList<DataClassPosts>, private val userId:String, private val likeClickListener: OnLikeClickListener, private val context: Context) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    var selectedPostPosition: Int = -1
    var onItemClick : ((DataClassPosts) -> Unit)? = null
    var onCommentClick: ((String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_posts, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userItem = list[position]
        holder.postImage.setImageResource(userItem.postImage)
        holder.comments.setOnClickListener {
            onCommentClick?.invoke(userItem.postId)
            selectedPostPosition = position
        }
        holder.caption.text = userItem.caption
        holder.publisher.text = userItem.publisher
        holder.username.text = userItem.publisher
        holder.likeCount.text = userItem.likeCount.toString()
        holder.likebtn.isChecked = userItem.isLiked

        holder.likebtn.setOnCheckedChangeListener { buttonView, isChecked ->
            likeClickListener.onLikeClick(userItem, isChecked)
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(userItem)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postImage = itemView.findViewById<AppCompatImageView>(R.id.post_image_home)
        var username = itemView.findViewById<TextView>(R.id.publisher_user_name_post)
        var publisher = itemView.findViewById<TextView>(R.id.publisher)
        var caption = itemView.findViewById<TextView>(R.id.caption)
        var comments = itemView.findViewById<ImageView>(R.id.post_image_comment_btn)
        var likebtn = itemView.findViewById<CheckBox>(R.id.post_image_like_btn)
        var likeCount = itemView.findViewById<TextView>(R.id.likes)
    }
}