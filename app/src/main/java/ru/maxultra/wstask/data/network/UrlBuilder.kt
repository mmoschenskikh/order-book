package ru.maxultra.wstask.data.network

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

object UrlBuilder {
    fun getDepthRequestHttp(symbol: String, limit: Int): HttpUrl {
        val urlBuilder = API_ENDPOINT.toHttpUrlOrNull()?.newBuilder()
            ?: throw IllegalArgumentException("Wrong API URL")
        urlBuilder
            .addPathSegments("api/v3/depth")
            .addQueryParameter("symbol", symbol)
            .addQueryParameter("limit", limit.toString())
        return urlBuilder.build()
    }
}
