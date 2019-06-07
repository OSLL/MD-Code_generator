package com.example

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

class Server {
    fun main() {
        val args_: MutableList<String> = mutableListOf()
        val server = embeddedServer(Netty, port = 8080) {
            routing {
                get("/") {
                    val args: String? = call.request.queryParameters["args"] // To access a single parameter (first one if repeated)
                    args_.addAll(args.toString().split(','))
                    if (args_[0] != "null")
                        call.respondText("${printFun(args_).joinToString("")}")
                    else {
                        call.respondText("Введите в адресную строку входные данные для задания\n" +
                                "Используйте URL код для символов: + - %2b и & - %26\n" +
                                "Например: \n" +
                                "args=1,19,1,4,3,2,1,<<,>>,|,*,+,&\n" +
                                "args=2,1,4,6,4\n" +
                                "args=4,13,6,7,3\n" +
                                "args=5,12,4,4,3\n" +
                                "args=6,18,6,5,3\n")
                    }
                    args_.clear()
                }
            }
        }
        server.start(wait = true)
    }
}