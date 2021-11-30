package com.example

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.html.body
import kotlinx.html.img
import kotlinx.html.p
import kotlinx.serialization.Serializable
import java.io.File
import java.lang.Integer.parseInt
import javax.imageio.ImageIO

const val PATH_SOURCE = "/get_source"
const val PATH_IMAGE = "/get_image"
const val PATH_ANSWER = "/check_answer"
const val PATH_IMAGE_BYTES_PNG = "/get_image_bytes_png"

const val NUMBER_TASKS = 11
const val TEXT = "Введите в адресную строку входные данные для задания\n" +
        "Используйте URL код для символов: + - %2b и & - %26\n" +
        "Например: \n" +
        "$PATH_SOURCE?task=1&rand_seed=variant_2&variables_num=6&statements_num=7&arguments_num=7&printf_num=3&redefinition_var=1&operations=|,%26,*,%2b\n" +
        "$PATH_SOURCE?task=2&rand_seed=variant_1&variables_num=4&arguments_num=4&if_num=6&nesting_level=4\n" +
        "$PATH_SOURCE?task=3&rand_seed=variant_7&variables_num=8&arguments_num=7&switch_num=7&case_num=6&nesting_level=3\n" +
        "$PATH_SOURCE?task=4&rand_seed=variant_13&variables_num=6&arguments_num=4&while_num=7&nesting_level=3\n" +
        "$PATH_SOURCE?task=5&rand_seed=variant_1&variables_num=4&arguments_num=3&do_while_num=4&nesting_level=3\n" +
        "$PATH_SOURCE?task=6&rand_seed=variant_18&variables_num=6&arguments_num=3&for_num=5&nesting_level=3\n" +
        "$PATH_SOURCE?task=7&rand_seed=variant_10&variables_num=10&arguments_num=5&if_num=2&switch_num=3&case_num=3&while_num=2&do_while_num=1&for_num=2&nesting_level=3\n" +
        "$PATH_SOURCE?task=8&rand_seed=variant_3&variables_num=6&statements_num=7&arguments_num=5&printf_num=7\n" +
        "$PATH_SOURCE?task=9&rand_seed=variant_0&arrays_num=3&array_size=4&statements_num=7&printf_num=5\n" +
        "$PATH_SOURCE?task=10&rand_seed=variant_2&variables_num=6&arguments_num=3&printf_num=3\n\n\n" +

        "или: \n" +
        "$PATH_IMAGE?task=1&rand_seed=variant_2&variables_num=6&statements_num=7&arguments_num=7&printf_num=3&redefinition_var=1&operations=|,%26,*,%2b\n" +
        "$PATH_IMAGE?task=2&rand_seed=variant_1&variables_num=4&arguments_num=4&if_num=6&nesting_level=4\n" +
        "$PATH_IMAGE?task=3&rand_seed=variant_7&variables_num=8&arguments_num=7&switch_num=7&case_num=6&nesting_level=3\n" +
        "$PATH_IMAGE?task=4&rand_seed=variant_13&variables_num=6&arguments_num=4&while_num=7&nesting_level=3\n" +
        "$PATH_IMAGE?task=5&rand_seed=variant_1&variables_num=4&arguments_num=3&do_while_num=4&nesting_level=3\n" +
        "$PATH_IMAGE?task=6&rand_seed=variant_18&variables_num=6&arguments_num=3&for_num=5&nesting_level=3\n" +
        "$PATH_IMAGE?task=7&rand_seed=variant_10&variables_num=10&arguments_num=5&if_num=2&switch_num=3&case_num=3&while_num=2&do_while_num=1&for_num=2&nesting_level=3\n" +
        "$PATH_IMAGE?task=8&rand_seed=variant_3&variables_num=6&statements_num=7&arguments_num=5&printf_num=7\n" +
        "$PATH_IMAGE?task=9&rand_seed=variant_0&arrays_num=3&array_size=4&statements_num=7&printf_num=5\n" +
        "$PATH_IMAGE?task=10&rand_seed=variant_2&variables_num=6&arguments_num=3&printf_num=3\n"

const val INPUT_ERROR_TEXT = "Ошибка ввода. Попробуйте снова.\n\n"
const val TASK_IN_DEVELOPMENT_TEXT = "Задача пока находится в разработке, попробуйте другой тип задач.\n\n"
const val TEXT___ =
    "Программа не может сгенерировать корректную программу на заданным наборе данных. Попробуйте другой набор входных данных.\n\n"
const val TEMPLATE = "variant_"
const val ALT_TEXT = "There should be an image, but something went wrong. Please try another request."

@Serializable
data class AnswerResponse(val correctnessPercentage: Int, val message: String)

fun Application.configureServer() {
    routing {
        static("") {
            file(IMAGE)
            default("index.html")
        }
        get("/") {
            call.respondText("$TEXT")
        }
        get(PATH_SOURCE) {
            val path = PATH_SOURCE
            var task: String? = call.request.queryParameters["task"]
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
            val arrays_num: String? = call.request.queryParameters["arrays_num"]
            val array_size: String? = call.request.queryParameters["array_size"]

            if (checkData(task, rand_seed)) {
                val args_: MutableList<String> = mutableListOf()
                if (dataNotCorrect(
                        task,
                        variables_num,
                        statements_num,
                        arguments_num,
                        printf_num,
                        redefinition_var,
                        operations,
                        if_num,
                        switch_num,
                        case_num,
                        while_num,
                        do_while_num,
                        for_num,
                        nesting_level,
                        arrays_num,
                        array_size
                    )
                )
                    call.respondText("$INPUT_ERROR_TEXT$TEXT")
                else {
                    args_.addAll(
                        returnArgs_(
                            task,
                            rand_seed,
                            variables_num,
                            statements_num,
                            arguments_num,
                            printf_num,
                            redefinition_var,
                            operations,
                            if_num,
                            switch_num,
                            case_num,
                            while_num,
                            do_while_num,
                            for_num,
                            nesting_level,
                            arrays_num,
                            array_size
                        )
                    )
                    val new_str = execution(args_, path)
                    val str_ = dataToStr(
                        path,
                        task,
                        rand_seed,
                        variables_num,
                        statements_num,
                        arguments_num,
                        printf_num,
                        redefinition_var,
                        operations,
                        if_num,
                        switch_num,
                        case_num,
                        while_num,
                        do_while_num,
                        for_num,
                        nesting_level,
                        arrays_num,
                        array_size
                    )
                    if (new_str == "") call.respondText("$TEXT___$TEXT")
                    if (new_str != "" && new_str != str_) return@get call.respondRedirect(new_str, permanent = false)
                    if (new_str == str_) call.respondText("${readFile(PROGRAM)}")
                }
            } else call.respondText("$INPUT_ERROR_TEXT$TEXT")
        }
        get(PATH_IMAGE) {
            val path = PATH_IMAGE
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
            val arrays_num: String? = call.request.queryParameters["arrays_num"]
            val array_size: String? = call.request.queryParameters["array_size"]

            if (checkData(task, rand_seed)) {
                val args_: MutableList<String> = mutableListOf()
                if (dataNotCorrect(
                        task,
                        variables_num,
                        statements_num,
                        arguments_num,
                        printf_num,
                        redefinition_var,
                        operations,
                        if_num,
                        switch_num,
                        case_num,
                        while_num,
                        do_while_num,
                        for_num,
                        nesting_level,
                        arrays_num,
                        array_size
                    )
                )
                    call.respondText("$INPUT_ERROR_TEXT$TEXT")
                else {
                    args_.addAll(
                        returnArgs_(
                            task,
                            rand_seed,
                            variables_num,
                            statements_num,
                            arguments_num,
                            printf_num,
                            redefinition_var,
                            operations,
                            if_num,
                            switch_num,
                            case_num,
                            while_num,
                            do_while_num,
                            for_num,
                            nesting_level,
                            arrays_num,
                            array_size
                        )
                    )
                    val new_str = execution(args_, path)
                    val str_ = dataToStr(
                        path,
                        task,
                        rand_seed,
                        variables_num,
                        statements_num,
                        arguments_num,
                        printf_num,
                        redefinition_var,
                        operations,
                        if_num,
                        switch_num,
                        case_num,
                        while_num,
                        do_while_num,
                        for_num,
                        nesting_level,
                        arrays_num,
                        array_size
                    )
                    if (new_str == "") call.respondText("$TEXT___$TEXT")
                    if (new_str != "" && new_str != str_) return@get call.respondRedirect(new_str, permanent = false)
                    if (new_str == str_) {
                        Image(readFile(PROGRAM))
                        call.respondHtml {
                            body {
                                p { img(src = "../$IMAGE", alt = ALT_TEXT) }
                            }
                        }
                    }
                }
            } else call.respondText("$INPUT_ERROR_TEXT$TEXT")
        }
        get(PATH_IMAGE_BYTES_PNG) {
            val path = PATH_IMAGE_BYTES_PNG
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
            val arrays_num: String? = call.request.queryParameters["arrays_num"]
            val array_size: String? = call.request.queryParameters["array_size"]

            if (checkData(task, rand_seed)) {
                val args_: MutableList<String> = mutableListOf()
                if (dataNotCorrect(
                        task,
                        variables_num,
                        statements_num,
                        arguments_num,
                        printf_num,
                        redefinition_var,
                        operations,
                        if_num,
                        switch_num,
                        case_num,
                        while_num,
                        do_while_num,
                        for_num,
                        nesting_level,
                        arrays_num,
                        array_size
                    )
                )
                    call.respondText("$INPUT_ERROR_TEXT$TEXT")
                else {
                    args_.addAll(
                        returnArgs_(
                            task,
                            rand_seed,
                            variables_num,
                            statements_num,
                            arguments_num,
                            printf_num,
                            redefinition_var,
                            operations,
                            if_num,
                            switch_num,
                            case_num,
                            while_num,
                            do_while_num,
                            for_num,
                            nesting_level,
                            arrays_num,
                            array_size
                        )
                    )
                    val new_str = execution(args_, path)
                    val str_ = dataToStr(
                        path,
                        task,
                        rand_seed,
                        variables_num,
                        statements_num,
                        arguments_num,
                        printf_num,
                        redefinition_var,
                        operations,
                        if_num,
                        switch_num,
                        case_num,
                        while_num,
                        do_while_num,
                        for_num,
                        nesting_level,
                        arrays_num,
                        array_size
                    )
                    if (new_str == "") call.respondText("$TEXT___$TEXT")
                    if (new_str != "" && new_str != str_) return@get call.respondRedirect(new_str, permanent = false)
                    if (new_str == str_) {
                        val image = createImage(readFile(PROGRAM))
                        call.respondOutputStream {
                            withContext(Dispatchers.IO) {
                                @Suppress("BlockingMethodInNonBlockingContext")
                                ImageIO.write(image, IMAGE_FORMAT, this@respondOutputStream)
                            }
                        }
                    }
                }
            } else call.respondText("$INPUT_ERROR_TEXT$TEXT")
        }
        get(PATH_ANSWER) {
            val path = PATH_ANSWER
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
            val arrays_num: String? = call.request.queryParameters["arrays_num"]
            val array_size: String? = call.request.queryParameters["array_size"]

            if (!checkData(task, rand_seed, answer) || dataNotCorrect(
                    task,
                    variables_num,
                    statements_num,
                    arguments_num,
                    printf_num,
                    redefinition_var,
                    operations,
                    if_num,
                    switch_num,
                    case_num,
                    while_num,
                    do_while_num,
                    for_num,
                    nesting_level,
                    arrays_num,
                    array_size
                )
            ) {
                call.respond(HttpStatusCode.BadRequest, INPUT_ERROR_TEXT)
                return@get
            }

            val args_: MutableList<String> = mutableListOf()

            args_.addAll(
                returnArgs_(
                    task,
                    rand_seed,
                    variables_num,
                    statements_num,
                    arguments_num,
                    printf_num,
                    redefinition_var,
                    operations,
                    if_num,
                    switch_num,
                    case_num,
                    while_num,
                    do_while_num,
                    for_num,
                    nesting_level,
                    arrays_num,
                    array_size
                )
            )
            var new_str = execution(args_, path)
            var str_ = dataToStr(
                path,
                task,
                rand_seed,
                variables_num,
                statements_num,
                arguments_num,
                printf_num,
                redefinition_var,
                operations,
                if_num,
                switch_num,
                case_num,
                while_num,
                do_while_num,
                for_num,
                nesting_level,
                arrays_num,
                array_size
            )
            new_str = "$new_str&answer=${answer.toString()}"
            str_ = "$str_&answer=${answer.toString()}"
            if (new_str == "") call.respondText("$TEXT___$TEXT")
            if (new_str != "" && new_str != str_) return@get call.respondRedirect(new_str, permanent = false)
            if (new_str == str_) {
                val answer_ = answer.toString().replace("\\s+".toRegex(), " ")
                val result = readFile("program_result.txt").replace(CARRIAGE_RETURN, "")
                val math = checkAnswer_(stringParser(result), stringParser(answer_))
                val response = if (result == answer_) {
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
}

fun readFile(fileName: String) = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)

fun checkData(task: String?, rand_seed: String?) =
    (task.toString() != "null" && 0 < parseInt(task.toString()) && parseInt(task.toString()) < NUMBER_TASKS && rand_seed.toString() != "null" && rand_seed.toString().length > 8 && rand_seed.toString()
        .contains(TEMPLATE))

fun checkData(task: String?, rand_seed: String?, answer: String?) =
    (task.toString() != "null" && 0 < parseInt(task.toString()) && parseInt(task.toString()) < NUMBER_TASKS && answer.toString() != "null" && rand_seed.toString() != "null" && rand_seed.toString().length > 8 && rand_seed.toString()
        .contains(TEMPLATE))

fun dataNotCorrect(
    task: String?,
    variables_num: String?,
    statements_num: String?,
    arguments_num: String?,
    printf_num: String?,
    redefinition_var: String?,
    operations: String?,
    if_num: String?,
    switch_num: String?,
    case_num: String?,
    while_num: String?,
    do_while_num: String?,
    for_num: String?,
    nesting_level: String?,
    arrays_num: String?,
    array_size: String?
): Boolean {
    when (parseInt(task)) {
        1 -> return (variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null" || redefinition_var.toString() == "null" || operations.toString() == "null")
        2 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || if_num.toString() == "null" || nesting_level.toString() == "null")
        3 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || switch_num.toString() == "null" || case_num.toString() == "null" || nesting_level.toString() == "null")
        4 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || while_num.toString() == "null" || nesting_level.toString() == "null")
        5 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || do_while_num.toString() == "null" || nesting_level.toString() == "null")
        6 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || for_num.toString() == "null" || nesting_level.toString() == "null")
        7 -> return (!(variables_num.toString() != "null" && arguments_num.toString() != "null" && nesting_level.toString() != "null" && (if_num.toString() != "null" || (switch_num.toString() != "null" && case_num.toString() != "null") || while_num.toString() != "null" || do_while_num.toString() != "null" || for_num != "null")))
        8 -> return (variables_num.toString() == "null" || statements_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null")
        9 -> return (arrays_num.toString() == "null" || array_size.toString() == "null" || statements_num.toString() == "null" || printf_num.toString() == "null")
        10 -> return (variables_num.toString() == "null" || arguments_num.toString() == "null" || printf_num.toString() == "null")
    }
    return true
}

fun returnArgs_(
    task: String?,
    rand_seed: String?,
    variables_num: String?,
    statements_num: String?,
    arguments_num: String?,
    printf_num: String?,
    redefinition_var: String?,
    operations: String?,
    if_num: String?,
    switch_num: String?,
    case_num: String?,
    while_num: String?,
    do_while_num: String?,
    for_num: String?,
    nesting_level: String?,
    arrays_num: String?,
    array_size: String?
): MutableList<String> {
    val args_: MutableList<String> = mutableListOf()
    args_.add(task.toString())
    args_.add(rand_seed.toString())
    if (parseInt(task) != 9)
        args_.add(variables_num.toString())
    when (parseInt(task)) {
        1 -> {
            args_.add(statements_num.toString())
            args_.add(arguments_num.toString())
            args_.add(printf_num.toString())
            args_.add(redefinition_var.toString())
            args_.addAll(operations.toString().split(','))
        }
        2 -> {
            args_.add(arguments_num.toString())
            args_.add(if_num.toString())
            args_.add(nesting_level.toString())
        }
        3 -> {
            args_.add(arguments_num.toString())
            args_.add(switch_num.toString())
            args_.add(case_num.toString())
            args_.add(nesting_level.toString())
        }
        4 -> {
            args_.add(arguments_num.toString())
            args_.add(while_num.toString())
            args_.add(nesting_level.toString())
        }
        5 -> {
            args_.add(arguments_num.toString())
            args_.add(do_while_num.toString())
            args_.add(nesting_level.toString())
        }
        6 -> {
            args_.add(arguments_num.toString())
            args_.add(for_num.toString())
            args_.add(nesting_level.toString())
        }
        7 -> {
            args_.add(arguments_num.toString())
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
            args_.add(nesting_level.toString())
        }
        8 -> {
            args_.add(statements_num.toString())
            args_.add(arguments_num.toString())
            args_.add(printf_num.toString())
        }
        9 -> {
            args_.add(arrays_num.toString())
            args_.add(array_size.toString())
            args_.add(statements_num.toString())
            args_.add(printf_num.toString())
        }
        10 -> {
            args_.add(arguments_num.toString())
            args_.add(printf_num.toString())
        }
    }
    return args_
}

fun dataToStr(
    str_: String,
    task: String?,
    rand_seed: String?,
    variables_num: String?,
    statements_num: String?,
    arguments_num: String?,
    printf_num: String?,
    redefinition_var: String?,
    operations: String?,
    if_num: String?,
    switch_num: String?,
    case_num: String?,
    while_num: String?,
    do_while_num: String?,
    for_num: String?,
    nesting_level: String?,
    arrays_num: String?,
    array_size: String?
): String {
    var str = "${str_}"
    if (task.toString() != "null") str = "$str?task=${task.toString()}"
    if (rand_seed.toString() != "null") str = "$str&rand_seed=${rand_seed.toString()}"
    if (variables_num.toString() != "null") str = "$str&variables_num=${variables_num.toString()}"
    if (arrays_num.toString() != "null") str = "$str&arrays_num=${arrays_num.toString()}"
    if (array_size.toString() != "null") str = "$str&array_size=${array_size.toString()}"
    if (statements_num.toString() != "null") str = "$str&statements_num=${statements_num.toString()}"
    if (arguments_num.toString() != "null") str = "$str&arguments_num=${arguments_num.toString()}"
    if (printf_num.toString() != "null") str = "$str&printf_num=${printf_num.toString()}"
    if (redefinition_var.toString() != "null") str = "$str&redefinition_var=${redefinition_var.toString()}"
    if (operations.toString() != "null") {
        val args_: MutableList<String> = mutableListOf()
        args_.addAll(operations.toString().split(','))
        str = "$str&operations="
        for (i in 0..args_.size - 1) {
            if (args_[i] == AMPERSAND) str = "$str%26"
            if (args_[i] == ADDITION) str = "$str%2b"
            if (args_[i] != AMPERSAND && args_[i] != ADDITION) str = "$str${args_[i]}"
            if (i != args_.size - 1) str = "$str,"
        }
    }
    if (if_num.toString() != "null") str = "$str&if_num=${if_num.toString()}"
    if (switch_num.toString() != "null") str = "$str&switch_num=${switch_num.toString()}"
    if (case_num.toString() != "null") str = "$str&case_num=${case_num.toString()}"
    if (while_num.toString() != "null") str = "$str&while_num=${while_num.toString()}"
    if (do_while_num.toString() != "null") str = "$str&do_while_num=${do_while_num.toString()}"
    if (for_num.toString() != "null") str = "$str&for_num=${for_num.toString()}"
    if (nesting_level.toString() != "null") str = "$str&nesting_level=${nesting_level.toString()}"
    return str
}

fun parametersToStr(str_: String, parameters_: ProgramParameters): String {
    var new_str = "$str_?task=${parameters_.task}&rand_seed=${parameters_.rand_seed_}"
    when (parameters_.task) {
        1 -> {
            new_str =
                "$new_str&variables_num=${parameters_.variables_num}&statements_num=${parameters_.statements_num}&arguments_num=${parameters_.arguments_num}&printf_num=${parameters_.printf_num}"
            if (parameters_.redefiniton_var) new_str = "$new_str&redefinition_var=1"
            else new_str = "$new_str&redefinition_var=0"
            var operations_ = ""
            for (i: Int in 0..parameters_.OPERATIONS_TYPE.size - 1) {
                if (parameters_.OPERATIONS_TYPE[i] == AMPERSAND) operations_ = "$operations_%26"
                if (parameters_.OPERATIONS_TYPE[i] == ADDITION) operations_ = "$operations_%2b"
                if (parameters_.OPERATIONS_TYPE[i] != AMPERSAND && parameters_.OPERATIONS_TYPE[i] != ADDITION) operations_ =
                    "$operations_${parameters_.OPERATIONS_TYPE[i]}"
                if (i != parameters_.OPERATIONS_TYPE.size - 1) operations_ = "$operations_,"
            }
            new_str = "$new_str&operations=${operations_}"
        }
        2 -> new_str =
            "$new_str&variables_num=${parameters_.variables_num}&arguments_num=${parameters_.arguments_num}&if_num=${parameters_.if_num}&nesting_level=${parameters_.nesting_level}"
        3 -> new_str =
            "$new_str&variables_num=${parameters_.variables_num}&arguments_num=${parameters_.arguments_num}&switch_num=${parameters_.switch_num}&case_num=${parameters_.case_num}&nesting_level=${parameters_.nesting_level}"
        4 -> new_str =
            "$new_str&variables_num=${parameters_.variables_num}&arguments_num=${parameters_.arguments_num}&while_num=${parameters_.while_num}&nesting_level=${parameters_.nesting_level}"
        5 -> new_str =
            "$new_str&variables_num=${parameters_.variables_num}&arguments_num=${parameters_.arguments_num}&do_while_num=${parameters_.do_while_num}&nesting_level=${parameters_.nesting_level}"
        6 -> new_str =
            "$new_str&variables_num=${parameters_.variables_num}&arguments_num=${parameters_.arguments_num}&for_num=${parameters_.for_num}&nesting_level=${parameters_.nesting_level}"
        7 -> new_str =
            "$new_str&variables_num=${parameters_.variables_num}&arguments_num=${parameters_.arguments_num}&if_num=${parameters_.if_num}&switch_num=${parameters_.switch_num}&case_num=${parameters_.case_num}&while_num=${parameters_.while_num}&do_while_num=${parameters_.do_while_num}&for_num=${parameters_.for_num}&nesting_level=${parameters_.nesting_level}"
        8 -> new_str =
            "$new_str&variables_num=${parameters_.variables_num}&statements_num=${parameters_.statements_num}&arguments_num=${parameters_.arguments_num}&printf_num=${parameters_.printf_num}"
        9 -> new_str =
            "$new_str&arrays_num=${parameters_.array_num}&array_size=${parameters_.array_size}&statements_num=${parameters_.statements_num}&printf_num=${parameters_.printf_num}"
        10 -> new_str =
            "$new_str&variables_num=${parameters_.variables_num}&arguments_num=${parameters_.arguments_num}&printf_num=${parameters_.printf_num}"
    }
    return new_str
}

fun execution(args_: MutableList<String>, str_: String): String {
    var rand_seed = args_[1]
    val parameters = ProgramParameters(args_)
    var generator = Generator(parameters)
    generator.programGenerate()

    var v_number = ""
    var number = 0
    var count = 15

    if (generator.runtime()) return parametersToStr(str_, parameters)

    if (!generator.runtime()) {
        for (i in (TEMPLATE.length)..(rand_seed.length - 1))
            v_number = "$v_number${rand_seed[i]}"
        number = parseInt(v_number)
    }

    while (!generator.runtime() && count != 0) {
        count--
        number++
        val rand_seed_ = "$TEMPLATE${number}"
        args_[1] = rand_seed_

        val parameters = ProgramParameters(args_)
        generator = Generator(parameters)
        generator.programGenerate()
        if (generator.runtime()) return parametersToStr(str_, parameters)
    }
    return ""
}

fun stringParser(line: String): MutableList<String> {
    var list: MutableList<String> = mutableListOf()
    var item = ""
    line.forEach {
        item = "$item$it"
        if (it == ']') {
            list.add(item)
            item = ""
        }
    }
    return list
}

fun checkAnswer_(result_: MutableList<String>, answer_: MutableList<String>): Int {
    if (result_.size == 0) {
        if (answer_.size == 0) return 100
        return 0
    } else {
        if (result_.size > answer_.size) return checkAnswer(result_, answer_)
        return checkAnswer(answer_, result_)
    }
}

fun checkAnswer(result_: MutableList<String>, answer_: MutableList<String>): Int {
    var count = 0
    var i = 0
    while (i < result_.size && !answer_.isEmpty()) {
        var j = 0
        while (i < result_.size && j < answer_.size && !answer_.isEmpty()) {
            if (result_[i] == answer_[j]) {
                count++
                answer_.remove(answer_[j])
                i++
            } else j++
        }
        i++
    }
    return count * 100 / result_.size
}
