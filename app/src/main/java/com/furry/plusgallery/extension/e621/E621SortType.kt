package com.furry.plusgallery.extension.e621;

import com.furry.plusgallery.extension.model.SortType

enum class E621SortType(private val text: String, value: String): SortType {
    DATE("Date", "-id"),
    FAVORITES("Favorites", "favcount"),
    RANDOM("Random", "random");

    companion object : SortType.Object {
        // Get list of strings defining sort items
        override fun listAll(): Array<SortType> {
            return Array(values().size) {
                return@Array values()[it]
            }
        }
    }

    override fun text(): String {
        return this.text
    }

    fun value(): String {
        return this.text
    }
}
