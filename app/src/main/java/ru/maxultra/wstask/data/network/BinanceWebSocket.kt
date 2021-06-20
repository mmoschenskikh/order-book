package ru.maxultra.wstask.data.network

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.entities.Order
import ru.maxultra.wstask.domain.usecases.ManageOrderBookUseCase
import ru.maxultra.wstask.round
import javax.inject.Inject

private typealias FlowTriple = Flow<Triple<List<Order>, List<Order>, Pair<List<Difference>, List<Difference>>>>

class BinanceWebSocket @Inject constructor(
    private val client: OkHttpClient,
    private val binanceApi: BinanceApi,
    private val binanceWebSocketListener: BinanceWebSocketListener,
    private val connectionRequest: Request
) {

    private var symbol: String = "btcusdt"

    @ExperimentalCoroutinesApi
    private val triple: FlowTriple = callbackFlow {
        val snapshot = binanceApi.getDepthSnapshot(symbol.uppercase())
            ?: throw IllegalStateException("Cannot fetch API data")
        val bidsManager = ManageOrderBookUseCase(ordersListToOrders(snapshot.bids))
        val asksManager = ManageOrderBookUseCase(ordersListToOrders(snapshot.asks))

        offer(
            Triple(
                bidsManager.orderBook,
                asksManager.orderBook,
                Pair(bidsManager.diff, asksManager.diff)
            )
        )

        binanceWebSocketListener.handleMessage = { event ->
            Log.d(this::class.java.simpleName, "Processing message")
            Log.d(this::class.java.simpleName, "Symbol is $symbol")
            Log.d(this::class.java.simpleName, "Snapshot is $snapshot")
            Log.d(this::class.java.simpleName, "Managers are $bidsManager and $asksManager")
            if (event.finalUpdateId > snapshot.lastUpdateId) {
                val bidsToUpdate = ordersListToOrders(event.bidsToUpdate)
                val asksToUpdate = ordersListToOrders(event.asksToUpdate)

                bidsManager.update(bidsToUpdate)
                asksManager.update(asksToUpdate)
                Log.d(this::class.java.simpleName, "Order book updated")
                offer(
                    Triple(
                        bidsManager.orderBook,
                        asksManager.orderBook,
                        Pair(bidsManager.diff, asksManager.diff)
                    )
                )
            }
        }
        binanceWebSocketListener.handleFailure = { msg, t -> cancel(msg, t) }
        binanceWebSocketListener.symbol = symbol
        val webSocket = client.newWebSocket(connectionRequest, binanceWebSocketListener)

        awaitClose {
            webSocket.close(CLOSE_CODE_OK, "No longer needed")
        }
    }

    @ExperimentalCoroutinesApi
    val bids = triple.map { it.first }

    @ExperimentalCoroutinesApi
    val asks = triple.map { it.second }

    @ExperimentalCoroutinesApi
    val diff = triple.map { it.third }

    private fun ordersListToOrders(ordersList: List<List<String>>): List<Order> {
        return ordersList.map { Order(it[1].toDouble(), it[0].toDouble().round(2)) }
    }

    companion object {
        private const val CLOSE_CODE_OK = 1000
    }
}
