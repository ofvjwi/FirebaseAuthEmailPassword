package com.example.firebaselesson1.managers

import com.example.firebaselesson1.models.Post

interface DatabaseHandler {
    fun onSuccess(post: Post? = null, posts: ArrayList<Post> = ArrayList())
    fun onError()
}
