package com.example.firebaselesson1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaselesson1.R
import com.example.firebaselesson1.activity.MainActivity
import com.example.firebaselesson1.models.Post

class MainRecyclerAdapter(var activity: MainActivity, var items: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = items[position]
        if (holder is PostViewHolder) {
            holder.tv_title.text = post.title!!.uppercase()
            holder.tv_body.text = post.body
        }
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_title: TextView = view.findViewById(R.id.title_text_view)
        var tv_body: TextView = view.findViewById(R.id.body_text_view)

    }

    init {
        this.activity = activity
        this.items = items
    }
}