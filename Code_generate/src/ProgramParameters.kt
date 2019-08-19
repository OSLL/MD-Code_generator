package com.example

import java.lang.Integer.parseInt

class ProgramParameters {
    var task = 0
    var rand_seed = 0
    var variables_num = 0
    var statements_num = 0
    var arguments_num = 0
    var printf_num = 0
    var redefiniton_var = false
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    var nesting_level = 0
    var size_ = 0

    constructor() { }

    constructor(args: MutableList<String>) {
        task = parseInt(args[0])
        rand_seed = parseInt(args[1])
        variables_num = parseInt(args[2])

        when (task) {
            1 -> {/*
                statements_num = Integer.parseInt(args[3])
                arguments_num = Integer.parseInt(args[4])
                printf_num = Integer.parseInt(args[5])
                redefiniton_var = false
                if (parseInt(args[6]) == 1)
                    redefiniton_var = true
//                operation_index = 7 //операции начинаются с args[7]

                OPERATIONS_TYPE.addAll(OperationType(args, 7))
                */
            }
            else -> {
                arguments_num = parseInt(args[3])
                if ( arguments_num < 1)
                    arguments_num = 1
                printf_num = parseInt(args[4])
                nesting_level = parseInt(args[5])
                if (printf_num == 1)
                    nesting_level = 0

                if (task == 6)
                    size_ = parseInt(args[6])
            }
        }
    }

    constructor(task_: String, rand_seed_: String, variables_num_: String, statements_num_: String, arguments_num_: String, printf_num_: String, redefinition_var_: String, operations_: String) {
        task = parseInt(task_)
        rand_seed = parseInt(rand_seed_)
        variables_num = parseInt(variables_num_)

        statements_num = parseInt(statements_num_)
        arguments_num = parseInt(arguments_num_)
        printf_num = parseInt(printf_num_)
        redefiniton_var = false
        if (parseInt(redefinition_var_) == 1)
            redefiniton_var = true
//        operation_index = 7 //операции начинаются с args[7]

//        val operations_list: MutableList<String> = mutableListOf()
//        operations_list.addAll(operations_.toString().split(','))

//        OPERATIONS_TYPE.addAll(OperationType(operations_list, operation_index))
        OPERATIONS_TYPE.addAll(operations_.toString().split(','))
    }

    constructor(task_: String, rand_seed_: String, variables_num_: String, arguments_num_: String, printf_num_: String, nesting_level_: String) {
        task = parseInt(task_)
        rand_seed = parseInt(rand_seed_)
        variables_num = parseInt(variables_num_)

        arguments_num = parseInt(arguments_num_)
        if ( arguments_num < 1)
            arguments_num = 1
        printf_num = parseInt(printf_num_)
        nesting_level = parseInt(nesting_level_)
        if (printf_num == 1)
            nesting_level = 0
    }

    constructor(task_: String, rand_seed_: String, variables_num_: String, arguments_num_: String, printf_num_: String, nesting_level_: String, size: String) {
        task = parseInt(task_)
        rand_seed = parseInt(rand_seed_)
        variables_num = parseInt(variables_num_)

        arguments_num = parseInt(arguments_num_)
        if ( arguments_num < 1)
            arguments_num = 1
        printf_num = parseInt(printf_num_)
        nesting_level = parseInt(nesting_level_)
        if (printf_num == 1)
            nesting_level = 0

        if (task == 6)
            size_ = parseInt(size)
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

    fun getOperationType(): MutableList<String> {
        return OPERATIONS_TYPE
    }

    fun getNestingLevel(): Int {
        return nesting_level
    }

    fun getSize(): Int {
        return size_
    }
}