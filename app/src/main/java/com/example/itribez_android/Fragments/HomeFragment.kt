package com.example.itribez_android.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.Adapters.PostAdapter
import com.example.itribez_android.Models.Post
import com.example.itribez_android.R

class HomeFragment: Fragment() {

    private var postAdapter: PostAdapter?=null
    private var postList:MutableList<Post>?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        var recyclerView: RecyclerView?=null
        recyclerView=view.findViewById(R.id.recycler_view_home)
        val linearlayoutManager= LinearLayoutManager(context)
        linearlayoutManager.reverseLayout=true
        //New posts at top
        linearlayoutManager.stackFromEnd=true
        recyclerView.layoutManager=linearlayoutManager
        //For Posts
        postList=ArrayList()
        postAdapter=context?.let { PostAdapter(it,postList as ArrayList<Post>) }
        recyclerView.adapter=postAdapter
        return view
    }
}