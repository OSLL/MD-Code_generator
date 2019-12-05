package com.example

import java.lang.Integer.parseInt

class ProgramParameters {
    var task = 0
    var rand_seed = 0
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
    var redefiniton_var = true
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    var nesting_level = 0
    var array_num = 0
    var array_size = 0

    constructor() { }

    constructor(args: MutableList<String>) {
        task = parseInt(args[0])
        rand_seed = args[1].hashCode()
        variables_num = parseInt(args[2])

        when (task) {
            1 -> {
                statements_num = parseInt(args[3])
                arguments_num = parseInt(args[4])
                printf_num = parseInt(args[5])
                redefiniton_var = false
                if (parseInt(args[6]) == 1) redefiniton_var = true
                for (i: Int in 7..args.size - 1)
                    OPERATIONS_TYPE.add(args[i])
            }
            2 -> {
                arguments_num = parseInt(args[3])
                if_num = parseInt(args[4])
                printf_num = if_num
                nesting_level = parseInt(args[5])
                if (if_num == 1) nesting_level = 0
            }
            3 -> {
                arguments_num = parseInt(args[3])
                switch_num = parseInt(args[4])
                case_num = parseInt(args[5])
                if (case_num < 2) case_num = 2
                printf_num = switch_num * case_num
                nesting_level = parseInt(args[6])
                if (switch_num == 1) nesting_level = 0
            }
            4 -> {
                arguments_num = parseInt(args[3])
                while_num = parseInt(args[4])
                printf_num = while_num
                nesting_level = parseInt(args[5])
                if (while_num == 1) nesting_level = 0
            }
            5 -> {
                arguments_num = parseInt(args[3])
                do_while_num = parseInt(args[4])
                printf_num = do_while_num
                nesting_level = parseInt(args[5])
                if (do_while_num == 1) nesting_level = 0
            }
            6 -> {
                arguments_num = parseInt(args[3])
                for_num = parseInt(args[4])
                printf_num = for_num
                nesting_level = parseInt(args[5])
                if (for_num == 1) nesting_level = 0
            }
            7 -> {
                arguments_num = parseInt(args[3])
                if_num = parseInt(args[4])
                switch_num = parseInt(args[5])
                case_num = parseInt(args[6])
                if (case_num < 2) case_num = 2
                while_num = parseInt(args[7])
                do_while_num = parseInt(args[8])
                for_num = parseInt(args[9])
                printf_num = if_num + switch_num * case_num + while_num + do_while_num + for_num
                nesting_level = parseInt(args[10])
                if (printf_num == 1) nesting_level = 0
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
                array_size = parseInt(args[3])
                if (array_size < 3) array_size = 3
                statements_num = parseInt(args[4])
                if (statements_num < 1) statements_num = 1
                printf_num = parseInt(args[5])
            }
            10 -> {
                arguments_num = parseInt(args[3])
                printf_num = parseInt(args[4])
            }
        }

        if (arguments_num < 1) arguments_num = 1
        if (arguments_num > 10) arguments_num = 10
        if (printf_num == 0) printf_num = 1
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