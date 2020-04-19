package com.example

import java.lang.Integer.parseInt

class ProgramParameters {
    var task = 0
    var rand_seed = 0
    var rand_seed_ = "0"
    var variables_num = 0
    var statements_num = 0
    var arguments_num = 0
    var printf_num = 0
    var if_num = 0
    var switch_num = 0
    var case_num = 0
    var while_num = 0
    var do_while_num = 0
    var for_num = 0
    var redefiniton_var = false
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    var nesting_level = 0
    var array_num = 0
    var array_size = 0

    constructor() { }

    constructor(args: MutableList<String>) {
        task = parseInt(args[0])
        rand_seed = args[1].hashCode()
        rand_seed_ = args[1]
        variables_num = parseInt(args[2])

        when (task) {
            1 -> {
                statements_num = parseInt(args[3])
                arguments_num = parseInt(args[4])
                printf_num = parseInt(args[5])
                if (printf_num < 1) printf_num = 1
                if (printf_num > statements_num) printf_num = statements_num
                redefiniton_var = false
                if (parseInt(args[6]) == 1) redefiniton_var = true
                val OPERATIONS_TYPE_: MutableSet<String> = mutableSetOf()
                for (i: Int in 7..args.size - 1)
                    if (args[i] == ADDITION || args[i] == MULTIPLICATION || args[i] == BITWISE_OPERATIONS[0] ||
                        args[i] == BITWISE_OPERATIONS[1] || args[i] == BITWISE_OPERATIONS[2] || args[i] == BITWISE_OPERATIONS[3]) {
                        OPERATIONS_TYPE_.add(args[i])
                    }
                OPERATIONS_TYPE.addAll(OPERATIONS_TYPE_)
            }
            2 -> {
                arguments_num = parseInt(args[3])
                if_num = parseInt(args[4])
                if (if_num < 1) if_num = 1
                if (if_num > 20) if_num = 20
                printf_num = if_num
                nesting_level = parseInt(args[5])
                if (if_num == 1) nesting_level = 0
                if (nesting_level > if_num - 1) nesting_level = if_num - 1
            }
            3 -> {
                arguments_num = parseInt(args[3])
                switch_num = parseInt(args[4])
                if (switch_num < 1) switch_num = 1
                if (switch_num > 10) switch_num = 10
                case_num = parseInt(args[5])
                if (case_num < 2) case_num = 2
                if (case_num > 5) case_num = 5
                printf_num = switch_num * case_num
                nesting_level = parseInt(args[6])
                if (switch_num == 1) nesting_level = 0
                if (nesting_level > switch_num - 1) nesting_level = switch_num - 1
            }
            4 -> {
                arguments_num = parseInt(args[3])
                while_num = parseInt(args[4])
                if (while_num < 1) while_num = 1
                if (while_num > 20) while_num = 20
                printf_num = while_num
                nesting_level = parseInt(args[5])
                if (while_num == 1) nesting_level = 0
                if (nesting_level > while_num - 1) nesting_level = while_num - 1
            }
            5 -> {
                arguments_num = parseInt(args[3])
                do_while_num = parseInt(args[4])
                if (do_while_num < 1) do_while_num = 1
                if (do_while_num > 20) do_while_num = 20
                printf_num = do_while_num
                nesting_level = parseInt(args[5])
                if (do_while_num == 1) nesting_level = 0
                if (nesting_level > do_while_num - 1) nesting_level = do_while_num - 1
            }
            6 -> {
                arguments_num = parseInt(args[3])
                for_num = parseInt(args[4])
                if (for_num < 1) for_num = 1
                if (for_num > 20) for_num = 20
                printf_num = for_num
                nesting_level = parseInt(args[5])
                if (for_num == 1) nesting_level = 0
                if (nesting_level > for_num - 1) nesting_level = for_num - 1
            }
            7 -> {
                arguments_num = parseInt(args[3])
                if (parseInt(args[4]) > 0) {
                    if_num = parseInt(args[4])
                    if (if_num > 10) if_num = 10
                    printf_num += if_num
                }
                if (parseInt(args[5]) > 0) {
                    switch_num = parseInt(args[5])
                    if (switch_num > 5) switch_num = 5
                    case_num = parseInt(args[6])
                    if (case_num < 2) case_num = 2
                    if (case_num > 5) case_num = 5
                    printf_num += switch_num * case_num
                }
                if (parseInt(args[7]) > 0) {
                    while_num = parseInt(args[7])
                    if (while_num > 10) while_num = 10
                    printf_num += while_num
                }
                if (parseInt(args[8]) > 0) {
                    do_while_num = parseInt(args[8])
                    if (do_while_num > 10) do_while_num = 10
                    printf_num += do_while_num
                }
                if (parseInt(args[9]) > 0) {
                    for_num = parseInt(args[9])
                    if (for_num > 10) for_num = 10
                    printf_num += for_num
                }
                nesting_level = parseInt(args[10])
                if (printf_num == 1) nesting_level = 0
                if (nesting_level > printf_num - 1) nesting_level = printf_num - 1
            }
            8 -> {
                statements_num = parseInt(args[3])
                arguments_num = parseInt(args[4])
                printf_num = parseInt(args[5])
                if (variables_num < 2) variables_num = 2
            }
            9 -> {
                array_num = parseInt(args[2])
                if (array_num < 1) array_num = 1
                if (array_num > 7) array_num = 7
                array_size = parseInt(args[3])
                if (array_size < 5) array_size = 5
                if (array_size > 10) array_size = 10
                statements_num = parseInt(args[4])
                printf_num = parseInt(args[5])
                if (printf_num <= array_num) printf_num = array_num + 1
                if (printf_num > array_num + statements_num) printf_num = array_num + statements_num
            }
            10 -> {
                arguments_num = parseInt(args[3])
                printf_num = parseInt(args[4])
                if (printf_num < 1) printf_num = 1
                if (printf_num > 7) printf_num = 7
            }
        }

        if (variables_num < 1) variables_num = 1
        if (variables_num > 10) variables_num = 10

        if (task == 1 || task == 8 || task == 9) {
            if (statements_num < 1) statements_num = 1
            if (statements_num > 20) statements_num = 20
        }

        if (task == 1 || task == 8 || task == 9 || task == 10)
            if (arguments_num < 2) arguments_num = 2
        if (task == 2 || task == 3 || task == 4 || task == 5 || task == 6 || task == 7)
            if (arguments_num < 1) arguments_num = 1
        if (arguments_num > 10) arguments_num = 10

        if (printf_num < 1) printf_num = 1

        if (task == 8)
            if (printf_num < 2) printf_num = 2
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

    fun getIfNum(): Int {
        return if_num
    }

    fun getSwitchNum(): Int {
        return switch_num
    }

    fun getCaseNum(): Int {
        return case_num
    }

    fun getWhileNum(): Int {
        return while_num
    }

    fun getDoWhileNum(): Int {
        return do_while_num
    }

    fun getForNum(): Int {
        return for_num
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

    fun println_() {
        println("[$task $rand_seed $variables_num $arguments_num $printf_num $if_num $switch_num $case_num $while_num $do_while_num $for_num $nesting_level]")
    }

    fun getArrayNum(): Int {
        return array_num
    }

    fun getArraySize(): Int {
        return array_size
    }

}