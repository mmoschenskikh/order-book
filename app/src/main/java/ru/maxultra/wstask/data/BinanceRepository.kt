package ru.maxultra.wstask.data


import kotlinx.coroutines.flow.MutableStateFlow
import ru.maxultra.wstask.data.network.BinanceApi
import ru.maxultra.wstask.data.network.BinanceWebSocket
import ru.maxultra.wstask.data.network.BinanceWebSocketFactory
import ru.maxultra.wstask.domain.Repository
import ru.maxultra.wstask.domain.entities.currency.EnumCurrency
import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair
import ru.maxultra.wstask.domain.entities.currencypair.SimpleCurrencyPair
import ru.maxultra.wstask.domain.usecases.CurrencyPairToStringUseCase
import javax.inject.Inject

class BinanceRepository @Inject constructor(
    private val binanceApi: BinanceApi,
    private val binanceWebSocketFactory: BinanceWebSocketFactory,
) : Repository {

    override val dataIsReady = MutableStateFlow(false)
    override val currentSymbol = MutableStateFlow<CurrencyPair?>(null)
    private lateinit var binanceWebSocket: BinanceWebSocket

    override fun getAvailablePairs(): List<CurrencyPair> {
        return listOf( // FIXME: Replace implementations with abstractions
            SimpleCurrencyPair(EnumCurrency.BTC, EnumCurrency.USDT),
            SimpleCurrencyPair(EnumCurrency.BNB, EnumCurrency.BTC),
            SimpleCurrencyPair(EnumCurrency.ETH, EnumCurrency.BTC)
        )
    }

    override suspend fun setCurrencyPair(currencyPair: CurrencyPair) {
        dataIsReady.value = false
        currentSymbol.value = currencyPair
        val symbol = CurrencyPairToStringUseCase.execute(currencyPair)
        val snapshot = binanceApi.getDepthSnapshot(symbol)
            ?: throw IllegalStateException("Cannot get data from the API")

        if (::binanceWebSocket.isInitialized) {
            binanceWebSocket.updateSymbol(symbol, snapshot)
        } else {
            binanceWebSocket = binanceWebSocketFactory.create(symbol, snapshot)
        }
        dataIsReady.value = true
    }

    override fun getBidsStream() = binanceWebSocket.bids

    override fun getAsksStream() = binanceWebSocket.asks

    override fun getDiffStream() = binanceWebSocket.diff
}
