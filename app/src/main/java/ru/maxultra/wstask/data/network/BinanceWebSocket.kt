package ru.maxultra.wstask.data.network

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.*
import ru.maxultra.wstask.data.entities.DepthStreamEvent
import ru.maxultra.wstask.data.entities.SocketRequest
import javax.inject.Inject

class BinanceWebSocket @Inject constructor(
    private val client: OkHttpClient,
    private val connectionRequest: Request,
    private val socketRequestAdapter: JsonAdapter<SocketRequest>,
    private val depthStreamEventAdapter: JsonAdapter<DepthStreamEvent>
) {

    @ExperimentalCoroutinesApi
    fun getOrderBook(symbol: String): Flow<DepthStreamEvent> = callbackFlow {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                val request = SocketRequest(
                    method = METHOD_SUBSCRIBE,
                    params = listOf("$symbol$PARAM_DEPTH"),
                    id = 1
                )
                val jsonRequest = socketRequestAdapter.toJson(request)
                webSocket.send(jsonRequest)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val event = depthStreamEventAdapter.fromJson(text) ?: return
                    offer(event)
                } catch (e: JsonDataException) {
                    Log.e("BinanceWebSocket", "$e")
                    Log.e("BinanceWebSocket", "Not a depth stream event:\n$text")
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                cancel("WS FAILURE", t)
            }
        }

        val webSocket = client.newWebSocket(connectionRequest, listener)

        awaitClose {
            webSocket.close(CLOSE_CODE_OK, "No longer needed")
        }
    }

    companion object {
        private const val CLOSE_CODE_OK = 1000
    }
}
