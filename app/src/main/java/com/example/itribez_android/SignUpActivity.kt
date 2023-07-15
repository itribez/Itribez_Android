package com.example.itribez_android

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var editTextFirstName: TextInputEditText
    lateinit var editTextLastName: TextInputEditText
    lateinit var editTextSignUpEmail: TextInputEditText
    lateinit var editTextSignUpPassword: TextInputEditText
    lateinit var btnSignUp: Button
    lateinit var txtViewSignIn: TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextSignUpEmail = findViewById(R.id.editSignUpTextEmailId)
        editTextSignUpPassword = findViewById(R.id.editSignUpTextPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        txtViewSignIn = findViewById(R.id.txtViewSignIn)
        firebaseAuth = FirebaseAuth.getInstance()
        txtViewSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {
            val firstname = editTextFirstName.text.toString()
            val lastname = editTextLastName.text.toString()
            val email = editTextSignUpEmail.text.toString()
            val pass = editTextSignUpPassword.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && firstname.isNotEmpty() && lastname.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "createUserWithEmail:success")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }
    }
}