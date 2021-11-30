package com.example.dao

import com.mongodb.ConnectionString
import io.ktor.http.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

typealias MongoDB = CoroutineDatabase

suspend fun createMongoDB(): MongoDB {
    val host = System.getenv("MONGODB_HOST") ?: "localhost"
    val port = System.getenv("MONGODB_PORT")?.toInt() ?: 27017
    val database = System.getenv("MONGODB_DATABASE") ?: "code-generator"
    return KMongo.createClient(
        connectionString = ConnectionString(
            URLBuilder(
                protocol = URLProtocol.createOrDefault("mongodb"),
                host,
                port
            ).buildString()
        )
    ).coroutine.getDatabase(database).prepareDatabase()
}

private suspend fun MongoDB.prepareDatabase() = apply {
    val variantsCollection = getCollection<Variants>()
    variantsCollection.ensureUniqueIndex(Variants::parameters)
}