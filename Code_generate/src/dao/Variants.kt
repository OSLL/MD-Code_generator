package com.example.dao

import com.example.GenerationParameters
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.newId

@Serializable
data class Variants(
    @Contextual
    @SerialName("_id")
    val id: Id<Variants> = newId(),
    val parameters: GenerationParameters,
    val image: ByteArray,
    val codeText: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Variants

        if (id != other.id) return false
        if (parameters != other.parameters) return false
        if (codeText != other.codeText) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + codeText.hashCode()
        return result
    }
}

class VariantsOps(private val database: MongoDB) {
    val collection: CoroutineCollection<Variants>
        get() = database.getCollection()

    suspend fun getImage(parameters: GenerationParameters) =
        collection.find(Variants::parameters eq parameters).first()?.image

    suspend fun getText(parameters: GenerationParameters) =
        collection.find(Variants::parameters eq parameters).first()?.codeText

    suspend fun addVariant(parameters: GenerationParameters, image: ByteArray, codeText: String) =
        collection.insertOne(Variants(parameters = parameters, image = image, codeText = codeText))
}