package com.example.dao

import com.example.GenerationParameters
import com.mongodb.DuplicateKeyException
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineCollection

@Serializable
data class Statistics(
    @Contextual
    @SerialName("_id")
    val id: Id<Statistics> = newId(),
    val parameters: GenerationParameters,
    val usersAnswer: List<String>,
    val rightAnswer: List<String>,
    val correctness: Int,
    val timestamp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (id != (other as Statistics).id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + parameters.hashCode()
        return result
    }
}

class StatisticsOps(private val database: MongoDB) {
    val collection: CoroutineCollection<Statistics>
        get() = database.getCollection()

    suspend fun getList() =
        collection.find().toList()

    suspend fun getAnswersOnTask(parameters: GenerationParameters) =
        collection.find(and(Statistics::parameters eq parameters))

    suspend fun clearCollection() =
        collection.deleteMany()

    suspend fun addAttempt(parameters: GenerationParameters, usersAnswer: List<String>, rightAnswer: List<String>, correctness: Int) {
        try {
            collection.insertOne(
                Statistics(
                    parameters = parameters,
                    usersAnswer = usersAnswer,
                    rightAnswer = rightAnswer,
                    correctness = correctness,
                    timestamp = System.currentTimeMillis()
                )
            )
        } catch (e: DuplicateKeyException) {
        }
    }
}