package ru.maxultra.wstask.data

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.maxultra.wstask.data.entities.SocketRequest
import ru.maxultra.wstask.di.DataModule
import ru.maxultra.wstask.removeWhiteSpaces

class JsonConversionTests {

    private val dataModule = DataModule()
    private val moshi = dataModule.provideMoshi()

    @Test
    fun socketRequestToJsonTest() {
        val adapter = dataModule.provideSocketRequestAdapter(moshi)
        val request = SocketRequest(
            method = "SUBSCRIBE",
            params = listOf("btcusdt@aggTrade", "btcusdt@depth"),
            id = 1
        )
        val json = adapter.toJson(request)
        val expected = "{\n" +
                "  \"method\": \"SUBSCRIBE\",\n" +
                "  \"params\": [\n" +
                "    \"btcusdt@aggTrade\",\n" +
                "    \"btcusdt@depth\"\n" +
                "  ],\n" +
                "  \"id\": 1\n" +
                "}"
        assertEquals(expected.removeWhiteSpaces(), json.removeWhiteSpaces())
    }
}
