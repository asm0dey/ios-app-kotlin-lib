package com.example.myapplication

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class Greeting {
    private val platform: Platform = getPlatform()
    private val client = HttpClient(CIO)

    private suspend fun getLocation(): String {
        val (country, city) = client
            .get("http://ip-api.com/csv?fields=country,city")
            .bodyAsText()
            .split(',')
        return "$city, $country"
    }

    suspend fun greet(): String {
        return "Hello, ${platform.name}. Your location is ${getLocation()}"
    }
}