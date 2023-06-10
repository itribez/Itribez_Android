package com.example.itribez_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.R
import com.example.itribez_android.Adapters.MessageAdapter
import com.example.itribez_android.dataclasses.DataClassMessages

class MessagesFragment : Fragment() {

    lateinit var recyclerViewMsg: RecyclerView
    var arrayListMsg = ArrayList<DataClassMessages>()
    lateinit var recycleradapterMsg: MessageAdapter

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
        recyclerViewMsg = view.findViewById(R.id.recyclerMessages)
        recycleradapterMsg = MessageAdapter(arrayListMsg)
        recyclerViewMsg.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = recycleradapterMsg
            return view
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arrayListMsg.add(DataClassMessages(R.drawable.profile_img_temp,"Ayushi Patel","hey there!","20 minutes ago"))
        arrayListMsg.add(DataClassMessages(R.drawable.profile_img_temp,"Indu Pandey","hey there!","20 minutes ago"))
        arrayListMsg.add(DataClassMessages(R.drawable.profile_img_temp,"Dhrumil Desai","hey there!","20 minutes ago"))
        arrayListMsg.add(DataClassMessages(R.drawable.profile_img_temp,"Vrunda Patel","hey there!","20 minutes ago"))
        arrayListMsg.add(DataClassMessages(R.drawable.profile_img_temp,"Brijal Patel","hey there!","20 minutes ago"))
        arrayListMsg.add(DataClassMessages(R.drawable.profile_img_temp,"Sinthuvamasan","hey there!","20 minutes ago"))
        arrayListMsg.add(DataClassMessages(R.drawable.profile_img_temp,"Saumya Maurya","hey there!","20 minutes ago"))
        arrayListMsg.add(DataClassMessages(R.drawable.profile_img_temp,"Charvi Parhwak","hey there!","20 minutes ago"))

    }
}