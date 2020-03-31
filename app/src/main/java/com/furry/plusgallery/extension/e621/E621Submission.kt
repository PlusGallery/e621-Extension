package com.furry.plusgallery.extension.e621

import com.furry.plusgallery.extension.model.Submission
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS"

private class E621FileData {
    var width: Int = 0
    var height: Int = 0
    var ext: String? = null
    var size: Int? = null
    var md5: String? = null
    var url: String? = null
}

private class E621TagsList {
    var general: ArrayList<String> = ArrayList()
    var species: ArrayList<String> = ArrayList()
    var artist: ArrayList<String> = ArrayList()
    var invalid: ArrayList<String> = ArrayList()
    var lore: ArrayList<String> = ArrayList()
    var meta: ArrayList<String> = ArrayList()
}

class E621Submission: Submission {
    private var id: Int = 0
    private var created_at: String = ""
    private var preview: E621FileData? = null
    private var file: E621FileData? = null
    private var tags: E621TagsList? = null
    private var description: String = ""
    private var uploader_id: Int = 0

    override fun id(): Int {
        return id
    }

    override fun author(): String {
        if (tags?.artist?.size  != 0)
            return tags?.artist?.get(0).toString()
        return uploader_id.toString()
    }

    override fun userId(): String {
        return author()
    }

    override fun date(): Date {
        val format = SimpleDateFormat(pattern, Locale.ENGLISH)
        try {
            return format.parse(created_at)!!
        } catch (e: java.lang.Exception) {}
        return Date()
    }

    override fun thumbnail(): String {
        if (preview != null)
            return preview?.url.toString()
        return ""
    }

    override fun picture(): String {
        if (file != null)
            return file?.url.toString()
        return ""
    }

    override fun text(): String {
        return description
    }
}