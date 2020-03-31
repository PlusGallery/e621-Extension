package com.furry.plusgallery.extension.e621

import com.furry.plusgallery.extension.model.Credentials

class E621Credentials: Credentials {
    lateinit var Username: String
    lateinit var ApiKey: String

    override fun id(): String {
        return Username
    }

    fun chain(): String {
        return "login=$Username&api_key=$ApiKey&"
    }

}