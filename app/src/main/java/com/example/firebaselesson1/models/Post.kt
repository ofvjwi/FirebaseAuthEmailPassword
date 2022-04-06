package com.example.firebaselesson1.models

class Post {
    var id: String? = null
    var title: String? = null
    var body: String? = null
    var image: String = ""

    constructor()

    constructor(id: String?, title: String?, body: String?) {
        this.id = id
        this.title = title
        this.body = body
    }

    constructor(title: String?, body: String?) {
        this.title = title
        this.body = body
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (title != other.title) return false
        if (body != other.body) return false
        if (image != other.image) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (body?.hashCode() ?: 0)
        result = 31 * result + image.hashCode()
        return result
    }

    override fun toString(): String {
        return "Post(id=$id, title=$title, body=$body, image='$image')"
    }
}

