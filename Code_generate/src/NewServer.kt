package com.example

import com.example.dao.MongoDB
import com.example.dao.VariantsOps
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

private val programRunMutex = Mutex()

private class ImageGenerationResult(val codeText: String, val image: ByteArray, val answer: String)

class ProgramGenerationFailed : Exception()
class GenerationParametersChanged(val newParametersString: String) : Exception()

private suspend fun tryGenerateImage(params: GenerationParameters, path: String): ImageGenerationResult {
    val newParamsString = execution(params.toArgsArray(), path)
    val oldParamsString = parametersToStr(path, params.toProgramParameters())
    if (newParamsString == "") {
        throw ProgramGenerationFailed()
    } else if (newParamsString != oldParamsString) {
        throw GenerationParametersChanged(newParamsString)
    } else {
        val (programText, image, answer) = withContext(Dispatchers.IO) {
            val programText = readFile(PROGRAM)
            val answer = readFile("program_result.txt")
            val image = createImage(programText)
            val stream = ByteArrayOutputStream(programText.length)
            @Suppress("BlockingMethodInNonBlockingContext")
            ImageIO.write(image, IMAGE_FORMAT, stream)
            val imageBytes = stream.toByteArray()
            Triple(programText, imageBytes, answer)
        }
        return ImageGenerationResult(programText, image, answer)
    }
}


fun Application.configureNewServer(database: MongoDB) {
    val databaseBackend = VariantsOps(database)


    routing {
        get("/new$PATH_SOURCE") {
            val path = PATH_SOURCE
            val params = parseGenerationParameters(call.request.queryParameters.toMap())
            validateParameters(params)
            var codeText = databaseBackend.getText(params)
            if (codeText != null) {
                return@get call.respondText(codeText, status = HttpStatusCode.OK)
            }
            try {
                codeText = programRunMutex.withLock {
                    val generationResult = tryGenerateImage(params, path)
                    databaseBackend.addVariant(
                        params,
                        generationResult.image,
                        generationResult.codeText,
                        generationResult.answer
                    )
                    generationResult.codeText
                }
                call.respondText(codeText, status = HttpStatusCode.Created)
            } catch (e: ProgramGenerationFailed) {
                call.respond(HttpStatusCode.BadRequest, TEXT___)
            } catch (e: GenerationParametersChanged) {
                call.respondRedirect(e.newParametersString, permanent = false)
            }
        }
        get("/new$PATH_IMAGE_BYTES_PNG") {
            val path = PATH_IMAGE_BYTES_PNG
            val params = parseGenerationParameters(call.request.queryParameters.toMap())
            validateParameters(params)
            var imageBytes = databaseBackend.getImage(params)
            if (imageBytes != null) {
                return@get call.respondBytes(imageBytes, status = HttpStatusCode.OK)
            }
            try {
                imageBytes = programRunMutex.withLock {
                    val generationResult = tryGenerateImage(params, path)
                    databaseBackend.addVariant(
                        params,
                        generationResult.image,
                        generationResult.codeText,
                        generationResult.answer
                    )
                    generationResult.image
                }
                call.respondBytes(imageBytes, status = HttpStatusCode.Created)
            } catch (e: ProgramGenerationFailed) {
                call.respond(HttpStatusCode.BadRequest, TEXT___)
            } catch (e: GenerationParametersChanged) {
                call.respondRedirect(e.newParametersString, permanent = false)
            }
        }
        get("/new$PATH_ANSWER") {
            val path = PATH_ANSWER
            val params = parseGenerationParameters(call.request.queryParameters.toMap())
            validateParameters(params)
            val answer = call.request.queryParameters["answer"] ?: throw ParametersParseError("Excepted answer parameter")
            val result = databaseBackend.getAnswer(params) ?: try {
                programRunMutex.withLock {
                    val generationResult = tryGenerateImage(params, path)
                    databaseBackend.addVariant(
                        params,
                        generationResult.image,
                        generationResult.codeText,
                        generationResult.answer
                    )
                    generationResult.answer
                }
            } catch (e: ProgramGenerationFailed) {
                return@get call.respond(HttpStatusCode.BadRequest, TEXT___)
            } catch (e: GenerationParametersChanged) {
                return@get call.respondRedirect(e.newParametersString, permanent = false)
            }
            val math = checkAnswer_(stringParser(result), stringParser(answer))
            val response = if (result == answer) {
                call.response.status(HttpStatusCode.OK)
                AnswerResponse(math, "Correct solution: $math% correctness")
            } else {
                call.response.status(HttpStatusCode.MultipleChoices)
                AnswerResponse(math, "Incorrect solution: $math% correctness")
            }
            call.respond(response)
        }

    }
}