package com.example.firebaselesson1.managers

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class StorageManager {
    companion object {
        private val storage = FirebaseStorage.getInstance()
        private var storageRef = storage.reference
        var photosRef = storageRef.child("photos")

        fun uploadPhoto(uri: Uri, handler: StorageHandler) {
            val filename = System.currentTimeMillis().toString() + ".png"
            val uploadTask: UploadTask = photosRef.child(filename).putFile(uri)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                val result = taskSnapshot.metadata!!.reference!!.downloadUrl
                result.addOnSuccessListener { url ->
                    val imgUrl = url.toString()
                    handler.onSuccess(imgUrl)
                }.addOnFailureListener { e ->
                    handler.onError(e)
                }
            }.addOnFailureListener { e ->
                handler.onError(e)
            }
        }
    }
}

