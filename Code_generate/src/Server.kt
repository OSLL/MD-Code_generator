package com.example

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.lang.Integer.parseInt

val TEXT = "Введите в адресную строку входные данные для задания\n" +
        "Используйте URL код для символов: + - %2b и & - %26\n" +
        "Например: \n" +
        "/get_source?task=1&rand_seed=19&variables_num=1&statements_num=4&arguments_num=3&printf_num=2&redefinition_var=1&operations=<<,>>,|,%26,*,%2b\n" +
        "/get_source?task=2&rand_seed=1&variables_num=4&arguments_num=4&printf_num=6&nesting_level=4\n" +
        "/get_source?task=4&rand_seed=13&variables_num=6&arguments_num=4&printf_num=7&nesting_level=3\n" +
        "/get_source?task=5&rand_seed=12&variables_num=4&arguments_num=3&printf_num=4&nesting_level=3\n" +
        "/get_source?task=6&rand_seed=18&variables_num=6&arguments_num=3&printf_num=5&nesting_level=3&size=2\n"
val TEXT_ = "Ошибка ввода. Попробуйте снова.\n\n"
val TEXT__ = "Задача пока находится в разработке, попробуйте другой тип задач.\n\n"

class Server {
    constructor() {
        val server = embeddedServer(Netty, port = 8080) {
            routing {
                get("/") {
                    call.respondText("$TEXT")
                }
                get("/get_source") {
                    val task: String? = call.request.queryParameters["task"]
                    val rand_seed: String? = call.request.queryParameters["rand_seed"]
                    val variables_num: String? = call.request.queryParameters["variables_num"]
                    val statements_num: String? = call.request.queryParameters["statements_num"]
                    val arguments_num: String? = call.request.queryParameters["arguments_num"]
                    val printf_num: String? = call.request.queryParameters["printf_num"]
                    val redefinition_var: String? = call.request.queryParameters["redefinition_var"]
                    val operations: String? = call.request.queryParameters["operations"]
                    val nesting_level: String? = call.request.queryParameters["nesting_level"]
                    val size: String? = call.request.queryParameters["size"]

                    if (task.toString() != "null") {
                        when (parseInt(task)) {
                            1 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null" || redefinition_var.toString() == "null" || operations.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else {
                                    val parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), redefinition_var.toString(), operations.toString())
                                    val generator = Generator(parameters)
                                    call.respondText("${generator.programGenerate().joinToString("")}")
//                                    call.respondText("$TEXT__$TEXT")
                                }
                            }
                            6 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null" || nesting_level.toString() == "null" || size.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else {
                                    val parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), printf_num.toString(), nesting_level.toString(), size.toString())
                                    val generator = Generator(parameters)
                                    call.respondText("${generator.programGenerate().joinToString("")}")
                                }
                            }
                            else -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else {
                                    val parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), printf_num.toString(), nesting_level.toString())
                                    val generator = Generator(parameters)
                                    call.respondText("${generator.programGenerate().joinToString("")}")
                                }
                            }
                        }
                    }
                    else {
                        call.respondText("$TEXT_$TEXT")
                    }
                }
            }
        }
        server.start(wait = true)
    }
}