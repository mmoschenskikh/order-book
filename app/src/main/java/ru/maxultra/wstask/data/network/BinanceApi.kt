package ru.maxultra.wstask.data.network

import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.maxultra.wstask.data.entities.DepthSnapshot
import ru.maxultra.wstask.data.network.UrlBuilder.getDepthRequestHttp
import javax.inject.Inject

class BinanceApi @Inject constructor(
    private val client: OkHttpClient,
    private val depthSnapshotAdapter: JsonAdapter<DepthSnapshot>
) {
    suspend fun getDepthSnapshot(symbol: String, limit: Int = 100): DepthSnapshot? {
        val url = getDepthRequestHttp(symbol, limit)
        val request = Request.Builder().url(url).build()
        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            depthSnapshotAdapter.fromJson(response.body?.string()!!)
        }
    }
}


