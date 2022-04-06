package com.example.firebaselesson1.activity

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firebaselesson1.R
import com.example.firebaselesson1.managers.DatabaseHandler
import com.example.firebaselesson1.managers.FirebaseDatabaseManager
import com.example.firebaselesson1.managers.StorageHandler
import com.example.firebaselesson1.managers.StorageManager
import com.example.firebaselesson1.models.Post
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter

class CreateActivity : BaseActivity() {

    companion object {
        private const val TAG: String = "CreateActivity"
    }

    lateinit var imageViewPhoto: ImageView
    var pickedImage: Uri? = null
    var uris = ArrayList<Uri>()

    private lateinit var exitImageView: ImageView
    private lateinit var titleEditText: EditText
    private lateinit var bodyEdittext: EditText
    private lateinit var createButton: Button
    private lateinit var imageViewCamera: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initViews()
    }

    private fun initViews() {
        exitImageView = findViewById(R.id.exit_btn)
        titleEditText = findViewById(R.id.edittext_title)
        bodyEdittext = findViewById(R.id.edittext_body)
        createButton = findViewById(R.id.create_btn)
        imageViewPhoto = findViewById(R.id.iv_photo)

        imageViewCamera = findViewById(R.id.iv_camera)

        createButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val body = bodyEdittext.text.toString()
            val post = Post(title, body)
            storePost(post)
        }


        imageViewCamera.setOnClickListener {
            pickUserPhoto()
        }

    }

    private fun storeDatabase(post: Post) {
        FirebaseDatabaseManager.storePost(post, object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                Log.d(TAG, "onSuccess: Post is saved!")
                dismissLoading()
                finishIntent()
            }

            override fun onError() {
                dismissLoading()
                Log.d(TAG, "onError: Post is not saved!")
            }
        })
    }

    private fun finishIntent() {
        val returnIntent = intent
        setResult(RESULT_OK, returnIntent)
        finish()
    }


    private fun pickUserPhoto() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setSelectedImages(uris)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                uris = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
                pickedImage = uris[0]
                imageViewPhoto.setImageURI(pickedImage)
            }
        }

    private fun storePost(post: Post) {
        if (pickedImage != null) {
            storeStorage(post)
        } else {
            storeDatabase(post)
        }
    }
    private fun storeStorage(post: Post) {
        showLoading(this)
        StorageManager.uploadPhoto(pickedImage!!, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                post.image = imgUrl
                storeDatabase(post)
            }

            override fun onError(exception: Exception?) {
                storeDatabase(post)
            }
        })
    }
}

