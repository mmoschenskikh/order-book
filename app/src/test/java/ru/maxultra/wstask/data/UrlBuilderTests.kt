package ru.maxultra.wstask.data

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.maxultra.wstask.data.network.UrlBuilder

class UrlBuilderTests {

    @Test
    fun depthRequestHttpTest() {
        var expected = "https://api.binance.com/api/v3/depth?symbol=BNBBTC&limit=1000"
        var actual = UrlBuilder.getDepthRequestHttp("BNBBTC", 1000).toString()
        assertEquals(expected, actual)

        expected = "https://api.binance.com/api/v3/depth?symbol=BTCUSDT&limit=1337"
        actual = UrlBuilder.getDepthRequestHttp("BTCUSDT", 1337).toString()
        assertEquals(expected, actual)
    }
}
