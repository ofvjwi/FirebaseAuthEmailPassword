package com.example.firebaselesson1.activity

import android.os.Bundle
import android.widget.Button
import com.example.firebaselesson1.R
import com.example.firebaselesson1.managers.AuthManager

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        findViewById<Button>(R.id.btn_sign_out).setOnClickListener {
            AuthManager.signOut()
            callSignInActivity(context)
            finish()
        }
    }
}


