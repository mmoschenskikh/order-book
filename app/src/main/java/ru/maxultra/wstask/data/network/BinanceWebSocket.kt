package ru.maxultra.wstask.data.network

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.maxultra.wstask.data.entities.DepthSnapshot
import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.entities.Order
import ru.maxultra.wstask.domain.usecases.ManageOrderBookUseCase

private typealias FlowTriple = Flow<Triple<List<Order>, List<Order>, Pair<List<Difference>, List<Difference>>>>

@ExperimentalCoroutinesApi
class BinanceWebSocket constructor(
    initialSymbol: String,
    initialSnapshot: DepthSnapshot,
    private val client: OkHttpClient,
    private val binanceWebSocketListener: BinanceWebSocketListener,
    private val connectionRequest: Request
) {

    private var symbol: String = initialSymbol
    private var snapshot: DepthSnapshot = initialSnapshot

    fun updateSymbol(symbol: String, snapshot: DepthSnapshot) {
        this.symbol = symbol
        this.snapshot = snapshot
        binanceWebSocketListener.symbol = symbol.lowercase()
    }

    private val triple: FlowTriple = callbackFlow {
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
            if (event.finalUpdateId > snapshot.lastUpdateId) {
                val bidsToUpdate = ordersListToOrders(event.bidsToUpdate)
                val asksToUpdate = ordersListToOrders(event.asksToUpdate)

                bidsManager.update(bidsToUpdate)
                asksManager.update(asksToUpdate)
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
        binanceWebSocketListener.symbol = symbol.lowercase()

        val webSocket = client.newWebSocket(connectionRequest, binanceWebSocketListener)

        awaitClose {
            webSocket.close(CLOSE_CODE_OK, "No longer needed")
        }
    }

    val bids = triple.map { it.first }
    val asks = triple.map { it.second }
    val diff = triple.map { it.third }

    private fun ordersListToOrders(ordersList: List<List<String>>): List<Order> {
        return ordersList.map { Order(it[1].toDouble(), it[0].toDouble()) }
    }

    companion object {
        private const val CLOSE_CODE_OK = 1000
    }
}
