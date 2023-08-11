package com.example.itribez_android.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itribez_android.Adapters.CommentsAdapter
import com.example.itribez_android.Adapters.PostAdapter
import com.example.itribez_android.Models.Post
import com.example.itribez_android.R
import com.example.itribez_android.dataclasses.DataClassComments
import com.example.itribez_android.dataclasses.DataClassPosts
import com.example.itribez_android.utils.Constant
import com.example.itribez_android.utils.Constant.arrayListPostMain
import com.google.android.material.bottomsheet.BottomSheetDialog

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
    ):View?{
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_home)
        recycleradapter = PostAdapter(arrayListPostMain)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = recycleradapter
            recycleradapter.onItemClick = {
                val dialog =BottomSheetDialog(context)
                val viewBottomSheet = layoutInflater.inflate(R.layout.bottomsheet_comment,null)
                dialog.setCancelable(true)
                dialog.setCanceledOnTouchOutside(true)
                dialog.setContentView(viewBottomSheet)
                val recyclerViewC = viewBottomSheet.findViewById<RecyclerView>(R.id.recyclerViewComments)
                recyclerViewC.apply{
                    val comment = CommentsAdapter(ArrayList<DataClassComments>().apply {
                        add(DataClassComments(R.drawable.profile_img_temp,"Indu Pandey","Nice Location"))
                        add(DataClassComments(R.drawable.profile_img_temp,"Indu Pandey","Nice Location"))
                        add(DataClassComments(R.drawable.profile_img_temp,"Ayushi Patel","Cool"))
                        add(DataClassComments(R.drawable.profile_img_temp,"Saumya maurya","Nice Location"))
                        add(DataClassComments(R.drawable.profile_img_temp,"Vrunda Patel","cool"))
                        add(DataClassComments(R.drawable.profile_img_temp,"Dhrumil desai","Nice Location"))
                        add(DataClassComments(R.drawable.profile_img_temp,"Sinthu Vamasan","Cool"))
                        add(DataClassComments(R.drawable.profile_img_temp,"Charvi Parhwak","Nice Location"))
                        add(DataClassComments(R.drawable.profile_img_temp,"Brijal Patel","Cool"))
                    })
                    adapter = comment
                }
                dialog.show()
            }
            return view
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arrayListPostMain.addAll(arrayList)
    }
}