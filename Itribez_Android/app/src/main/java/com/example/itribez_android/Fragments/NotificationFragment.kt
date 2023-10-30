package com.example.itribez_android.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.dataclasses.DataClassNotification
import com.example.itribez_android.R
import com.example.itribez_android.Adapters.NotificationAdapter

class NotificationFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    var arrayList = ArrayList<DataClassNotification>()
    lateinit var recycleradapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        recyclerView = view.findViewById(R.id.recyclerNotification)
        recycleradapter = NotificationAdapter(arrayList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = recycleradapter
            return view
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Ayushi Patel",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Indu Pandey",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Dhrumil Desai",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Vrunda Patel",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Sinthuvamasan",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Charvi Parhwak",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Brijal Patel",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Saumya Maurya",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Ayushi Patel",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Indu Pandey",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Dhrumil Desai",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Vrunda Patel",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Sinthuvamasan",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Charvi Parhwak",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Brijal Patel",
                "Started following you",
                "follow"
            )
        )
        arrayList.add(
            DataClassNotification(
                R.drawable.profile_img_temp,
                "Saumya Maurya",
                "Started following you",
                "follow"
            )
        )
    }
}