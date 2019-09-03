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
            1 -> {
                statements_num = parseInt(args[3])
                arguments_num = parseInt(args[4])
                printf_num = parseInt(args[5])
                if (printf_num == 0) printf_num = 1
                redefiniton_var = false
                if (parseInt(args[6]) == 1) redefiniton_var = true
                for (i: Int in 7..args.size - 1)
                    OPERATIONS_TYPE.add(args[i])
            }
            2 -> {
                arguments_num = parseInt(args[3])
                if ( arguments_num < 1)
                    arguments_num = 1
                if_num = parseInt(args[4])
                printf_num = if_num
                nesting_level = parseInt(args[5])
                if (if_num == 1)
                    nesting_level = 0
            }
            3 -> {
                arguments_num = parseInt(args[3])
                if ( arguments_num < 1) arguments_num = 1
                switch_num = parseInt(args[4])
                case_num = parseInt(args[5])
                if (case_num < 2) case_num = 2
                printf_num = case_num
                nesting_level = parseInt(args[6])
                if (switch_num == 1)
                    nesting_level = 0
            }
            4 -> {
                arguments_num = parseInt(args[3])
                if ( arguments_num < 1)
                    arguments_num = 1
                while_num = parseInt(args[4])
                printf_num = while_num
                nesting_level = parseInt(args[5])
                if (while_num == 1)
                    nesting_level = 0
            }
            5 -> {
                arguments_num = parseInt(args[3])
                if ( arguments_num < 1)
                    arguments_num = 1
                do_while_num = parseInt(args[4])
                printf_num = do_while_num
                nesting_level = parseInt(args[5])
                if (do_while_num == 1)
                    nesting_level = 0
            }
            6 -> {
                arguments_num = parseInt(args[3])
                if ( arguments_num < 1) arguments_num = 1
                for_num = parseInt(args[4])
                printf_num = for_num
                nesting_level = parseInt(args[5])
                if (for_num == 1) nesting_level = 0
                size_ = parseInt(args[6])
            }
            else -> {
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
        if (parseInt(redefinition_var_) == 1) redefiniton_var = true
        OPERATIONS_TYPE.addAll(operations_.toString().split(','))
    }

    constructor(task_: String, rand_seed_: String, variables_num_: String, arguments_num_: String, printf_num_: String, nesting_level_: String) {
        task = parseInt(task_)
        rand_seed = parseInt(rand_seed_)
        variables_num = parseInt(variables_num_)

        arguments_num = parseInt(arguments_num_)
        if ( arguments_num < 1) arguments_num = 1
        printf_num = parseInt(printf_num_)
        when (task) {
            2 -> if_num = printf_num
            4 -> while_num = printf_num
            5 -> do_while_num = printf_num
        }
        nesting_level = parseInt(nesting_level_)
        if (printf_num == 1) nesting_level = 0
    }

//    val parameters = ProgramParameters(task.toString(), rand_seed.toString(), variables_num.toString(), arguments_num.toString(), for_num.toString(), size.toString(), nesting_level.toString())

    constructor(task_: String, rand_seed_: String, variables_num_: String, arguments_num_: String, printf_num_: String, size: String, nesting_level_: String) {
        task = parseInt(task_)
        rand_seed = parseInt(rand_seed_)
        variables_num = parseInt(variables_num_)

        arguments_num = parseInt(arguments_num_)
        if ( arguments_num < 1) arguments_num = 1
        when (task) {
            3 -> {
                switch_num = parseInt(printf_num_)
                case_num = parseInt(size)
                printf_num = case_num
            }
            6 -> {
                for_num = parseInt(printf_num_)
                size_ = parseInt(size)
                printf_num = for_num
            }
        }
        nesting_level = parseInt(nesting_level_)
        if (printf_num == 1) nesting_level = 0
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

    fun getSize(): Int {
        return size_
    }
}