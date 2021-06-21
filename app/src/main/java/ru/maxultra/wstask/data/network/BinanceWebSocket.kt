package ru.maxultra.wstask.data.network

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.maxultra.wstask.data.entities.DepthSnapshot
import ru.maxultra.wstask.data.entities.DepthStreamEvent
import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.entities.Order
import ru.maxultra.wstask.domain.usecases.ManageOrderBookUseCase

class BinanceWebSocket(
    initialSymbol: String,
    initialSnapshot: DepthSnapshot,
    client: OkHttpClient,
    connectionRequest: Request,
    private val binanceWebSocketListener: BinanceWebSocketListener,
    private val scope: CoroutineScope = GlobalScope,
) {

    init {
        binanceWebSocketListener.symbol = initialSymbol.lowercase()
        binanceWebSocketListener.handleMessage = { event -> handleMessage(event) }
    }

    private val webSocket = client.newWebSocket(connectionRequest, binanceWebSocketListener)

    private var symbol: String = initialSymbol
    private var snapshot: DepthSnapshot = initialSnapshot

    private var bidsManager = ManageOrderBookUseCase(ordersListToOrders(snapshot.bids))
    private var asksManager = ManageOrderBookUseCase(ordersListToOrders(snapshot.asks))

    private var messageHandlerJob: Job? = null

    private val _bids = MutableStateFlow(bidsManager.orderBook)
    private val _asks = MutableStateFlow(asksManager.orderBook)
    private val _diff = MutableStateFlow(Pair(bidsManager.diff, asksManager.diff))

    val bids: Flow<List<Order>>
        get() = _bids
    val asks: Flow<List<Order>>
        get() = _asks
    val diff: Flow<Pair<List<Difference>, List<Difference>>>
        get() = _diff

    fun updateSymbol(symbol: String, snapshot: DepthSnapshot) {
        messageHandlerJob?.cancel()
        clearFlows()
        this.symbol = symbol
        this.snapshot = snapshot
        binanceWebSocketListener.symbol = symbol.lowercase()
        bidsManager = ManageOrderBookUseCase(ordersListToOrders(snapshot.bids))
        asksManager = ManageOrderBookUseCase(ordersListToOrders(snapshot.asks))
    }

    fun closeConnection() {
        messageHandlerJob?.cancel()
        webSocket.close(CLOSE_CODE_OK, "No longer needed")
    }

    private fun clearFlows() {
        _bids.value = emptyList()
        _asks.value = emptyList()
        _diff.value = Pair(emptyList(), emptyList())
    }

    private fun handleMessage(event: DepthStreamEvent) {
        messageHandlerJob = scope.launch {
            if (event.finalUpdateId > snapshot.lastUpdateId) {
                val bidsToUpdate = ordersListToOrders(event.bidsToUpdate)
                val asksToUpdate = ordersListToOrders(event.asksToUpdate)

                withContext(Dispatchers.Default) {
                    bidsManager.update(bidsToUpdate)
                    asksManager.update(asksToUpdate)
                }

                _bids.value = bidsManager.orderBook
                _asks.value = asksManager.orderBook
                _diff.value = Pair(bidsManager.diff, asksManager.diff)
            }
        }
    }

    private fun ordersListToOrders(ordersList: List<List<String>>): List<Order> {
        return ordersList.map { Order(it[1].toDouble(), it[0].toDouble()) }
    }

    companion object {
        private const val CLOSE_CODE_OK = 1000
    }
}
