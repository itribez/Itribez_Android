package com.example.itribez_android.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itribez_android.dataclasses.DataClassNotification
import com.example.itribez_android.R
import com.example.itribez_android.Adapters.NotificationAdapter
import com.example.itribez_android.Api.Methods.UserApi
import com.example.itribez_android.Api.Responses.NotificationResponseModel
import com.example.itribez_android.utils.SessionManager
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NotificationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    var arrayList = ArrayList<NotificationResponseModel>()
    lateinit var recyclerAdapter: NotificationAdapter
    lateinit var userApi: UserApi



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        recyclerView = view.findViewById(R.id.recyclerNotification)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerAdapter = NotificationAdapter(listOf()) // Initialize the adapter with an empty list
        recyclerView.adapter = recyclerAdapter
        val userId = SessionManager.getUserId(requireContext())
        userId?.let { fetchNotifications(it) }
        return view;
    }


    private fun fetchNotifications(userId: String) {
        val token = SessionManager.getToken(requireContext()) // Replace with your actual token
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://itribez-node-apis.onrender.com/notifications/$userId")
            .header("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("NotificationFragment", "Network error: ${e.message}")
                // Handle network error here
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("NotificationFragment", "Response successful: ${responseBody}")

                    val notificationResponseModel = Gson().fromJson(responseBody, NotificationResponseModel::class.java)

                    // Access first name and last name from the first notification if it exists


                    // Parse the JSON response here
                    activity?.runOnUiThread {
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.adapter = NotificationAdapter(notificationResponseModel.notifications)
                    }


                } else {
                    Log.d("NotificationFragment", "Request failed: ${response.code}")
                    // Handle failure here
                }
            }
        })
    }
}