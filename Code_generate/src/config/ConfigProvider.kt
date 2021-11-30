package com.example.config

import java.io.FileInputStream
import java.util.Properties

object ConfigProvider {
    private val configFile = FileInputStream("resources/config.properties")
    private val appProps = Properties()

    init {
        appProps.load(configFile)
    }

    val dbUrl: String by lazy { appProps.getProperty("db.url") }
    val dbUser: String by lazy { appProps.getProperty("db.user") }
    val dbPassword: String by lazy { appProps.getProperty("db.password") }
    val dbDriver: String by lazy { appProps.getProperty("db.driver") }

    val debugUrl: String by lazy { appProps.getProperty("debug.url") }

    val adminPassword: String by lazy { appProps.getProperty("admin.password") }
    val adminUser: String by lazy { appProps.getProperty("admin.user") }

    val logsPath: String by lazy { appProps.getProperty(("logs.path"))}
}