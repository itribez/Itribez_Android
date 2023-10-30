package com.example.itribez_android

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.itribez_android.Api.Responses.BaseResponse
import com.example.itribez_android.Api.Responses.RegisterResponse
import com.example.itribez_android.ViewModels.RegisterViewModel
import com.example.itribez_android.utils.SessionManager
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var editTextFirstName: TextInputEditText
    lateinit var editTextLastName: TextInputEditText
    lateinit var editTextSignUpEmail: TextInputEditText
    lateinit var editTextSignUpPassword: TextInputEditText
    lateinit var btnSignUp: Button
    lateinit var txtViewSignIn: TextView
    lateinit var prgbarregister:ProgressBar
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextSignUpEmail = findViewById(R.id.editSignUpTextEmailId)
        editTextSignUpPassword = findViewById(R.id.editSignUpTextPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        prgbarregister = findViewById(R.id.prgbarregister)
        txtViewSignIn = findViewById(R.id.txtViewSignIn)
        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
            navigateToHome()
        }
        viewModel.registerResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                   showLoading()
                }
                is BaseResponse.Success -> {
                    stopLoading()
                    processRegister(it.data)
                }
                is BaseResponse.Error -> {
                    processError(it.msg)
                }
            }
        }

        txtViewSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {
            doRegister()
        }
    }

    private fun doRegister() {
        val userName = editTextFirstName.text.toString() + editTextLastName.text.toString()
        val email = editTextSignUpEmail.text.toString()
        val password = editTextSignUpPassword.text.toString()
        viewModel.registerUser(email, password)
    }
    private fun processError(message: String?) {
        showToast("Error:$message")
        if (message != null) {
            Log.d("Error",message)
        }
    }

    private fun processRegister(data: RegisterResponse?) {
        showToast("Success:" + data?.token)
        if (!data?.token.isNullOrEmpty()) {
           // data?.token?.let { SessionManager.saveAuthToken(this, it) }
            navigateToHome()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun stopLoading() {
        prgbarregister.visibility = View.GONE
    }
    private fun showLoading() {prgbarregister.visibility = View.VISIBLE}

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }
}