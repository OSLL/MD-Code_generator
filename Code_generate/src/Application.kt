package com.example

import io.ktor.application.*
import io.ktor.features.*

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(CallLogging)
    congigureServer()
}
