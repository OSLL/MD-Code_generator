package com.example

import com.example.config.ConfigProvider
import io.ktor.application.*
import io.ktor.features.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
    val log: Logger = LoggerFactory.getLogger("jsonLogger")
    log.info("debugUrl = ${ConfigProvider.debugUrl}")
    log.info("dbUrl = ${ConfigProvider.dbUrl}")
    io.ktor.server.jetty.EngineMain.main(args)
}

fun Application.module(testing: Boolean = false) {
    install(CallLogging)
    congigureServer()
}
