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

val NUMBER_TASKS = 9
val TEXT = "Введите в адресную строку входные данные для задания\n" +
        "Используйте URL код для символов: + - %2b и & - %26\n" +
        "Например: \n" +
        "/get_source?task=1&rand_seed=variant_2&variables_num=6&statements_num=7&arguments_num=13&printf_num=3&redefinition_var=1&operations=|,%26,*,%2b\n" +
        "/get_source?task=2&rand_seed=variant_1&variables_num=4&arguments_num=4&if_num=6&nesting_level=4\n" +
        "/get_source?task=3&rand_seed=variant_7&variables_num=8&arguments_num=7&switch_num=7&case_num=6&nesting_level=3\n" +
        "/get_source?task=4&rand_seed=variant_13&variables_num=6&arguments_num=4&while_num=7&nesting_level=3\n" +
        "/get_source?task=5&rand_seed=variant_1&variables_num=4&arguments_num=3&do_while_num=4&nesting_level=3\n" +
        "/get_source?task=6&rand_seed=variant_18&variables_num=6&arguments_num=3&for_num=5&nesting_level=3\n" +
        "/get_source?task=7&rand_seed=variant_10&variables_num=10&arguments_num=5&if_num=2&switch_num=3&case_num=3&while_num=2&do_while_num=1&for_num=2&nesting_level=3\n" +
        "/get_source?task=8&rand_seed=variant_3&variables_num=6&statements_num=7&arguments_num=5&printf_num=7\n" +
        "/get_source?task=9&rand_seed=variant_0&variables_num=3&statements_num=7&arguments_num=4&printf_num=5\n\n\n" +

        "или: \n" +
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

                    if (checkData(task, rand_seed)) {
                        val args_: MutableList<String> = mutableListOf()
                        var parameters = ProgramParameters()
                        if (dataNotCorrect(task, variables_num, statements_num, arguments_num, printf_num, redefinition_var, operations, if_num, switch_num, case_num, while_num, do_while_num, for_num, nesting_level))
                            call.respondText("$TEXT_$TEXT")
                        else {
                            args_.addAll(returnArgs_(task, rand_seed, variables_num, statements_num, arguments_num, printf_num, redefinition_var, operations, if_num, switch_num, case_num, while_num, do_while_num, for_num, nesting_level))
                            parameters = ProgramParameters(args_)
                        }

                        var v_number = ""
                        var number = 0
                        var count = 15

                        var generator = Generator(parameters)
                        generator.programGenerate()

                        if (!generator.runtime()) {
                            for (i in (TEMPLATE.length)..(rand_seed.toString().length - 1))
                                v_number = "$v_number${rand_seed.toString()[i]}"
                            number = parseInt(v_number)
                        }

                        while (!generator.runtime() && count != 0) {
                            count--
                            number++
                            rand_seed = "$TEMPLATE${number}"
                            args_[1] = rand_seed.toString()
//                            println(args_)

                            parameters = ProgramParameters(args_)
                            generator = Generator(parameters)
                            generator.programGenerate()
                            if (generator.runtime())
                                return@get call.respondRedirect(newData("/get_source", task, rand_seed, variables_num, statements_num, arguments_num, printf_num, redefinition_var, operations, if_num, switch_num, case_num, while_num, do_while_num, for_num, nesting_level), permanent = false)
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

                    if (checkData(task, rand_seed)) {
                        val args_: MutableList<String> = mutableListOf()
                        var parameters = ProgramParameters()
                        if (dataNotCorrect(task, variables_num, statements_num, arguments_num, printf_num, redefinition_var, operations, if_num, switch_num, case_num, while_num, do_while_num, for_num, nesting_level))
                            call.respondText("$TEXT_$TEXT")
                        else {
                            args_.addAll(returnArgs_(task, rand_seed, variables_num, statements_num, arguments_num, printf_num, redefinition_var, operations, if_num, switch_num, case_num, while_num, do_while_num, for_num, nesting_level))
                            parameters = ProgramParameters(args_)
                        }

                        var v_number = ""
                        var number = 0
                        var count = 15

                        var generator = Generator(parameters)
                        generator.programGenerate()

                        if (!generator.runtime()) {
                            for (i in (TEMPLATE.length)..(rand_seed.toString().length - 1))
                                v_number = "$v_number${rand_seed.toString()[i]}"
                            number = parseInt(v_number)
                        }

                        while (!generator.runtime() && count != 0) {
                            count--
                            number++
                            rand_seed = "$TEMPLATE${number}"
                            args_[1] = rand_seed.toString()
//                            println(args_)

                            parameters = ProgramParameters(args_)
                            generator = Generator(parameters)
                            generator.programGenerate()
                            if (generator.runtime())
                                return@get call.respondRedirect(newData("/get_image", task, rand_seed, variables_num, statements_num, arguments_num, printf_num, redefinition_var, operations, if_num, switch_num, case_num, while_num, do_while_num, for_num, nesting_level), permanent = false)
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

                    if (checkData(task, rand_seed, answer)) {
                        if (dataNotCorrect(task, variables_num, statements_num, arguments_num, printf_num, redefinition_var, operations, if_num, switch_num, case_num, while_num, do_while_num, for_num, nesting_level))
                            call.respondText("$TEXT_$TEXT")

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

    fun checkData(task: String?, rand_seed: String?) = (task.toString() != "null" && 0 < parseInt(task.toString()) && parseInt(task.toString()) < NUMBER_TASKS && rand_seed.toString() != "null" && rand_seed.toString().length > 8 && rand_seed.toString().contains(TEMPLATE))

    fun checkData(task: String?, rand_seed: String?, answer: String?) = (task.toString() != "null" && 0 < parseInt(task.toString()) && parseInt(task.toString()) < NUMBER_TASKS && answer.toString() != "null" && rand_seed.toString() != "null" && rand_seed.toString().length > 8 && rand_seed.toString().contains(TEMPLATE))

    fun dataNotCorrect(task: String?, variables_num: String?, statements_num: String?, arguments_num: String?, printf_num: String?, redefinition_var: String?, operations: String?, if_num: String?, switch_num: String?, case_num: String?, while_num: String?, do_while_num: String?, for_num: String?, nesting_level: String?): Boolean {
        when (parseInt(task)) {
            1 -> return (variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null" || redefinition_var.toString() == "null" || operations.toString() == "null")
            2 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || if_num.toString() == "null" || nesting_level.toString() == "null")
            3 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || switch_num.toString() == "null" || case_num.toString() == "null" || nesting_level.toString() == "null")
            4 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || while_num.toString() == "null" || nesting_level.toString() == "null")
            5 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || do_while_num.toString() == "null" || nesting_level.toString() == "null")
            6 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || for_num.toString() == "null" || nesting_level.toString() == "null")
            7 -> return (!(variables_num.toString() != "null" && arguments_num.toString() != "null" && nesting_level.toString() != "null" && (if_num.toString() != "null" || (switch_num.toString() != "null" && case_num.toString() != "null") || while_num.toString() != "null" || do_while_num.toString() != "null" || for_num != "null")))
            8 -> return (variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null")
            9 -> return (variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null")
        }
        return true
    }

    fun returnArgs_(task: String?, rand_seed: String?, variables_num: String?, statements_num: String?, arguments_num: String?, printf_num: String?, redefinition_var: String?, operations: String?, if_num: String?, switch_num: String?, case_num: String?, while_num: String?, do_while_num: String?, for_num: String?, nesting_level: String?): MutableList<String> {
        val args_: MutableList<String> = mutableListOf()
        when (parseInt(task)) {
            1 -> {
                args_.add(task.toString())
                args_.add(rand_seed.toString())
                args_.add(variables_num.toString())
                args_.add(statements_num.toString())
                args_.add(arguments_num.toString())
                args_.add(printf_num.toString())
                args_.add(redefinition_var.toString())
                args_.addAll(operations.toString().split(','))

//                parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), redefinition_var.toString(), operations.toString())
            }
            2 -> {
                args_.add(task.toString())
                args_.add(rand_seed.toString())
                args_.add(variables_num.toString())
                args_.add(arguments_num.toString())
                args_.add(if_num.toString())
                args_.add(nesting_level.toString())

//                parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), nesting_level.toString())
            }
            3 -> {
                args_.add(task.toString())
                args_.add(rand_seed.toString())
                args_.add(variables_num.toString())
                args_.add(arguments_num.toString())
                args_.add(switch_num.toString())
                args_.add(case_num.toString())
                args_.add(nesting_level.toString())

//                parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), switch_num.toString(), case_num.toString(), nesting_level.toString())
            }
            4 -> {
                args_.add(task.toString())
                args_.add(rand_seed.toString())
                args_.add(variables_num.toString())
                args_.add(arguments_num.toString())
                args_.add(while_num.toString())
                args_.add(nesting_level.toString())

//                parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), while_num.toString(), nesting_level.toString())
            }
            5 -> {
                args_.add(task.toString())
                args_.add(rand_seed.toString())
                args_.add(variables_num.toString())
                args_.add(arguments_num.toString())
                args_.add(do_while_num.toString())
                args_.add(nesting_level.toString())

//                parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), do_while_num.toString(), nesting_level.toString())
            }
            6 -> {
                args_.add(task.toString())
                args_.add(rand_seed.toString())
                args_.add(variables_num.toString())
                args_.add(arguments_num.toString())
                args_.add(for_num.toString())
                args_.add(nesting_level.toString())

//                parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), for_num.toString(), nesting_level.toString())
            }
            7 -> {
                args_.add(task.toString())
                args_.add(rand_seed.toString())
                args_.add(variables_num.toString())
                args_.add(arguments_num.toString())
                args_.add(nesting_level.toString())
                if (if_num.toString() != "null") args_.add(if_num.toString())
                else args_.add("0")
                if (switch_num.toString() != "null") args_.add(switch_num.toString())
                else args_.add("0")
                if (case_num.toString() != "null") args_.add(case_num.toString())
                else args_.add("0")
                if (while_num.toString() != "null") args_.add(while_num.toString())
                else args_.add("0")
                if (do_while_num.toString() != "null") args_.add(do_while_num.toString())
                else args_.add("0")
                if (for_num.toString() != "null") args_.add(for_num.toString())
                else args_.add("0")

//                parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), if_num.toString(), switch_num.toString(), case_num.toString(), while_num.toString(), do_while_num.toString(), for_num.toString(), nesting_level.toString())
            }
            8 -> {
                args_.add(task.toString())
                args_.add(rand_seed.toString())
                args_.add(variables_num.toString())
                args_.add(statements_num.toString())
                args_.add(arguments_num.toString())
                args_.add(printf_num.toString())

//                parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), true)
            }
            9 -> {
                args_.add(task.toString())
                args_.add(rand_seed.toString())
                args_.add(variables_num.toString())
                args_.add(statements_num.toString())
                args_.add(arguments_num.toString())
                args_.add(printf_num.toString())

//                parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), statements_num.toString(), arguments_num.toString(), printf_num.toString(), true)
            }
        }
        return args_
    }

    fun newData(str_: String, task: String?, rand_seed: String?, variables_num: String?, statements_num: String?, arguments_num: String?, printf_num: String?, redefinition_var: String?, operations: String?, if_num: String?, switch_num: String?, case_num: String?, while_num: String?, do_while_num: String?, for_num: String?, nesting_level: String?): String {
        var str = "${str_}"
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
        return str
    }
}