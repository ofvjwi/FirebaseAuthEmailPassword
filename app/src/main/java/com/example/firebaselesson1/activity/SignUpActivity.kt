package com.example.firebaselesson1.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaselesson1.R
import com.example.firebaselesson1.managers.AuthHandler
import com.example.firebaselesson1.managers.AuthManager
import com.example.firebaselesson1.utils.Extensions.toast
import java.lang.Exception

class SignUpActivity : BaseActivity() {
    val TAG = SignUpActivity::class.java.toString()
    lateinit var et_fullname: EditText
    lateinit var et_password: EditText
    lateinit var et_email: EditText
    lateinit var et_cpassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initViews()
    }

    private fun initViews() {
        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_cpassword = findViewById(R.id.et_cpassword)

        val b_signup = findViewById<Button>(R.id.b_signup)
        b_signup.setOnClickListener { firebaseSignUp(et_email.text.toString(),et_password.text.toString()) }
        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener { finish() }
    }

    private fun firebaseSignUp(email: String, password: String) {
        // showLoading(this)

        AuthManager.signUp(email, password, object : AuthHandler {
            override fun onSuccess() {
                // dismiss loading
                toast("Signed up successfully")
                callMainActivity(context)
            }

            override fun onError(exception: Exception?) {
                // dismiss loading
                toast("Signed up failed")
            }
        })
    }
}