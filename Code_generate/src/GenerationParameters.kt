package com.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationParameters(
    val task: Int,
    @SerialName("rand_seed")
    val randSeed: String,
    @SerialName("variables_num")
    val variablesNum: Int,
    @SerialName("statements_num")
    val statementsNum: Int? = null,
    @SerialName("arguments_num")
    val argumentsNum: Int? = null,
    @SerialName("printf_num")
    val printfNum: Int? = null,
    @SerialName("if_num")
    val ifNum: Int? = null,
    @SerialName("switch_num")
    val switchNum: Int? = null,
    @SerialName("case_num")
    val caseNum: Int? = null,
    @SerialName("while_num")
    val whileNum: Int? = null,
    @SerialName("do_while_num")
    val doWhileNum: Int? = null,
    @SerialName("for_num")
    val forNum: Int? = null,
    @SerialName("redefinition_var")
    val redefinitionVar: Boolean? = null,
    val operations: List<String>? = null,
    @SerialName("nesting_level")
    val nestingLevel: Int? = null,
    @SerialName("array_num")
    val arrayNum: Int? = null,
    @SerialName("array_size")
    val arraySize: Int? = null
) {
    fun toArgsArray(): MutableList<String> {
        val args = mutableListOf<String>()
        args.add(task.toString())
        args.add(randSeed)
        args.add(variablesNum.toString())
        arrayNum?.let {
            args.add(it.toString())
        }
        arraySize?.let {
            args.add(it.toString())
        }
        statementsNum?.let {
            args.add(it.toString())
        }
        argumentsNum?.let {
            args.add(it.toString())
        }
        ifNum?.let {
            args.add(it.toString())
        }
        switchNum?.let {
            args.add(it.toString())
        }
        caseNum?.let {
            args.add(it.toString())
        }
        whileNum?.let {
            args.add(it.toString())
        }
        doWhileNum?.let {
            args.add(it.toString())
        }
        forNum?.let {
            args.add(it.toString())
        }
        printfNum?.let {
            args.add(it.toString())
        }
        nestingLevel?.let {
            args.add(it.toString())
        }
        redefinitionVar?.let {
            args.add(if (it) "1" else "0")
        }
        operations?.let {
            args.addAll(it)
        }
        return args
    }

    fun toProgramParameters(): ProgramParameters {
        return ProgramParameters(this.toArgsArray())
    }
}
