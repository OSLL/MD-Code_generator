package com.example.ImageGenerator

import ImageGenerator.IMAGE
import com.example.CARRIAGE_RETURN
import com.example.ImageGeneration.ImageInfo
import com.example.ImageGeneration.SettingsInfo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import io.ktor.client.statement.HttpResponse
import java.io.File

class ImageWebGenerator(programText: String) : ImageGenerator(programText) {
    override suspend fun generateImage() {
        val values = mapOf("code" to programText, "settings" to mapOf("language" to "C", "theme" to "dark-plus"))
        val requestBody: String = Json.encodeToString(ImageInfo(programText,
                                                                SettingsInfo("C", "dark-plus")))

        val client = HttpClient()
        val generatorUrl = "https://sourcecodeshots.com/api/image"
        val request : HttpResponse = client.post<HttpResponse>(generatorUrl) {
            body = TextContent(requestBody, contentType = ContentType.Application.Json)
        }


        val count = programText.count { it.toString() == CARRIAGE_RETURN } + 1
        val imageWidth = 1200
        val imageHeight = count * 30
        val capchaImage = com.example.ImageGeneration.generateImage(programText, imageWidth, imageHeight)
        val test = File("test.png")
        test.writeBytes(capchaImage!!)

        val response = request.receive<ByteArray>()
        val file = File("program.png")
        file.writeBytes(response)
        println("A file saved to ${file.path}")
    }
}