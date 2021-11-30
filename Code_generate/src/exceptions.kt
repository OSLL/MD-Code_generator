package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureExceptions() {
    install(StatusPages) {
        exception<ValidationError> { cause ->
            call.respondText(cause.message ?: "", status = HttpStatusCode.BadRequest)
        }
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}

open class ValidationError(message: String) : Exception(message)

class IncorrectTaskDataError(message: String) : ValidationError(message)

class ParametersParseError(message: String) : ValidationError(message)
