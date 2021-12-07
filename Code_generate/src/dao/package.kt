package com.example.dao

import com.example.config.ConfigProvider
import com.mongodb.ConnectionString
import io.ktor.http.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

typealias MongoDB = CoroutineDatabase

suspend fun createMongoDB(): MongoDB {
    return KMongo.createClient(
        connectionString = ConnectionString(
            URLBuilder(
                protocol = URLProtocol.createOrDefault("mongodb"),
                host = ConfigProvider.dbHost,
                port = ConfigProvider.dbPort,
                user = ConfigProvider.dbUser,
                password = ConfigProvider.dbPassword,
            ).buildString()
        )
    ).coroutine.getDatabase(ConfigProvider.dbName).prepareDatabase()
}

private suspend fun MongoDB.prepareDatabase() = apply {
    val variantsCollection = getCollection<Variants>()
    variantsCollection.ensureUniqueIndex(Variants::parameters)
}