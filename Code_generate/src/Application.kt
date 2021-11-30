package com.example

import com.example.dao.createMongoDB
import io.ktor.application.*
import io.ktor.features.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

private val mongoDB by lazy {
    runBlocking {
        createMongoDB()
    }
}

@Suppress("unused")
fun Application.module(testing: Boolean = false) {
    install(CallLogging)
    configureServer()
    configureExceptions()
    configureNewServer(mongoDB)
}
