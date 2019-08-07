//package com.example

import java.lang.Integer.parseInt

class ProgramParameters {
    var task = 0
    var rand_seed = 0
    var variables_num = 0
    var statements_num = 0
    var arguments_num = 0
    var printf_num = 0
    var redefiniton_var = false
    var operation_index = 7 //операции начинаются с args[7]
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()

    var nesting_level = 0
    var size_ = 0

    constructor() { }

    constructor(args: MutableList<String>) {
        task = parseInt(args[0])
        rand_seed = parseInt(args[1])
        variables_num = parseInt(args[2])

        when (task) {
            1 -> {
                statements_num = Integer.parseInt(args[3])
                arguments_num = Integer.parseInt(args[4])
                printf_num = Integer.parseInt(args[5])
                redefiniton_var = false
                if (parseInt(args[6]) == 1)
                    redefiniton_var = true
                operation_index = 7 //операции начинаются с args[7]

                OPERATIONS_TYPE.addAll(OperationType(args, operation_index))
            }
            else -> {
                arguments_num = Integer.parseInt(args[3])
                if ( arguments_num < 1)
                    arguments_num = 1
                printf_num = Integer.parseInt(args[4])
                nesting_level = Integer.parseInt(args[5])
                if (printf_num == 1)
                    nesting_level = 0

                if (task == 6)
                    size_ = parseInt(args[6])
            }
        }
    }

    fun getTask_(): Int {
        return task
    }

    fun getRandSeed(): Int {
        return rand_seed
    }

    fun getVariablesNum(): Int {
        return variables_num
    }

    fun getStatementsNum(): Int {
        return statements_num
    }

    fun getArgumentsNum(): Int {
        return arguments_num
    }

    fun getPrintfNum(): Int {
        return printf_num
    }

    fun getRedefinitionVar(): Boolean {
        return redefiniton_var
    }

    fun getOperationIndex(): Int {
        return operation_index
    }

    fun getOperationType(): MutableList<String> {
        return OPERATIONS_TYPE
    }

    fun getNestingLevel(): Int {
        return nesting_level
    }

    fun getSize(): Int {
        return size_
    }

    fun coutParam() {
        println("task: $task")
        println("rand_seed: $rand_seed")
        println("variables_num: $variables_num")
        println("statements_num: $statements_num")
        println("arguments_num: $arguments_num")
        println("printf_num: $printf_num")
        println("redefiniton_var: $redefiniton_var")
        println("operation_index: $operation_index")
        println("OPERATIONS_TYPE.size: ${OPERATIONS_TYPE.size}")
        println("nesting_level: $nesting_level")
        println("size_: $size_")
    }
}