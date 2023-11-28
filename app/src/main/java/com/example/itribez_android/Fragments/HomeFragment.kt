

package com.example.itribez_android.Fragments


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.Adapters.CommentsAdapter
import com.example.itribez_android.Adapters.PostAdapter
import com.example.itribez_android.Api.Methods.UserApi
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.LikeResponse
import com.example.itribez_android.Api.Responses.PostResponse
import com.example.itribez_android.Api.Responses.UnlikeResponse
import com.example.itribez_android.OnLikeClickListener
import com.example.itribez_android.R
import com.example.itribez_android.ViewModels.CommentViewModel
import com.example.itribez_android.ViewModels.CreateCommentViewModel
import com.example.itribez_android.ViewModels.LikeViewModel
import com.example.itribez_android.ViewModels.PostViewModel
import com.example.itribez_android.ViewModels.UnlikeViewModel
import com.example.itribez_android.dataclasses.DataClassComments
import com.example.itribez_android.dataclasses.DataClassPosts
import com.example.itribez_android.utils.SessionManager
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : Fragment(), OnLikeClickListener {
    lateinit var recyclerView: RecyclerView
    var arrayList = ArrayList<PostResponse.PostItem>()
    lateinit var recycleradapter: PostAdapter
    private lateinit var postViewModel: PostViewModel
    lateinit var commentViewModel: CommentViewModel
    lateinit var createCommentViewModel: CreateCommentViewModel
    private lateinit var commentAdapter: CommentsAdapter
    private lateinit var commentInput: EditText
    private lateinit var btnSendComment: ImageView
    private lateinit var userId: String
    //var postId = "6510b73f825da39d0826e8ce"

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        userId = SessionManager.getUserId(requireContext()) ?: ""
        val userApi = UserApi.getApi()
        val imageBase64 = arguments?.getString("imageBase64")

        recyclerView = view.findViewById(R.id.recycler_view_home)
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        recycleradapter = PostAdapter(arrayList, this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = recycleradapter
            postViewModel.getPosts()
            postViewModel.postResult.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is BaseResponse.Success -> {
                        response.data?.data?.let {
                            Log.d(TAG, "onCreateView: ${response.data}")
                            arrayList.clear()
                            arrayList.addAll(it)
                            recycleradapter.notifyDataSetChanged()
                        }
                    }

                    is BaseResponse.Error -> {
                        Log.d(TAG, "went into error")
                        Toast.makeText(context, "Failed to fetch posts", Toast.LENGTH_SHORT).show()
                    }

                    is BaseResponse.Loading -> {
                        Log.d(TAG, "Loading posts....")
                    }
                }
            })
            recycleradapter.onCommentClick = { postItem ->
                showBottomSheetForComments(postItem._id!!)
            }
            commentAdapter = CommentsAdapter(ArrayList())

            return view
        }

    }


    override fun onLikeClick(post: PostResponse.PostItem, liked: Boolean) {
        if (!likeInProgress) {
            if (liked) {
                likeInProgress = true
                likePost(post._id!!)
            } else {
                unlikePost(post._id!!)
            }
        }
    }

    private var likeInProgress = false

    private fun unlikePost(postId: String) {
        // Call the unlike API using ViewModel
        likeInProgress = true  // Set the flag to true to indicate that an operation is in progress
        val unlikeViewModel = ViewModelProvider(this).get(UnlikeViewModel::class.java)
        unlikeViewModel.unlikePost(userId, postId)
        unlikeViewModel.unlikeResult.observe(viewLifecycleOwner, Observer { response ->
            handleUnlikeResponse(response as BaseResponse<UnlikeResponse>, postId)
        })
    }

    private fun likePost(postId: String) {
        // Call the like API using ViewModel
        likeInProgress = true  // Set the flag to true to indicate that an operation is in progress
        val likeViewModel = ViewModelProvider(this).get(LikeViewModel::class.java)
        likeViewModel.likePost(userId, postId)
        likeViewModel.likeResult.observe(viewLifecycleOwner, Observer { response ->
            handleLikeResponse(response as BaseResponse<LikeResponse>, postId)
        })
    }

    private fun handleUnlikeResponse(response: BaseResponse<UnlikeResponse>, postId: String) {
        when (response) {
            is BaseResponse.Success -> {
                // Handle unlike success
                val unlikedPost = arrayList.find { it._id == postId }
                if (unlikedPost != null && unlikedPost.isLiked == true) {
                    // If the post was liked previously, decrement the likeCount
                    unlikedPost.likeCount = unlikedPost.likeCount - 1
                }
                unlikedPost?.isLiked = false
                val position = arrayList.indexOf(unlikedPost)
                if (position != -1) {
                    recycleradapter.notifyItemChanged(position)
                    recycleradapter.notifyDataSetChanged()
                }
                // likeInProgress = false
            }
            is BaseResponse.Error -> {
                // Handle unlike error
                Toast.makeText(context, "Failed to unlike the post", Toast.LENGTH_SHORT).show()
                // likeInProgress = false
            }
            is BaseResponse.Loading -> {
                // Handle loading state if needed
            }
            else -> {
                Toast.makeText(context, "Unexpected response", Toast.LENGTH_SHORT).show()
            }
        }
        likeInProgress = false
    }

    private fun handleLikeResponse(response: BaseResponse<LikeResponse>, postId: String) {
        when (response) {
            is BaseResponse.Success -> {
                // Handle like success
                val likedPost = arrayList.find { it._id == postId }
                if (likedPost != null) {
                    if (!likedPost.isLiked!!) {
                        // If the post was not liked previously, increment the likeCount
                        likedPost.likeCount = likedPost.likeCount + 1
                    }
                    likedPost.isLiked = true
                    val position = arrayList.indexOf(likedPost)
                    if (position != -1) {
                        recycleradapter.notifyItemChanged(position)
                        recycleradapter.notifyDataSetChanged()
                    }
                }
                //  likeInProgress = false
            }
            is BaseResponse.Error -> {
                // Handle like error
                Toast.makeText(context, "Failed to like the post", Toast.LENGTH_SHORT).show()
                //likeInProgress = false
            }
            is BaseResponse.Loading -> {
                // Handle loading state if needed
            }
            else -> {
                Toast.makeText(context, "Unexpected response", Toast.LENGTH_SHORT).show()
            }
        }
        likeInProgress = false
    }
    private fun showBottomSheetForComments(postId: String) {
        val dialog = BottomSheetDialog(requireContext())
        val viewBottomSheet = layoutInflater.inflate(R.layout.bottomsheet_comment, null)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(viewBottomSheet)
        val recyclerViewC = viewBottomSheet.findViewById<RecyclerView>(R.id.recyclerViewComments)
        commentAdapter = CommentsAdapter(ArrayList())
        recyclerViewC.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
        commentInput = viewBottomSheet.findViewById(R.id.editTextComment)
        btnSendComment = viewBottomSheet.findViewById(R.id.btnSendComment)
        createCommentViewModel = ViewModelProvider(this).get(CreateCommentViewModel::class.java)
        btnSendComment.setOnClickListener {
            val newCommentText = commentInput.text.toString()
            Log.d(TAG, "Comment Text: $newCommentText")
            if (newCommentText.isNotEmpty()) {
                // Call the API to create a comment
                createCommentViewModel.createComment(postId, userId, newCommentText)
            } else {
                Toast.makeText(context, "Please enter a comment", Toast.LENGTH_SHORT).show()
            }
        }
        createCommentViewModel.createCommentResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is BaseResponse.Success -> {
                    // Handle create comment success
                    response.data?.let { createCommentResponse ->
                        // Assuming you have a way to resolve imgprofileComment. If not, use a placeholder.
                        val newComment = DataClassComments(
                            createCommentResponse.fullName,  // Set the commenter's name as per your requirement
                            createCommentResponse.content)

                        commentAdapter.addComment(newComment)
                        // Update the adapter to reflect the changes
                        commentAdapter.notifyDataSetChanged()
//
//                // Clear the comment input
                        commentInput.text.clear()
                    }
                }
                is BaseResponse.Error -> {
                    // Handle create comment error
                    Toast.makeText(context, "Failed to create a comment", Toast.LENGTH_SHORT).show()
                }
                is BaseResponse.Loading -> {
                    // Handle loading state if needed
                }
            }
        })

        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        commentViewModel.getComments(postId)

        commentViewModel.commentResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is BaseResponse.Success -> {
                    response.data?.let { commentResponse ->
                        Log.d(TAG, "Comments Loaded Successfully: ${commentResponse.data}")

                        val commentsList: List<DataClassComments> = commentResponse.data.map { commentItem ->
                            DataClassComments(
                                // Assuming you have a way to resolve imgprofileComment. If not, use a placeholder.
                                //imgprofileComment = /* Your way to resolve imgprofileComment */,
                                nameComment = "${commentItem.user.firstName} ${commentItem.user.lastName}",
                                commentChat = commentItem.content)
                        }
                        val newComments = commentsList.filter { newComment ->
                            !commentAdapter.containsComment(newComment)
                        }

                        commentAdapter.updateComments(newComments)
                        commentAdapter.notifyDataSetChanged()
                    }
                }
                is BaseResponse.Error -> {
                    Log.d(TAG, "went into error")
                    Toast.makeText(context, "Failed to fetch comments", Toast.LENGTH_SHORT).show()
                }
                is BaseResponse.Loading -> {
                    // Handle loading state if needed
                }
            }
        })
        dialog.show()
    }

}
