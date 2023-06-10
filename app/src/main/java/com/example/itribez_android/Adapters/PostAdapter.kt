package com.example.itribez_android.Adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.R
import com.example.itribez_android.dataclasses.DataClassPosts

class PostAdapter(var list: ArrayList<DataClassPosts>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
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
        holder.caption.text = userItem.caption
        holder.publisher.text = userItem.publisher
        holder.username.text = userItem.publisher

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postImage= itemView.findViewById<AppCompatImageView>(R.id.post_image_home)
//        var likeButton= itemView.findViewById<Button>(R.id.post_image_like_btn)
//        var saveButton= itemView.findViewById<Button>(R.id.post_save_comment_btn)
//        var commentButton= itemView.findViewById<Button>(R.id.post_image_comment_btn)
//        var likes=itemView.findViewById<TextView>(R.id.likes)
//        var comments=itemView.findViewById<TextView>(R.id.comments)
        var username=itemView.findViewById<TextView>(R.id.publisher_user_name_post)
        var publisher=itemView.findViewById<TextView>(R.id.publisher)
        var caption=itemView.findViewById<TextView>(R.id.caption)
    }
}