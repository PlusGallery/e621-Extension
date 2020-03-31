package com.furry.plusgallery.extension.e621

import com.furry.plusgallery.extension.model.*
import com.furry.plusgallery.extension.network.Http
import okhttp3.Response

private const val domain = "https://e621.net"
private const val posts = "/posts.json?"
private const val limit = "limit="
private const val page = "page="
private const val tags = "tags="

private const val rSafe = "rating:s"
private const val owner = "user:"
private const val favBy = "favoritedby:"
private const val order = "order:"

private class E621Response : ResponseData {
    var success: Boolean? = null
    var message: String = ""
    val posts: ArrayList<E621Submission> = ArrayList()

    override fun codeResult(): ResponseCode {
        return when (success) {
            null -> ResponseCode.NET_FAILURE
            false -> ResponseCode.FAILURE
            true -> ResponseCode.SUCCESS
        }
    }

    override fun message(): String = message

    override fun endReached(): Boolean {
        return posts.size == 0
    }

    override fun submissions(): List<Submission> {
        return posts
    }
}



class E621Controller: RequestHandler() {

    private var credentials: E621Credentials? = null

    private fun basePath(path: String): String {
        return domain.plus(path)
            .plus(credentials?.chain() ?: "")
    }

    override fun login(c: Credentials?, call: (r: ResponseData) -> Unit) {
        credentials = c as E621Credentials?
        val url = basePath(posts).plus(limit).plus(0)
        client.request<E621Response>(Http.get(url)) { _: Response?, obj: E621Response? ->
            if (obj != null)
                call(obj)
            call(E621Response()) // Empty for net failure
            return@request
        }
    }

    override fun search(request: SearchRequest, call: (r: ResponseData) -> Unit) {
        val url = basePath(posts)
            .plus(limit).plus(20).plus("&")
            .plus(page).plus(request.page).plus("&")
            .plus(tags).plus(request.search.replace(',', '+')).plus("+")
            .plus(if(request.safe) rSafe.plus("+") else "")
            .plus(if(request.owner.isNotEmpty()) owner.plus(request.owner).plus("+") else "")
            .plus(if(request.favBy.isNotEmpty()) favBy.plus(request.favBy).plus("+") else "")
            .plus(order).plus((request.order as E621SortType).value())
        client.request<E621Response>(Http.get(url)) { _: Response?, obj: E621Response? ->
            if (obj != null)
                call(obj)
            call(E621Response())
            return@request
        }
    }

}