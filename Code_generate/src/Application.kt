package com.example

import io.ktor.application.*
import io.ktor.features.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
    val log: Logger = LoggerFactory.getLogger("configLogger")
    io.ktor.server.jetty.EngineMain.main(args)
}

fun Application.module(testing: Boolean = false) {
    install(CallLogging)
    congigureServer()
}
