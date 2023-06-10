package com.example.itribez_android.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.Adapters.PostAdapter
import com.example.itribez_android.R
import com.example.itribez_android.dataclasses.DataClassPosts

class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    var arrayList = ArrayList<DataClassPosts>()
    lateinit var recycleradapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_messages, container, false)
        recyclerView = view.findViewById(R.id.recyclerMessages)
        recycleradapter = PostAdapter(arrayList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = recycleradapter
            return view
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arrayList.add(DataClassPosts(R.drawable.landscape,"Caption cool","hey there!","Ayushi Patel"))
        arrayList.add(DataClassPosts(R.drawable.landscape,"Summers caption","hey there!","Indu Pandey"))
        arrayList.add(DataClassPosts(R.drawable.landscape,"Travel caption","hey there!","Dhrumil Desai"))
        arrayList.add(DataClassPosts(R.drawable.landscape,"caption coffee","hey there!","Vrunda Patel"))
        arrayList.add(DataClassPosts(R.drawable.landscape,"Winters caption","hey there!","Brijal Patel"))
        arrayList.add(DataClassPosts(R.drawable.landscape,"family caption","hey there!","Sinthuvamasan"))
        arrayList.add(DataClassPosts(R.drawable.landscape,"cation pet","hey there!","Saumya Maurya"))
        arrayList.add(DataClassPosts(R.drawable.landscape,"food caption","hey there!","Charvi Parhwak"))

    }
}