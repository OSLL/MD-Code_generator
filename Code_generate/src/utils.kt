package com.example

import kotlinx.serialization.json.*

val jsonParser = Json

fun parseGenerationParameters(queryParameters: Map<String, List<String>>) = buildJsonObject {
    for (entry in queryParameters) {
        if (entry.value.size > 1) {
            put(entry.key, JsonArray(entry.value.map { JsonPrimitive(it) }))
        } else {
            val value = entry.value[0]
            when (entry.key) {
                "rand_seed" -> {
                    put(entry.key, value)
                }
                "redefinition_var" -> {
                    put(entry.key, value == "1")
                }
                else -> {
                    put(entry.key, value.toInt())
                }
            }
        }
    }
}.let {
    jsonParser.decodeFromJsonElement<GenerationParameters>(it)
}