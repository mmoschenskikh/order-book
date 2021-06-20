package ru.maxultra.wstask.data.network

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ru.maxultra.wstask.data.entities.DepthStreamEvent
import ru.maxultra.wstask.data.entities.SocketRequest
import javax.inject.Inject

class BinanceWebSocketListener @Inject constructor(
    private val socketRequestAdapter: JsonAdapter<SocketRequest>,
    private val depthStreamEventAdapter: JsonAdapter<DepthStreamEvent>
) : WebSocketListener() {

    lateinit var handleMessage: (DepthStreamEvent) -> Unit
    lateinit var handleFailure: (String, Throwable) -> Unit

    var symbol: String? = null
        set(value) {
            if (this::webSocket.isInitialized) {
                when {
                    value == null && field != null -> {
                        unsubscribe(field!!)
                    }
                    value != null && field != null && value != field -> {
                        unsubscribe(field!!)
                        subscribe(value)
                    }
                    value != null && field == null -> {
                        subscribe(value)
                    }
                }
            }
            field = value
        }

    private lateinit var webSocket: WebSocket

    private fun unsubscribe(symbol: String) {
        val unsubscribeRequest = SocketRequest(
            method = METHOD_UNSUBSCRIBE,
            params = listOf("$symbol$PARAM_DEPTH"),
            id = 1
        )
        val jsonUnsubscribeRequest = socketRequestAdapter.toJson(unsubscribeRequest)
        webSocket.send(jsonUnsubscribeRequest)
    }

    private fun subscribe(symbol: String) {
        val request = SocketRequest(
            method = METHOD_SUBSCRIBE,
            params = listOf("$symbol$PARAM_DEPTH"),
            id = 1
        )
        val jsonRequest = socketRequestAdapter.toJson(request)
        webSocket.send(jsonRequest)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        this.webSocket = webSocket
        if (symbol != null) subscribe(symbol!!)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        try {
            val event = depthStreamEventAdapter.fromJson(text) ?: return
            handleMessage(event)
        } catch (e: JsonDataException) {
            Log.e("BinanceWSListener", "$e")
            Log.e("BinanceWSListener", "Not a depth stream event:\n$text")
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        handleFailure("WebSocket failure", t)
    }
}
