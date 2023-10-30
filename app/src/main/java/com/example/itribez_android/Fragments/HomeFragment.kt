package com.example.itribez_android.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.Adapters.CommentsAdapter
import com.example.itribez_android.Adapters.PostAdapter
import com.example.itribez_android.Api.Methods.UserApi
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.OnLikeClickListener
import com.example.itribez_android.R
import com.example.itribez_android.ViewModels.CommentViewModel
import com.example.itribez_android.ViewModels.CreateCommentViewModel
import com.example.itribez_android.ViewModels.LikeViewModel
import com.example.itribez_android.ViewModels.UnlikeViewModel
import com.example.itribez_android.dataclasses.DataClassComments
import com.example.itribez_android.dataclasses.DataClassPosts
import com.example.itribez_android.utils.SessionManager
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : androidx.fragment.app.Fragment(), OnLikeClickListener {

    lateinit var recyclerView: RecyclerView
    var arrayList = ArrayList<DataClassPosts>()
    //lateinit var context: Context
    lateinit var recycleradapter: PostAdapter
    lateinit var commentViewModel : CommentViewModel
    lateinit var createCommentViewModel: CreateCommentViewModel
    private lateinit var commentAdapter: CommentsAdapter
    private lateinit var commentInput: EditText
    private lateinit var btnSendComment: ImageView
    private lateinit var userId : String
    var postId = "64b9d0c0e69fc1dcccd8fc3b"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userId = SessionManager.getUserId(requireContext()) ?: ""
        Log.d(TAG,"Userid : $userId")
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val userApi = UserApi.getApi()
        recyclerView = view.findViewById(R.id.recycler_view_home)
        recycleradapter = PostAdapter(arrayList, userId,this,requireContext())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            recyclerView.adapter = recycleradapter
        }
        recycleradapter.onCommentClick = { postId ->
            showBottomSheetForComments(postId)
        }
        commentAdapter = CommentsAdapter(ArrayList())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)

        userId = SessionManager.getUserId(requireContext()) ?: ""
        arrayList.add(
            DataClassPosts(postId,
                R.drawable.img1,
                "Looking for Accommodation.",
                ArrayList<DataClassComments>(),
                "Ayushi Patel",
                false,
                0
            )
        )
        arrayList.add(
            DataClassPosts("6510b73f825da39d0826e8ce",
                R.drawable.img5,
                "Travelling Kitchener",
                ArrayList<DataClassComments>(),
                "Indu Pandey",
                false,
                0
            )
        )
        arrayList.add(
            DataClassPosts(
                postId,
                R.drawable.img2,
                "Must visit this in Barrie",
                ArrayList<DataClassComments>(),
                "Dhrumil Desai",
                false,
                0
            )
        )
        arrayList.add(
            DataClassPosts(
                postId,
                R.drawable.img1,
                "Looking for Accommodation.",
                ArrayList<DataClassComments>(),
                "Vrunda Patel",
                false,
                0
            )
        )
        arrayList.add(
            DataClassPosts(
                postId,
                R.drawable.img5,
                "Must visit this in Barrie",
                ArrayList<DataClassComments>(),
                "Brijal Patel",
                false,
                0
            )
        )
        arrayList.add(
            DataClassPosts(
                postId,
                R.drawable.img,
                "Travelling Brampton",
                ArrayList<DataClassComments>(),
                "Sinthuvamasan",
                false,
                0
            )
        )
        arrayList.add(
            DataClassPosts(
                postId,
                R.drawable.img1,
                "Loooking for Accommodation",
                ArrayList<DataClassComments>(),
                "Saumya Maurya",
                false,
                0
            )
        )
        arrayList.add(
            DataClassPosts(
                postId,
                R.drawable.img5,
                "Must visit this in Toronto",
                ArrayList<DataClassComments>(),
                "Charvi Parhwak",
                false,
                0
            )
        )

    }

    private var likeInProgress = false
    override fun onLikeClick(post: DataClassPosts, liked: Boolean) {
        //val postId = "64b9d0c0e69fc1dcccd8fc3b"
        if (!likeInProgress) {  // Check if a like/unlike operation is not already in progress
            // Set the flag to true to indicate that an operation is in progress
            if (liked) {
                likeInProgress = true
                likePost("64b9d0c0e69fc1dcccd8fc3b")
            } else {
                unlikePost("64b9d0c0e69fc1dcccd8fc3b")
            }
        }
    }

    private fun likePost(postId: String) {
        //val postId = "64b9d0c0e69fc1dcccd8fc3b"
        val likeViewModel = ViewModelProvider(this).get(LikeViewModel::class.java)
        likeViewModel.likePost(userId)
        likeViewModel.likeResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is BaseResponse.Success -> {
                    // Handle like success
                    // Toast.makeText(context, "succeed to like the post", Toast.LENGTH_SHORT).show()
                    Log.d(TAG,"likepost called")
                    val likedPost = arrayList.find {it.postId == postId  }
                    if (likedPost != null) {
                        if (!likedPost.isLiked) {
                            // If the post was not liked previously, increment the likeCount
                            likedPost.likeCount++
                        }
                        likedPost.isLiked = true
                        val position = arrayList.indexOf(likedPost)
                        if (position != -1) {
                            recycleradapter.notifyItemChanged(position)
                        }
                    }
                    likeInProgress = false
                }
                is BaseResponse.Error -> {
                    // Handle like error
                    Log.e("ResponseError", "Code: ${response.hashCode()}, Message: ${response.hashCode()}")
                    Toast.makeText(context, "Failed to like the post", Toast.LENGTH_SHORT).show()
                    likeInProgress = false
                }
                is BaseResponse.Loading -> {
                    // Handle loading state if needed
                }

                else -> {

                }
            }
        })
    }

    private fun unlikePost(postId: String) {
        val unlikeViewModel = ViewModelProvider(this).get(UnlikeViewModel::class.java)
        unlikeViewModel.unlikePost(userId)
        unlikeViewModel.unlikeResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is BaseResponse.Success -> {
                    // Handle unlike success
                    val unlikedPost = arrayList.find { it.postId == postId }
                    if (unlikedPost != null && unlikedPost.isLiked) {
                        // If the post was liked previously, decrement the likeCount
                        unlikedPost.likeCount--
                    }
                    unlikedPost?.isLiked = false
                    val position = arrayList.indexOf(unlikedPost)
                    if (position != -1) {
                        recycleradapter.notifyItemChanged(position)
                    }
                    likeInProgress = false
                }
                is BaseResponse.Error -> {
                    // Handle unlike error
                    Toast.makeText(context, "Failed to unlike the post", Toast.LENGTH_SHORT).show()
                    likeInProgress = false
                }
                is BaseResponse.Loading -> {
                    // Handle loading state if needed
                }

                else -> {
                    Toast.makeText(context, "went in else", Toast.LENGTH_SHORT).show()
                }
            }
        })
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
//        btnSendComment.setOnClickListener {
//            val newCommentText = commentInput.text.toString()
//            if (newCommentText.isNotEmpty()) {
//                // Create a new DataClassComments object with the new comment
//                val newComment = DataClassComments(
//                    //R.drawable.profile_chat,  // Set the profile image as per your requirement
//                    "Vrunda Patel",  // Set the commenter's name as per your requirement
//                    newCommentText
//                )
//
//                commentAdapter.addComment(newComment)
//
//                // Update the adapter to reflect the changes
//                commentAdapter.notifyDataSetChanged()
//
//                // Clear the comment input
//                commentInput.text.clear()
//            } else {
//                Toast.makeText(context, "Please enter a comment", Toast.LENGTH_SHORT).show()
//            }
//        }
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
                            "Vrunda Patel",  // Set the commenter's name as per your requirement
                            createCommentResponse.content
                        )

//                        commentAdapter.addComment(newComment)
////
////                // Update the adapter to reflect the changes
//                        commentAdapter.notifyDataSetChanged()
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
        commentViewModel.getComments()

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
                                commentChat = commentItem.content
                            )
                        }

                        commentAdapter.updateComments(commentsList)
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


