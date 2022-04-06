package com.example.firebaselesson1.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaselesson1.R
import com.example.firebaselesson1.adapters.MainRecyclerAdapter
import com.example.firebaselesson1.managers.AuthManager
import com.example.firebaselesson1.managers.DatabaseHandler
import com.example.firebaselesson1.managers.FirebaseDatabaseManager
import com.example.firebaselesson1.models.Post
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity() {

    private lateinit var signOutImageView: ImageView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        signOutImageView = findViewById(R.id.btn_sign_out)
        floatingActionButton = findViewById(R.id.add_btn)

        recyclerView = findViewById(R.id.main_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        signOutImageView.setOnClickListener {
            AuthManager.signOut()
            callSignInActivity(context)
            finish()
        }
        floatingActionButton.setOnClickListener {
            callCreateActivity()
        }
        apiLoadPosts()
    }

    fun refreshAdapter(posts: ArrayList<Post>) {
        val adapter = MainRecyclerAdapter(this, posts)
        recyclerView.adapter = adapter
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                // Load all posts...
                apiLoadPosts()
            }
        }

    private fun callCreateActivity() {
        val intent = Intent(this, CreateActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun apiLoadPosts() {
        showLoading(this)
        FirebaseDatabaseManager.apiLoadPosts(object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                dismissLoading()
                refreshAdapter(posts)
            }

            override fun onError() {
                dismissLoading()
            }
        })
    }

    fun apiDeletePost(post: Post) {
        FirebaseDatabaseManager.apiDeletePost(post, object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                apiLoadPosts()
            }

            override fun onError() {
                TODO("Not yet implemented")
            }
        })
    }
}


