package com.example

import com.example.config.ConfigProvider
import com.example.dao.createMongoDB
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
    val log: Logger = LoggerFactory.getLogger("jsonLogger")
    log.info("debugUrl = ${ConfigProvider.debugUrl}")
    io.ktor.server.jetty.EngineMain.main(args)
}

private val mongoDB by lazy {
    runBlocking {
        createMongoDB()
    }
}

@Suppress("unused")
fun Application.module(testing: Boolean = false) {
    install(CallLogging)
    install(ContentNegotiation) {
        json(jsonParser)
    }
    configureServer()
    configureExceptions()
    configureNewServer(mongoDB)
}
