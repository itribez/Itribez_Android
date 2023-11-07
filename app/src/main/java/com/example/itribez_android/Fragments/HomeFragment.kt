package com.example.itribez_android.Fragments


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.Adapters.PostAdapter
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.PostResponse
import com.example.itribez_android.R
import com.example.itribez_android.ViewModels.PostViewModel
import com.example.itribez_android.dataclasses.DataClassPosts

class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    var arrayList = ArrayList<PostResponse.PostItem>()
    lateinit var recycleradapter: PostAdapter
    private lateinit var postViewModel: PostViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_home)
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        recycleradapter = PostAdapter(arrayList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = recycleradapter
            postViewModel.getPosts()
            postViewModel.postResult.observe(viewLifecycleOwner, Observer {response ->
                when(response){
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
            return view
        }
    }
}