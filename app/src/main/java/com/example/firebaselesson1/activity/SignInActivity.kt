package com.example.firebaselesson1.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaselesson1.R
import com.example.firebaselesson1.managers.AuthHandler
import com.example.firebaselesson1.managers.AuthManager
import com.example.firebaselesson1.utils.Extensions
import com.example.firebaselesson1.utils.Extensions.toast
import java.lang.Exception
import java.security.cert.Extension

class SignInActivity : BaseActivity() {
    val TAG = SignInActivity::class.java.toString()
    lateinit var et_email: EditText
    lateinit var et_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        initViews()
    }

    private fun initViews() {
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        val b_signin = findViewById<Button>(R.id.b_signin)
        b_signin.setOnClickListener { firebaseSignIn(et_email.text.toString(), et_password.text.toString()) }
        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener { callSignUpActivity() }
    }

    private fun callSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun firebaseSignIn(email: String, password: String) {

        showLoading(this)

        AuthManager.signIn(email, password, object : AuthHandler {
            override fun onSuccess() {
                dismissLoading()
                toast("Signed in successfully!")
                callMainActivity(context)
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                toast("Signed in failed! ${exception.toString()}")
            }
        })
    }
}

