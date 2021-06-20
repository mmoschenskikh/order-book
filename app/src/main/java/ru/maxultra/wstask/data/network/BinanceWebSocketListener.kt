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
    lateinit var symbol: String

    private lateinit var webSocket: WebSocket

    private fun subscribe(symbol: String) {
        if (symbol.isBlank()) return
        Log.d(this::class.java.simpleName, "Subscribing at $symbol")
        val request = SocketRequest(
            method = METHOD_SUBSCRIBE,
            params = listOf("$symbol$PARAM_DEPTH"),
            id = 1
        )
        val jsonRequest = socketRequestAdapter.toJson(request)
        webSocket.send(jsonRequest)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d(this::class.java.simpleName, "onOpen")
        super.onOpen(webSocket, response)
        this.webSocket = webSocket
        Log.d(this::class.java.simpleName, "this.webSocket : ${this.webSocket}")
//        Log.d(this::class.java.simpleName, "Symbol is : ${symbol}")
        if (::symbol.isInitialized) subscribe(symbol)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d(this::class.java.simpleName, "Message received: $text")
        try {
            val event = depthStreamEventAdapter.fromJson(text) ?: return
            handleMessage(event)
        } catch (e: JsonDataException) {
            Log.e("BinanceWSListener", "$e")
            Log.e("BinanceWSListener", "Not a depth stream event:\n$text")
        }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(this::class.java.simpleName, "Closed: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e(this::class.java.simpleName, "Failure (symbol is $symbol)", t)
        handleFailure("WebSocket failure", t)
    }
}
