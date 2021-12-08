package com.example

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.*

val jsonParser = Json {
    ignoreUnknownKeys = true
}

fun parseGenerationParameters(queryParameters: Map<String, List<String>>) = buildJsonObject {
    for (entry in queryParameters) {
        val value = entry.value[0]
        when (entry.key) {
            "answer" -> {}
            "operations" -> {
                put(entry.key, JsonArray(entry.value[0].split(',').map { JsonPrimitive(it) }))
            }
            "rand_seed" -> {
                put(entry.key, value)
            }
            "redefinition_var" -> {
                put(entry.key, value == "1")
            }
            else -> {
                put(
                    entry.key,
                    value.toIntOrNull() ?: throw ParametersParseError("${entry.key} should be an integer value")
                )
            }
        }
    }
}.let {
    try {
        jsonParser.decodeFromJsonElement<GenerationParameters>(it)
    } catch (e: SerializationException) {
        throw ParametersParseError("Fail to parse parameters: $e")
    }
}