package ru.maxultra.wstask.data.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.maxultra.wstask.data.entities.DepthSnapshot
import javax.inject.Inject

class BinanceWebSocketFactory @Inject constructor(
    private val client: OkHttpClient,
    private val binanceWebSocketListener: BinanceWebSocketListener,
    private val connectionRequest: Request
) {

    fun create(symbol: String, snapshot: DepthSnapshot, scope: CoroutineScope = GlobalScope) =
        BinanceWebSocket(
            symbol,
            snapshot,
            client,
            connectionRequest,
            binanceWebSocketListener,
            scope
        )
}

