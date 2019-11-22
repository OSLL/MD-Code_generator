package com.example
import io.ktor.http.content.*

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*
import java.io.File
import java.lang.Integer.parseInt

val TEXT = "Введите в адресную строку входные данные для задания\n" +
        "Используйте URL код для символов: + - %2b и & - %26\n" +
        "Например: \n" +
        "/get_image?task=1&rand_seed=variant_2&variables_num=6&statements_num=7&arguments_num=13&printf_num=3&redefinition_var=1&operations=|,%26,*,%2b\n" +
        "/get_image?task=2&rand_seed=variant_1&variables_num=4&arguments_num=4&if_num=6&nesting_level=4\n" +
        "/get_image?task=3&rand_seed=variant_7&variables_num=8&arguments_num=7&switch_num=7&case_num=6&nesting_level=3\n" +
        "/get_image?task=4&rand_seed=variant_13&variables_num=6&arguments_num=4&while_num=7&nesting_level=3\n" +
        "/get_image?task=5&rand_seed=variant_1&variables_num=4&arguments_num=3&do_while_num=4&nesting_level=3\n" +
        "/get_image?task=6&rand_seed=variant_18&variables_num=6&arguments_num=3&for_num=5&nesting_level=3\n" +
        "/get_image?task=7&rand_seed=variant_10&variables_num=10&arguments_num=5&if_num=2&switch_num=3&case_num=3&while_num=2&do_while_num=1&for_num=2&nesting_level=3\n" +
        "/get_image?task=8&rand_seed=variant_3&variables_num=6&statements_num=7&arguments_num=5&printf_num=7\n" +
        "/get_image?task=9&rand_seed=variant_0&variables_num=3&statements_num=7&arguments_num=4&printf_num=5\n"

val TEXT_ = "Ошибка ввода. Попробуйте снова.\n\n"
val TEXT__ = "Задача пока находится в разработке, попробуйте другой тип задач.\n\n"
val TEXT___ = "Программа не может сгенерировать корректную программу на заданным наборе данных. Попробуйте другой набор входных данных.\n\n"
val TEMPLATE = "variant_"

class Server {
    constructor() {
        val server = embeddedServer(Netty, port = 8080) {
            routing {
                static("") {
                    file("saved.png")
                    default("index.html")
                }

                get("/") {
                    call.respondText("$TEXT")
                }
                get("/get_source") {
                    val task: String? = call.request.queryParameters["task"]
                    var rand_seed: String? = call.request.queryParameters["rand_seed"]
                    val variables_num: String? = call.request.queryParameters["variables_num"]
                    val statements_num: String? = call.request.queryParameters["statements_num"]
                    val arguments_num: String? = call.request.queryParameters["arguments_num"]
                    val printf_num: String? = call.request.queryParameters["printf_num"]
                    val redefinition_var: String? = call.request.queryParameters["redefinition_var"]
                    val operations: String? = call.request.queryParameters["operations"]
                    val if_num: String? = call.request.queryParameters["if_num"]
                    val switch_num: String? = call.request.queryParameters["switch_num"]
                    val case_num: String? = call.request.queryParameters["case_num"]
                    val while_num: String? = call.request.queryParameters["while_num"]
                    val do_while_num: String? = call.request.queryParameters["do_while_num"]
                    val for_num: String? = call.request.queryParameters["for_num"]
                    val nesting_level: String? = call.request.queryParameters["nesting_level"]

                    if (task.toString() != "null" && 0 < parseInt(task.toString()) && parseInt(task.toString()) < 10 &&
                        rand_seed.toString() != "null" && rand_seed.toString().length > 8 && rand_seed.toString().contains(TEMPLATE)) {
                        var parameters: ProgramParameters = ProgramParameters()
                        when (parseInt(task)) {
                            1 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null" || redefinition_var.toString() == "null" || operations.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), redefinition_var.toString(), operations.toString())
                            }
                            2 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || if_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), nesting_level.toString())
                            }
                            3 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || switch_num.toString() == "null" || case_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), switch_num.toString(), case_num.toString(), nesting_level.toString())
                            }
                            4 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || while_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), while_num.toString(), nesting_level.toString())
                            }
                            5 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || do_while_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), do_while_num.toString(), nesting_level.toString())
                            }
                            6 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || for_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), for_num.toString(), nesting_level.toString())
                            }
                            7 -> {
                                if (rand_seed.toString() != "null" && variables_num.toString() != "null" && arguments_num.toString() != "null" && nesting_level.toString() != "null"
                                    && (if_num.toString() != "null" || (switch_num.toString() != "null" && case_num.toString() != "null") || while_num.toString() != "null" || do_while_num.toString() != "null" || for_num != "null"))
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), switch_num.toString(), case_num.toString(), while_num.toString(), do_while_num.toString(), for_num.toString(), nesting_level.toString())
                                else call.respondText("$TEXT_$TEXT")
                            }
                            8 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), true)
                            }
                            9 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), true)
                            }
                        }
                        var v_number = ""
                        for (i in (TEMPLATE.length)..(rand_seed.toString().length - 1)) v_number = "$v_number${rand_seed.toString()[i]}"
                        var number = parseInt(v_number)
                        var count = 15

                        var generator = Generator(parameters)
                        generator.programGenerate()

                        while (!generator.runtime() && count != 0) {
                            count--
                            rand_seed = "$TEMPLATE${++number}"

                            when (parseInt(task)) {
                                1 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), redefinition_var.toString(), operations.toString())
                                2 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), nesting_level.toString())
                                3 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), switch_num.toString(), case_num.toString(), nesting_level.toString())
                                4 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), while_num.toString(), nesting_level.toString())
                                5 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), do_while_num.toString(), nesting_level.toString())
                                6 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), for_num.toString(), nesting_level.toString())
                                7 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), switch_num.toString(), case_num.toString(), while_num.toString(), do_while_num.toString(), for_num.toString(), nesting_level.toString())
                            }
                            generator = Generator(parameters)
                            generator.programGenerate()
                            if (generator.runtime()) {
                                var str = "/get_source"
                                if (task.toString() != "null")              str = "$str?task=${task.toString()}"
                                if (rand_seed.toString() != "null")         str = "$str&rand_seed=${rand_seed.toString()}"
                                if (variables_num.toString() != "null")     str = "$str&variables_num=${variables_num.toString()}"
                                if (statements_num.toString() != "null")    str = "$str&statements_num=${statements_num.toString()}"
                                if (arguments_num.toString() != "null")     str = "$str&arguments_num=${arguments_num.toString()}"
                                if (printf_num.toString() != "null")        str = "$str&printf_num=${printf_num.toString()}"
                                if (redefinition_var.toString() != "null")  str = "$str&redefinition_var=${redefinition_var.toString()}"
                                if (operations.toString() != "null")        str = "$str&operations=${operations.toString()}"
                                if (if_num.toString() != "null")            str = "$str&if_num=${if_num.toString()}"
                                if (switch_num.toString() != "null")        str = "$str&switch_num=${switch_num.toString()}"
                                if (case_num.toString() != "null")          str = "$str&case_num=${case_num.toString()}"
                                if (while_num.toString() != "null")         str = "$str&while_num=${while_num.toString()}"
                                if (do_while_num.toString() != "null")      str = "$str&do_while_num=${do_while_num.toString()}"
                                if (for_num.toString() != "null")           str = "$str&for_num=${for_num.toString()}"
                                if (nesting_level.toString() != "null")     str = "$str&nesting_level=${nesting_level.toString()}"

                                return@get call.respondRedirect(str, permanent = false)
                            }
                        }
                        if (generator.runtime()) {
                            val func = readFile("program.c")
                            call.respondText("$func")
                        }
                        else call.respondText("$TEXT___$TEXT")
                    }
                    else call.respondText("$TEXT_$TEXT")
                }
                get("/get_image") {
                    val task: String? = call.request.queryParameters["task"]
                    var rand_seed: String? = call.request.queryParameters["rand_seed"]
                    val variables_num: String? = call.request.queryParameters["variables_num"]
                    val statements_num: String? = call.request.queryParameters["statements_num"]
                    val arguments_num: String? = call.request.queryParameters["arguments_num"]
                    val printf_num: String? = call.request.queryParameters["printf_num"]
                    val redefinition_var: String? = call.request.queryParameters["redefinition_var"]
                    val operations: String? = call.request.queryParameters["operations"]
                    val if_num: String? = call.request.queryParameters["if_num"]
                    val switch_num: String? = call.request.queryParameters["switch_num"]
                    val case_num: String? = call.request.queryParameters["case_num"]
                    val while_num: String? = call.request.queryParameters["while_num"]
                    val do_while_num: String? = call.request.queryParameters["do_while_num"]
                    val for_num: String? = call.request.queryParameters["for_num"]
                    val nesting_level: String? = call.request.queryParameters["nesting_level"]

                    if (task.toString() != "null" && 0 < parseInt(task.toString()) && parseInt(task.toString()) < 10 &&
                        rand_seed.toString() != "null" && rand_seed.toString().length > 8 && rand_seed.toString().contains(TEMPLATE)) {
                        var parameters: ProgramParameters = ProgramParameters()
                        when (parseInt(task)) {
                            1 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null" || redefinition_var.toString() == "null" || operations.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), redefinition_var.toString(), operations.toString())
                            }
                            2 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || if_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), nesting_level.toString())
                            }
                            3 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || switch_num.toString() == "null" || case_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), switch_num.toString(), case_num.toString(), nesting_level.toString())
                            }
                            4 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || while_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), while_num.toString(), nesting_level.toString())
                            }
                            5 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || do_while_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), do_while_num.toString(), nesting_level.toString())
                            }
                            6 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || for_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), for_num.toString(), nesting_level.toString())
                            }
                            7 -> {
                                if (rand_seed.toString() != "null" && variables_num.toString() != "null" && arguments_num.toString() != "null" && nesting_level.toString() != "null"
                                    && (if_num.toString() != "null" || (switch_num.toString() != "null" && case_num.toString() != "null") || while_num.toString() != "null" || do_while_num.toString() != "null" || for_num != "null"))
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), switch_num.toString(), case_num.toString(), while_num.toString(), do_while_num.toString(), for_num.toString(), nesting_level.toString())
                                else call.respondText("$TEXT_$TEXT")
                            }
                            8 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), true)
                            }
                            9 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                                else
                                    parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), true)
                            }
                        }
                        var v_number = ""
                        for (i in (TEMPLATE.length)..(rand_seed.toString().length - 1)) v_number = "$v_number${rand_seed.toString()[i]}"
                        var number = parseInt(v_number)
                        var count = 15

                        var generator = Generator(parameters)
                        generator.programGenerate()

                        while (!generator.runtime() && count != 0) {
                            count--
                            rand_seed = "$TEMPLATE${++number}"

                            when (parseInt(task)) {
                                1 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), redefinition_var.toString(), operations.toString())
                                2 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), nesting_level.toString())
                                3 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), switch_num.toString(), case_num.toString(), nesting_level.toString())
                                4 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), while_num.toString(), nesting_level.toString())
                                5 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), do_while_num.toString(), nesting_level.toString())
                                6 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), for_num.toString(), nesting_level.toString())
                                7 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), switch_num.toString(), case_num.toString(), while_num.toString(), do_while_num.toString(), for_num.toString(), nesting_level.toString())
                                8 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), true)
                                9 -> parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), true)
                            }
                            generator = Generator(parameters)
                            generator.programGenerate()
                            if (generator.runtime()) {
                                var str = "/get_image"
                                if (task.toString() != "null")              str = "$str?task=${task.toString()}"
                                if (rand_seed.toString() != "null")         str = "$str&rand_seed=${rand_seed.toString()}"
                                if (variables_num.toString() != "null")     str = "$str&variables_num=${variables_num.toString()}"
                                if (statements_num.toString() != "null")    str = "$str&statements_num=${statements_num.toString()}"
                                if (arguments_num.toString() != "null")     str = "$str&arguments_num=${arguments_num.toString()}"
                                if (printf_num.toString() != "null")        str = "$str&printf_num=${printf_num.toString()}"
                                if (redefinition_var.toString() != "null")  str = "$str&redefinition_var=${redefinition_var.toString()}"
                                if (operations.toString() != "null")        str = "$str&operations=${operations.toString()}"
                                if (if_num.toString() != "null")            str = "$str&if_num=${if_num.toString()}"
                                if (switch_num.toString() != "null")        str = "$str&switch_num=${switch_num.toString()}"
                                if (case_num.toString() != "null")          str = "$str&case_num=${case_num.toString()}"
                                if (while_num.toString() != "null")         str = "$str&while_num=${while_num.toString()}"
                                if (do_while_num.toString() != "null")      str = "$str&do_while_num=${do_while_num.toString()}"
                                if (for_num.toString() != "null")           str = "$str&for_num=${for_num.toString()}"
                                if (nesting_level.toString() != "null")     str = "$str&nesting_level=${nesting_level.toString()}"

                                return@get call.respondRedirect(str, permanent = false)
                            }
                        }
                        if (generator.runtime()) {
                            Image(readFile("program.c"))
                            call.respondHtml {
                                body {
                                    p {
                                        img(src = "../saved.png", alt = "qwe")
                                    }
                                }
                            }
//                            val func = readFile("program.c")
//                            call.respondText("")
                        }
                        else call.respondText("$TEXT___$TEXT")
                    }
                    else call.respondText("$TEXT_$TEXT")
                }
                get("/check_answer") {
                    val task: String? = call.request.queryParameters["task"]
                    val rand_seed: String? = call.request.queryParameters["rand_seed"]
                    val variables_num: String? = call.request.queryParameters["variables_num"]
                    val statements_num: String? = call.request.queryParameters["statements_num"]
                    val arguments_num: String? = call.request.queryParameters["arguments_num"]
                    val printf_num: String? = call.request.queryParameters["printf_num"]
                    val redefinition_var: String? = call.request.queryParameters["redefinition_var"]
                    val operations: String? = call.request.queryParameters["operations"]
                    val if_num: String? = call.request.queryParameters["if_num"]
                    val switch_num: String? = call.request.queryParameters["switch_num"]
                    val case_num: String? = call.request.queryParameters["case_num"]
                    val while_num: String? = call.request.queryParameters["while_num"]
                    val do_while_num: String? = call.request.queryParameters["do_while_num"]
                    val for_num: String? = call.request.queryParameters["for_num"]
                    val nesting_level: String? = call.request.queryParameters["nesting_level"]
                    val answer: String? = call.request.queryParameters["answer"]

                    if (task.toString() != "null" && 0 < parseInt(task.toString()) && parseInt(task.toString()) < 8 && answer.toString() != "null"
                        && rand_seed.toString() != "null" && rand_seed.toString().length > 8 && rand_seed.toString().contains(TEMPLATE)) {
                        when (parseInt(task)) {
                            1 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null" || redefinition_var.toString() == "null" || operations.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                            }
                            2 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || if_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                            }
                            3 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || switch_num.toString() == "null" || case_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                            }
                            4 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || while_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                            }
                            5 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || do_while_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                            }
                            6 -> {
                                if (rand_seed.toString() == "null" || variables_num.toString() == "null" || arguments_num.toString() == "null" || for_num.toString() == "null" || nesting_level.toString() == "null")
                                    call.respondText("$TEXT_$TEXT")
                            }
                            7 -> {
                                if (!(rand_seed.toString() != "null" && variables_num.toString() != "null" && arguments_num.toString() != "null" && nesting_level.toString() != "null"
                                    && (if_num.toString() != "null" || (switch_num.toString() != "null" && case_num.toString() != "null") || while_num.toString() != "null" || do_while_num.toString() != "null" || for_num != "null")))
                                    call.respondText("$TEXT_$TEXT")
                            }
                        }

//                        val func = readFile("func.c")
                        var result = readFile("program_result.txt")
                        result = result.replace(CARRIAGE_RETURN, "")
                        var code : HttpStatusCode

                        if (result == answer) code = HttpStatusCode.OK
                        else code = HttpStatusCode.MultipleChoices
                        call.response.status(code)
                        call.respondText("${code.value}\n\nresult:\n$result\n\nanswer:\n$answer")
                    }
                    else call.respondText("$TEXT_$TEXT")
                }
            }
        }
        server.start(wait = true)
    }

    fun readFile(fileName: String) = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)

}