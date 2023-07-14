package com.example.itribez_android.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.itribez_android.Models.Post
import com.example.itribez_android.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileFragment : Fragment() {
    private lateinit var profileId: String
    lateinit var setting:ImageView
    var postList:List<Post>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setting = view.findViewById(R.id.options_view)
        setting.setOnClickListener {
            val dialog = context?.let { it1 -> BottomSheetDialog(it1) }
            val viewBottomSheet = layoutInflater.inflate(R.layout.bottomsheet_settings,null)
            dialog?.setCancelable(true)
            dialog?.setCanceledOnTouchOutside(true)
            dialog?.setContentView(viewBottomSheet)
            dialog?.show()
            //Log.d(TAG, "onCreateView: Hello")
        }
        return view
    }
}