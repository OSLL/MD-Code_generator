package com.example

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Generator {
    var program: Program = Program()
    var parameters: ProgramParameters = ProgramParameters()
    var randList_: RandList = RandList()

    constructor() { }

    constructor(param: ProgramParameters) {
        parameters = param
        when (parameters.getTask_()) {
            1 -> randList_ = RandList(parameters.getRandSeed(), 50, parameters.getVariablesNum(), parameters.getOperationType().size)
            6 -> randList_ = RandList(parameters.getRandSeed(), 60, parameters.getVariablesNum(), parameters.getForNum(), 0)
            7 -> randList_ = RandList(parameters.getRandSeed(), 60, parameters.getVariablesNum(), parameters.getForNum(), 0)
            else -> randList_ = RandList(parameters.getRandSeed(), 60, parameters.getVariablesNum())
        }
    }

    fun Include(index: Int): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        program_.add(INCLUDE[0])
        program_.add(LIBRARY[index])
        program_.add(INCLUDE[1])
        program_.add(CARRIAGE_RETURN)
        return program_
    }

    fun Main(): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        program_.add(INT)
        program_.add(" ")
        program_.add(MAIN)
        program_.add(ROUND_BRACKET)
        program_.add(ROUND_BRACKET_)
        program_.add(BRACE)
        program_.add(CARRIAGE_RETURN)
        program_.addAll(itemSelection())
        program_.addAll(Return(0))
        program_.add(BRACE_)
        return program_
    }

    fun Return(index: Int): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        program_.add(RETURN)
        program_.add(" ")
        program_.add("$index")
        program_.add(END_OF_LINE)
        return program_
    }

    fun programGenerate(): MutableList<String> {
        program.getProgram().addAll(Include(0))
        if (parameters.getTask_() != 1)
            program.getProgram().addAll(Include(1))
        program.getProgram().add(CARRIAGE_RETURN)
        program.getProgram().addAll(Main())

//        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
//        var file = File("func_$time.c")
//        Runtime.getRuntime().exec("clang-format -i func_$time.c")

        var file = File("func.c")
        file.writeText(program.getProgram().joinToString(""))

        return program.getProgram()
    }

    fun runtime() {
//        Runtime.getRuntime().exec("clang-format -i func.c")
//        Runtime.getRuntime().exec("gcc func.c -o func")
//        val process: Process = Runtime.getRuntime().exec("./func > 1.txt")
        val process: Process = Runtime.getRuntime().exec("./run.sh")
//        if (process.exitValue() == 0)
//            return true
//        return false
        val is_: BufferedReader = BufferedReader(InputStreamReader(process.getInputStream()))
        var line = is_.readLine()
//        process.exitValue()
//        val line_ = mutableListOf<String>()
//
//        while (line != null) {
//            line_.add(line)
//            line = is_.readLine()
//        }
//        return line_

    }

    fun unsignedIntVariableIndex(visibleVar: Program): Int {
        var index = randList_.randListIntPop(randList_.variableIdList)
        while (index > visibleVar.getVariableUnsInt().size - 1)
            index = randList_.randListIntPop(randList_.variableIdList)
        return index
    }

    fun unsignedIntVariableOrNumber(visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (visibleVar.getVariableUnsInt().size != 0 && randList_.randListBoolPop(randList_.listBool))
            program_.add(visibleVar.getVariableUnsIntIndex(unsignedIntVariableIndex(visibleVar)))
        else program_.add("${randList_.randListIntPop(randList_.listInt)}")

        return program_
    }

    fun TermAddition(argNum: Int, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (argNum > 1) {
            val operation = parameters.OPERATIONS_TYPE[randList_.randListIntPop(randList_.operationIdList)]
            if (operation != BITWISE_OPERATIONS[0] && operation != BITWISE_OPERATIONS[1]) {
                program_.add(operation)
                val flag = randList_.randListBoolPop(randList_.listBool)
                if (flag) program_.add(ROUND_BRACKET)
                program_.addAll(unsignedIntVariableOrNumber(visibleVar))
                if (argNum - 1 > 1 && randList_.randListBoolPop(randList_.listBool))
                    program_.addAll(TermAddition(argNum - 1, visibleVar))
                if (flag) program_.add(ROUND_BRACKET_)
            }
            else {
                program_.add(operation)
                program_.add("${randList_.randListIntPop(randList_.listInt)}")
            }
        }

        return program_
    }

    //добавляет строку-выражение в контейнер
    fun Term(visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        var variable: String
        if (!parameters.getRedefinitionVar()) {
            variable = IDENTIFIER[randList_.randListIntPop(randList_.variableIdList)]
            while (visibleVar.getVariableUnsInt().contains(variable))
                variable = IDENTIFIER[randList_.randListIntPop(randList_.variableIdList)]
        }
        else variable = IDENTIFIER[randList_.randListIntPop(randList_.variableIdList)]
        program_.add(variable)
        program_.add(EQUALLY)

        val flag = randList_.randListBoolPop(randList_.listBool)
        if (flag) program_.add(ROUND_BRACKET)
        program_.addAll(unsignedIntVariableOrNumber(visibleVar))
        val operation = parameters.OPERATIONS_TYPE[randList_.randListIntPop(randList_.operationIdList)]
        if (operation != BITWISE_OPERATIONS[0] && operation != BITWISE_OPERATIONS[1]) {
            program_.add(operation)
            program_.addAll(unsignedIntVariableOrNumber(visibleVar))
            if (parameters.getArgumentsNum() > 2 && randList_.randListBoolPop(randList_.listBool))
                program_.addAll(TermAddition(parameters.getArgumentsNum(), visibleVar))
        }
        else {
            program_.add(operation)
            program_.add("${randList_.randListIntPop(randList_.listInt)}")
        }

        if (flag) program_.add(ROUND_BRACKET_)
        program_.add(END_OF_LINE)
        program.getVariableUnsInt().add(variable)
        program.incrementCounterTerm()

        val prog = mutableListOf<String>()
        prog.addAll(program.getProgram())
        prog.addAll(program_)
        val visibleVar_ = findVisibleVar(prog)
        if (parameters.getStatementsNum() / parameters.getPrintfNum() == 0) program_.addAll(Printf(visibleVar_))
        else
            if (parameters.getPrintfNum() == 2 && program.getCounterTerm() % (parameters.getStatementsNum() / parameters.getPrintfNum()) == 0
                    || parameters.getStatementsNum() - program.getCounterTerm() == 0
                    || program.getCounterTerm() % (parameters.getStatementsNum() / (parameters.getPrintfNum() - 1)) == 0)
                    program_.addAll(Printf(visibleVar_))

        return program_
    }

    fun itemSelection() : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        when (parameters.getTask_()) {
            1 -> { //arithmetic operating block
                program_.addAll(Init(program.getProgram()))
                do {
                    val visibleVar = findVisibleVar(program_)
                    program_.addAll(Term(visibleVar))
                } while (parameters.getStatementsNum() - program.getCounterTerm() > 0)
            }
            2 -> { //if block
                do {
                    program_.addAll(If(parameters.getNestingLevel(), program_))
                } while (parameters.getIfNum() - program.getCounterIf() > 0)
            }
            3 -> { //switch block
                    do {
                        program_.addAll(Switch(parameters.getNestingLevel(), program_))
                    }
                    while (parameters.getSwitchNum() - program.getCounterSwitch() > 0)
            }
            4 -> { //while block
                do {
                    program_.addAll(While(parameters.getNestingLevel(), program_))
                } while (parameters.getWhileNum() - program.getCounterWhile() > 0)
            }
            5 -> { //do while block
                do {
                    program_.addAll(DoWhile(parameters.getNestingLevel(), program_))
                } while (parameters.getDoWhileNum() - program.getCounterDoWhile() > 0)
            }
            6 -> { //for block
                do {
                    program_.addAll(ForLoop(parameters.getNestingLevel(), program_))
                } while (parameters.getForNum() - program.getCounterFor() > 0)
            }
            7 -> {
                do {
                    program_.addAll(itemSelection_(parameters.getNestingLevel(), program_))
                } while (parameters.getIfNum() - program.getCounterIf() > 0 || parameters.getSwitchNum() - program.getCounterSwitch() > 0
                    || parameters.getWhileNum() - program.getCounterWhile() > 0 || parameters.getDoWhileNum() - program.getCounterDoWhile() > 0
                    || parameters.getForNum() - program.getCounterFor() > 0)
            }
        }
        return program_
    }

    fun itemSelection_(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
//        if (nestingLevel == parameters.getNestingLevel() && !prog.isEmpty()) prog.clear()

        if (parameters.getIfNum() != 0 && parameters.getIfNum() - program.getCounterIf() > 0 && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(If(nestingLevel, prog))

        if (parameters.getSwitchNum() != 0 && parameters.getSwitchNum() - program.getCounterSwitch() > 0 && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(Switch(nestingLevel, prog))

        if (parameters.getWhileNum() != 0 && parameters.getWhileNum() - program.getCounterWhile() > 0 && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(While(nestingLevel, prog))

        if (parameters.getDoWhileNum() != 0 && parameters.getDoWhileNum() - program.getCounterDoWhile() > 0 && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(DoWhile(nestingLevel, prog))

        if (parameters.getForNum() != 0 && parameters.getForNum() - program.getCounterFor() > 0 && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(ForLoop(nestingLevel, prog))

        return program_
    }

    fun AdditionOrSubtractionOperation(): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (randList_.randListBoolPop(randList_.listBool))
            program_.add(ADDITION)
        else
            program_.add(SUBTRACTION)
        return program_
    }

    fun intVariableIndex(visibleVar: Program): Int {
        var index = randList_.randListIntPop(randList_.variableIdList)
        while (index > visibleVar.getVariableInt().size - 1)
            index = randList_.randListIntPop(randList_.variableIdList)
        return index
    }

    fun boolVariableIndex(visibleVar: Program): Int {
        var index = randList_.randListIntPop(randList_.variableIdList)
        while (index > visibleVar.getVariableBool().size - 1)
            index = randList_.randListIntPop(randList_.variableIdList)
        return index
    }

    fun floatVariableIndex(visibleVar: Program): Int {
        var index = randList_.randListIntPop(randList_.variableIdList)
        while (index > visibleVar.getVariableFloat().size - 1)
            index = randList_.randListIntPop(randList_.variableIdList)
        return index
    }

    fun InitModOperation(argNum: Int, visibleVar: Program): MutableList<String> {
            val program_: MutableList<String> = mutableListOf()

            if (argNum > 2) {
                var a: String
                if (visibleVar.getVariableInt().size != 0 && randList_.randListBoolPop(randList_.listBool))
                    a = visibleVar.getVariableIntIndex(intVariableIndex(visibleVar))
                else
                    a = "${randList_.randListIntPop(randList_.listInt)}"
                val b = randList_.randListIntPop(randList_.listInt)
                program_.add(a)
                program_.add(MOD)
                program_.add("${b}")

                if (argNum - 2 > 1 && randList_.randListBoolPop(randList_.listBool))
                    program_.addAll(InitAddition(argNum - 2, visibleVar))
                else {
                    if (argNum - 2 > 2 && randList_.randListBoolPop(randList_.listBool)) {
                        program_.addAll(AdditionOrSubtractionOperation())
                        program_.addAll(InitModOperation(argNum - 2, visibleVar))
                    }
                }
            }
            return program_
        }

    fun addFloatOrInt(visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (randList_.randListBoolPop(randList_.listBool)) { //тогда выбираем перем/число типа float
            if (visibleVar.getVariableFloat().size != 0 && randList_.randListBoolPop(randList_.listBool))
                program_.add(visibleVar.getVariableFloatIndex(floatVariableIndex(visibleVar)))
            else
                program_.add("${randList_.randListFloatPop(randList_.listFloat)}")
        }
        else { //иначе выбираем перем/число типа int
            if (visibleVar.getVariableInt().size != 0 && randList_.randListBoolPop(randList_.listBool))
                program_.add(visibleVar.getVariableIntIndex(intVariableIndex(visibleVar)))
            else
                program_.add("${randList_.randListIntPop(randList_.listInt)}")
        }
        return program_
    }

    fun addBool(visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (visibleVar.getVariableBool().size != 0 && randList_.randListBoolPop(randList_.listBool)) {
            if (randList_.randListBoolPop(randList_.listBool))
                program_.add(LOGICAL_NEGATION)
            program_.add(visibleVar.getVariableBoolIndex(boolVariableIndex(visibleVar)))
        }
        else
            program_.add("${randList_.randListBoolPop(randList_.listBool)}")
        return program_
    }

    fun BoolInitAddition(argNum: Int, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (argNum > 1) {
            program_.add(logicalAndOr())
            program_.addAll(addBool(visibleVar))

            if (argNum - 1 > 1 && randList_.randListBoolPop(randList_.listBool))
                program_.addAll(BoolInitAddition(argNum - 1, visibleVar))
        }
        return program_
    }

    fun InitAddition(argNum: Int, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (argNum > 1) {
            program_.add(ARITHMETIC_OPERATIONS[randList_.randListIntPop(randList_.operationIdList)])
            program_.addAll(addFloatOrInt(visibleVar))

            if (argNum - 1 > 1 && randList_.randListBoolPop(randList_.listBool))
                program_.addAll(InitAddition(argNum - 1, visibleVar))
            else {
                if (argNum - 1 > 2 && randList_.randListBoolPop(randList_.listBool)) {
                    program_.addAll(AdditionOrSubtractionOperation())
                    program_.addAll(InitModOperation(argNum - 1, visibleVar))
                }
            }
        }
        return program_
    }

    //инициализация переменных
    fun Init(prog: MutableList<String>): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        when (parameters.getTask_()) {
            1 -> {
                //номера инициализированных переменных
                var initialized_args = randList_.rand(0, parameters.getVariablesNum() - 1, parameters.getRandSeed())

                if (parameters.getVariablesNum() == 1) initialized_args = -1
                for (i in 0..initialized_args) {
                    program_.add(UNSIGNED_INT)
                    program_.add(" ")
                    program_.add(IDENTIFIER[i])
                    program_.add(EQUALLY)
                    program_.add("${randList_.randListIntPop(randList_.listInt)}")
                    program_.add(END_OF_LINE)
                    program.getVariableUnsInt().add(IDENTIFIER[i])
                    program.incrementCounterVariables()
                }

                if (!parameters.redefiniton_var)
                    parameters.statements_num = (parameters.getVariablesNum()) - (initialized_args + 1)

                program_.add(UNSIGNED_INT)
                for (i: Int in initialized_args + 1..parameters.getVariablesNum() - 2) {
                    program_.add(" ")
                    program_.add(IDENTIFIER[i])
                    program_.add(COMMA)
                }
                program_.add(" ")
                program_.add(IDENTIFIER[parameters.getVariablesNum() - 1])
                program_.add(END_OF_LINE)

                return program_
            }
            else -> {
                //инициализация переменных
                var j: Int
                    j = parameters.getVariablesNum() / parameters.getPrintfNum()
                    if (j == 0) j = 1
                    if (parameters.getVariablesNum() % parameters.getPrintfNum() != 0 && parameters.getVariablesNum() - program.getCounterVariables() != j && randList_.randListBoolPop(randList_.listBool))
                        j += 1

                    if (parameters.getPrintfNum() - program.getCounterPrintf() == 0) j = parameters.getVariablesNum() - program.getCounterVariables()

                for (i in program.getCounterVariables()..(program.getCounterVariables() + j - 1)) {
                    var index_ = randList_.randListIntPop(randList_.listCondition)
                    if (parameters.getTask_() == 3) index_ = 1
                    val visibleVar = findVisibleVar(prog)
                    val visibleVar_ = findVisibleVar(program_)
                    visibleVar.getVariableBool().addAll(visibleVar_.getVariableBool())
                    visibleVar.getVariableInt().addAll(visibleVar_.getVariableInt())
                    visibleVar.getVariableFloat().addAll(visibleVar_.getVariableFloat())
                    when(index_) {
                        0 -> {
                            program_.add(BOOL)
                            program_.add(" ")
                            program_.add(IDENTIFIER[i])
                            program_.add(EQUALLY)
                            program_.addAll(addBool(visibleVar))
                            if (parameters.getArgumentsNum() > 1 && randList_.randListBoolPop(randList_.listBool))
                                program_.addAll(BoolInitAddition(parameters.getArgumentsNum(), visibleVar))

                            program.getVariableBool().add(IDENTIFIER[i])
                        }
                        else -> {
                            when (index_) {
                                1 -> {
                                    program_.add(INT)
                                    program.getVariableInt().add(IDENTIFIER[i])
                                }
                                2 -> {
                                    program_.add(FLOAT)
                                    program.getVariableFloat().add(IDENTIFIER[i])
                                }
                            }
                            program_.add(" ")
                            program_.add(IDENTIFIER[i])
                            program_.add(EQUALLY)

                            if (parameters.getArgumentsNum() > 2 && randList_.randListBoolPop(randList_.listBool))
                                program_.addAll(InitModOperation(parameters.getArgumentsNum(), visibleVar))
                            else {
                                program_.addAll(addFloatOrInt(visibleVar))
                                if (parameters.getArgumentsNum() > 1 && randList_.randListBoolPop(randList_.listBool))
                                    program_.addAll(InitAddition(parameters.getArgumentsNum(), visibleVar))
                            }
                        }
                    }

                    program_.add(END_OF_LINE)
                    program.incrementCounterVariables()
                }
            }
        }
        return program_
    }

    fun calculate(sign: String, value: Float, value_: Float): Float {
        var _value = value
        if (sign == MOD) _value %= value_
        if (sign == DIV) _value /= value_
        if (sign == MULTIPLICATION) _value *= value_
        if (sign == ADDITION) _value += value_
        if (sign == SUBTRACTION) _value -= value_
        return _value
    }

    fun initVariable(variable: MutableList<String>): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        var i = 0
        while (i < variable.size) {
            if ((variable[i] == INT || variable[i] == FLOAT) && variable[i + 3] == EQUALLY) {
                var flag: String
                if (variable[i] == INT) flag = INT
                else flag = FLOAT
                program_.add(variable[i + 2])
                i += 4
                var value = 0F
                if (!IDENTIFIER.contains(variable[i])) value = variable[i].toFloat()
                else {
                    for (j: Int in 0..program_.size - 1) {
                        if (program_[j] == variable[i]) {
                            value = program_[j + 1].toFloat()
                            break
                        }
                    }
                }
                i++
                while (variable[i] != END_OF_LINE && i + 1 < variable.size) {
                    if (!IDENTIFIER.contains(variable[i + 1]))
                        value = calculate(variable[i], value, variable[i + 1].toFloat())
                    else {
                        for (j: Int in 0..program_.size - 1) {
                            if (program_[j] == variable[i + 1]) {
                                value = calculate(variable[i], value, program_[j + 1].toFloat())
                                break
                            }
                        }
                    }
                    i += 2
                }
                if (variable[i] == END_OF_LINE) {
                    if (program_.contains("${value.toInt()}")) program_.remove(program_[program_.size - 1])
                    else
                        if (flag == INT) program_.add("${value.toInt()}")
                        else program_.add("${value}")
                }
            }
            i++
        }
        return program_
    }

    fun Printf(visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (visibleVar.getVariableUnsInt().size != 0) {
            program_.add("$PRINTF$ROUND_BRACKET$QUOTES$SQUARE_BRACKET")
            program_.add("%u$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES$COMMA ")
            program_.add(visibleVar.getVariableUnsIntIndex(unsignedIntVariableIndex(visibleVar)))
            program.incrementCounterPrintf()
            program_.add("$ROUND_BRACKET_$END_OF_LINE")
        }
        return program_
    }

    fun Printf(task: Int, varChange: MutableList<String>): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        var format_code = ""
        if (varChange.contains(INT))
            format_code = "%i"
        if (varChange.contains(FLOAT))
            format_code = "%f"
        varChange.remove(varChange.first())
        varChange.remove(END_OF_LINE)
        program_.add("$PRINTF$ROUND_BRACKET$QUOTES$SQUARE_BRACKET")
        when (task) {
            4 -> program_.add("$WHILE $DASH ${program.getCounterWhile()}$COMMA $format_code$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES$COMMA ${varChange.joinToString("")}")
            5 -> program_.add("$DO $WHILE $DASH ${program.getCounterDoWhile()}$COMMA $format_code$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES$COMMA ${varChange.joinToString("")}")
            6 -> program_.add("$FOR $DASH ${program.getCounterFor()}] %li$PRINT_CARRIAGE_RETURN$QUOTES$COMMA i")
        }
        program.incrementCounterPrintf()
        program_.add("$ROUND_BRACKET_$END_OF_LINE")
        return program_
    }

    fun Printf(variable: String, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        program_.add("$PRINTF$ROUND_BRACKET$QUOTES$SQUARE_BRACKET")
        program_.add("$FOR $DASH ${program.getCounterFor()}$COMMA ")
        val flag_ = randList_.randListBoolPop(randList_.listBool)
        val operation = ARITHMETIC_OPERATIONS[randList_.randListIntPop(randList_.operationIdList)]
        val b: String
        if (!visibleVar.getVariableFloat().isEmpty() && !visibleVar.getVariableInt().isEmpty())
            b = returnVariable(visibleVar)
        else b = "${randList_.randListIntPop(randList_.listInt)}"
        var format_code = "%i"
        if (visibleVar.getVariableFloat().contains(b)) format_code = "%f"
        if (visibleVar.getVariableFloat().contains(b) && !flag_) format_code = "%i"
        if (!visibleVar.getVariableFloat().contains(b)) format_code = "%i"
        program_.add(format_code)
        program_.add("$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES$COMMA ")
        program_.add(variable)
        if (flag_) {
            program_.add(" ")
            program_.add(operation)
            program_.add(" ")
            program_.add(b)
        }
        program.incrementCounterPrintf()
        program_.add(ROUND_BRACKET_)
        program_.add(END_OF_LINE)
        return program_
    }

    fun Printf(task: Int): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        program_.add("$PRINTF$ROUND_BRACKET$QUOTES$SQUARE_BRACKET")
        when (task) {
            2 -> program_.add("$IF $DASH ${program.getCounterIf()}$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES")
            0 -> program_.add("$ELSE $DASH ${program.getCounterElse()}$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES")
            3 -> program_.add("$SWITCH $DASH ${program.getCounterPrintf() + 1}$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES")
            4 -> program_.add("$WHILE $DASH ${program.getCounterWhile()}$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES")
            5 -> program_.add("$DO $WHILE $DASH ${program.getCounterDoWhile()}$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES")
            6 -> program_.add("$FOR $DASH ${program.getCounterFor()}] %i$PRINT_CARRIAGE_RETURN$QUOTES$COMMA i")
        }
        program.incrementCounterPrintf()
        program_.add("$ROUND_BRACKET_$END_OF_LINE")
        return program_
    }

    fun returnIntVariable(visibleVar: Program): String {
        val variable: String
        if (visibleVar.getVariableInt().size == 1)
            variable = visibleVar.getVariableIntIndex(0)
        else
            variable = visibleVar.getVariableIntIndex(intVariableIndex(visibleVar))
        return variable
    }

    fun returnFloatVariable(visibleVar: Program): String {
        val variable: String
        if (visibleVar.getVariableFloat().size == 1)
            variable = visibleVar.getVariableFloatIndex(0)
        else
            variable = visibleVar.getVariableFloatIndex(floatVariableIndex(visibleVar))
        return variable
    }

    fun returnVariable(visibleVar: Program): String {
        val variable: String
        if (visibleVar.getVariableFloat().size == 0) {
            variable = returnIntVariable(visibleVar)
            return variable
        }
        if (visibleVar.getVariableInt().size == 0) {
            variable = returnFloatVariable(visibleVar)
            return variable
        }
        if (randList_.randListBoolPop(randList_.listBool))
            variable = returnIntVariable(visibleVar)
        else
            variable = returnFloatVariable(visibleVar)
        return variable
    }

    fun findClosingBracket(program_: MutableList<String>, i: Int) : Int {
        if (!program_.contains(BRACE_))
            return 0

        var j = i + 1
        while (j < program_.size - 1) {
            if (program_[j] == BRACE) {
                j = findClosingBracket(program_, j) //infinite recursion !!!
            }
            if (program_[j] == BRACE_)
                return j
            j++
        }
        return 0
    }

    fun findVisibleVar(program_: MutableList<String>): Program {
        val program = Program()
        var i = 0

        while (i < program_.size - 1) {
            if (program_[i] == BRACE) {
                if (program_.count({it == BRACE}) == program_.count({it == BRACE_})) {
                    var index = program_.count({it == BRACE_})
                    while (index > 0) {
                        if (program_[i] == BRACE_)
                            index--
                        i++
                    }
                }
                else {
                    val k = findClosingBracket(program_, i)
                    if (k != 0) i = k
                }
            }
            if (program_[i] == UNSIGNED_INT && program_[i + 3] == EQUALLY) {
                program.getVariableUnsInt().add("${program_[i + 2]}")
                program.incrementCounterVariables()
            }
            if (program_[i] == INT && program_[i + 3] == EQUALLY) {
                program.getVariableInt().add("${program_[i + 2]}")
                program.incrementCounterVariables()
            }
            if (program_[i] == BOOL && program_[i + 3] == EQUALLY) {
                program.getVariableBool().add("${program_[i + 2]}")
                program.incrementCounterVariables()
            }
            if (program_[i] == FLOAT && program_[i + 3] == EQUALLY) {
                program.getVariableFloat().add("${program_[i + 2]}")
                program.incrementCounterVariables()
            }
            if (program_[i] == EQUALLY && program_[i - 3] != UNSIGNED_INT) {
                program.getVariableUnsInt().add("${program_[i - 1]}")
                program.incrementCounterVariables()
            }

            i++
        }
        return program
    }

    fun logicalAndOr(): String {
        if (randList_.randListBoolPop(randList_.listBool))
            return LOGICAL_AND
        else
            return LOGICAL_OR
    }

    fun Condition(visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
/*
    print("condition: ")
    for (i: Int in 0..prog.getVariableBool().size - 1)
        print(prog.getVariableBoolIndex(i) + ", ")
    for (i: Int in 0..prog.getVariableFloat().size - 1)
        print(prog.getVariableFloatIndex(i) + ", ")
    for (i: Int in 0..prog.getVariableInt().size - 1)
        print(prog.getVariableIntIndex(i) + ", ")
    println("")
*/

        if (visibleVar.getVariableBool().size == 0 && visibleVar.getVariableFloat().size == 0 && visibleVar.getVariableInt().size == 1) {
            program_.add(visibleVar.getVariableIntIndex(0))
            return program_
        }
        if (visibleVar.getVariableBool().size == 0 && visibleVar.getVariableFloat().size == 1 && visibleVar.getVariableInt().size == 0) {
            program_.add(visibleVar.getVariableFloatIndex(0))
            return program_
        }
        if (visibleVar.getVariableBool().size == 1 && visibleVar.getVariableFloat().size == 0 && visibleVar.getVariableInt().size == 0) {
            if (!randList_.randListBoolPop(randList_.listBool))
                program_.add(LOGICAL_NEGATION)
            program_.add(visibleVar.getVariableBoolIndex(0))
            return program_
        }

        var index: Int
        if ((visibleVar.getVariableFloat().size == 0 && visibleVar.getVariableInt().size == 0)
            || (visibleVar.getVariableFloat().size == 0 && visibleVar.getVariableInt().size == 1)
            || (visibleVar.getVariableFloat().size == 1 && visibleVar.getVariableInt().size == 0)) index = 0
        else index = randList_.randListIntPop(randList_.listCondition)
        if (visibleVar.getVariableBool().size == 0 && index == 0) {
            if (randList_.randListBoolPop(randList_.listBool)) index = 1
            else index = 2
        }

        when (index) {
            0 -> { //bool variable
                if (visibleVar.getVariableBool().size == 1) {
                    if (!randList_.randListBoolPop(randList_.listBool))
                        program_.add("$LOGICAL_NEGATION")
                    program_.add(visibleVar.getVariableBoolIndex(0))
                    return program_
                }
                else {
                    var i: Int
                    var i_: Int
                    var a = ""
                    var b = ""
                    val operation = logicalAndOr()
                    if (visibleVar.getVariableBool().size == 2) {
                        a = visibleVar.getVariableBoolIndex(0)
                        b = visibleVar.getVariableBoolIndex(1)
                    }
                    if (visibleVar.getVariableBool().size > 2) {
                        i = randList_.randListIntPop(randList_.variableIdList)
                        while (i > visibleVar.getVariableBool().size - 1)
                            i = randList_.randListIntPop(randList_.variableIdList)
                        a = visibleVar.getVariableBoolIndex(i)
                        visibleVar.getVariableBool().remove(a)

                        i_ = randList_.randListIntPop(randList_.variableIdList)
                        while (i_ > visibleVar.getVariableBool().size - 1)
                            i_ = randList_.randListIntPop(randList_.variableIdList)
                        b = visibleVar.getVariableBoolIndex(i_)
                    }

                    when (randList_.randListIntPop(randList_.listCondition)) {
                        0 -> {
                            program_.add(a)
                            program_.add(operation)
                            program_.add(b)
                        }
                        1 -> {
                            program_.add("$LOGICAL_NEGATION$a")
                            program_.add(operation)
                            program_.add(b)
                        }
                        2 -> {
                            program_.add(a)
                            program_.add(operation)
                            program_.add("$LOGICAL_NEGATION$b")
                        }
                    }
                    return program_
                }
            }
            1 -> { //int or float variable - comparison
                var i = randList_.randListIntPop(randList_.relationalOperationIdList)

                var a = ""
                var b = ""
                if (visibleVar.getVariableInt().size == 0 && visibleVar.getVariableFloat().size == 2) {
                    a = visibleVar.getVariableFloatIndex(0)
                    b = visibleVar.getVariableFloatIndex(1)
                }
                if (visibleVar.getVariableInt().size == 2 && visibleVar.getVariableFloat().size == 0) {
                    a = visibleVar.getVariableIntIndex(0)
                    b = visibleVar.getVariableIntIndex(1)
                }
                if (!(visibleVar.getVariableInt().size == 0 && visibleVar.getVariableFloat().size == 2)
                    && !(visibleVar.getVariableInt().size == 2 && visibleVar.getVariableFloat().size == 0)) {
                    a = returnVariable(visibleVar)
                    visibleVar.getVariableInt().remove(a)
                    visibleVar.getVariableFloat().remove(a)
                    b = returnVariable(visibleVar)
                }

                program_.add(a)
                program_.add(RELATIONAL_OPERATIONS[i])
                program_.add(b)
                return program_
            }
            2 -> { //int or float variable - arithmetic operation
                var a = ""
                var b = ""
                var operation = ""
                if (visibleVar.getVariableInt().size == 0 && visibleVar.getVariableFloat().size == 2) {
                    a = visibleVar.getVariableFloatIndex(0)
                    b = visibleVar.getVariableFloatIndex(1)
                    operation = ARITHMETIC_OPERATIONS[randList_.randListIntPop(randList_.operationIdList)]
                }
                if (visibleVar.getVariableInt().size == 2 && visibleVar.getVariableFloat().size == 0) {
                    a = visibleVar.getVariableIntIndex(0)
                    b = visibleVar.getVariableIntIndex(1)
                    if (randList_.randListBoolPop(randList_.listBool)) operation = MOD
                    else operation = ARITHMETIC_OPERATIONS[randList_.randListIntPop(randList_.operationIdList)]
                }
                if (!(visibleVar.getVariableInt().size == 0 && visibleVar.getVariableFloat().size == 2)
                    && !(visibleVar.getVariableInt().size == 2 && visibleVar.getVariableFloat().size == 0)) {
                    if (visibleVar.getVariableInt().size > 1 && randList_.randListBoolPop(randList_.listBool)) {//MOD operation
                        a = returnIntVariable(visibleVar)
                        visibleVar.getVariableInt().remove(a)
                        b = returnIntVariable(visibleVar)
                        operation = MOD
                    }
                    else {
                        a = returnVariable(visibleVar)
                        visibleVar.getVariableInt().remove(a)
                        visibleVar.getVariableFloat().remove(a)
                        b = returnVariable(visibleVar)
                        operation = ARITHMETIC_OPERATIONS[randList_.randListIntPop(randList_.operationIdList)]
                    }
                }

                program_.add(a)
                program_.add(operation)
                program_.add(b)
                return program_
            }
        }

        return program_
    }

    fun If(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (parameters.getVariablesNum() - program.getCounterVariables() > 0 )
            program_.addAll(Init(prog))

        program_.add(IF)
        program_.add(ROUND_BRACKET)

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)
        val visibleVar = findVisibleVar(p)

        program_.addAll(Condition(visibleVar))
        program_.add(ROUND_BRACKET_)
        program_.add(BRACE)
        program_.add(CARRIAGE_RETURN)
        program.incrementCounterIf()
        program_.addAll(Printf(/*parameters.getTask_()*/2))

        if (parameters.getTask_() == 2 && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterIf() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(If(nestingLevel - 1, p))
        if (parameters.getTask_() == 7 && nestingLevel > 1)
            program_.addAll(itemSelection_(nestingLevel - 1, p))

        program_.add(BRACE_)
        program_.add(CARRIAGE_RETURN)

        if (randList_.randListBoolPop(randList_.listBool))
            program_.addAll(Else(nestingLevel - 1, p))

        return program_
    }

    fun Else(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        program_.add(ELSE)
        program_.add(BRACE)

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)

        program_.add(CARRIAGE_RETURN)
        program.incrementCounterElse()
        program_.addAll(Printf(/*parameters.getTask_() - 2*/0))

        if (parameters.getTask_() == 2 && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterIf() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(If(nestingLevel - 1, p))
        if (parameters.getTask_() == 7 && nestingLevel > 1)
            program_.addAll(itemSelection_(nestingLevel - 1, p))

        program_.add(BRACE_)
        program_.add(CARRIAGE_RETURN)
        return program_
    }

    fun loopCondition(flag: Boolean, variable: String) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        val flag_ = randList_.randListBoolPop(randList_.listBool)
        var a = randList_.randListIntPop(randList_.listInt)
        var b = randList_.randListIntPop(randList_.listInt)
        val step = randList_.randListIntPop(randList_.listInt)
        if (flag_ && a > b) {
            val a_ = a
            a = b
            b = a_
        }
        if (!flag_ && a < b) {
            val a_ = a
            a = b
            b = a_
        }
        if (!flag) {
            program_.add(INT)
            program_.add(" ")
            program_.add(" ")
        }
        program_.add(variable)
        if (!flag) {
            program_.add(EQUALLY)
            program_.add("$a")
        }
        program_.add(SEMICOLON)
        program_.add(variable)

        if (flag_) program_.add(LESS_THAN)
        else program_.add(MORE_THAN)
        if (randList_.randListBoolPop(randList_.listBool))
            program_.add(EQUALLY)
        program_.add("$b")
        program_.add(SEMICOLON)

        if (randList_.randListBoolPop(randList_.listBool)) {
            if (randList_.randListBoolPop(randList_.listBool)) {
                program_.add(variable)
                if (flag_) program_.add(INCREMENT)
                else program_.add(DECREMENT)
            }
            else {
                if (flag_) program_.add(INCREMENT)
                else program_.add(DECREMENT)
                program_.add(variable)
            }
        }
        else {
            program_.add(variable)
            if (flag_) program_.add(ADDITION)
            else program_.add(SUBTRACTION)
            program_.add(EQUALLY)
            program_.add("$step")
        }
        return program_
    }

    fun ForLoop(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (parameters.getVariablesNum() - program.getCounterVariables() > 0 )
            program_.addAll(Init(prog))

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)
        val visibleVar = findVisibleVar(p)
        val variable: String
        var flag = randList_.randListBoolPop(randList_.listBool)

        if (visibleVar.getVariableInt().isEmpty()) flag = false
        if (flag) variable = returnIntVariable(visibleVar)
        else variable = "${IDENTIFIER[randList_.randListIntPop(randList_.indexVariableList)]}$UNDERSCORE"

        program_.add(FOR)
        program_.add(ROUND_BRACKET)
        program_.addAll(loopCondition(flag, variable))
        program_.add(ROUND_BRACKET_)
        program_.add(BRACE)
        program_.add(CARRIAGE_RETURN)
        program.incrementCounterFor()
        program_.addAll(Printf(variable, visibleVar))

        if (randList_.randListBoolPop(randList_.listBool))
            program_.addAll(ExitPoint())

        if (parameters.getTask_() == 6 && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterFor() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(ForLoop(nestingLevel - 1, p))
        if (parameters.getTask_() == 7 && nestingLevel > 1)
            program_.addAll(itemSelection_(nestingLevel - 1, p))

        program_.add(BRACE_)
        program_.add(CARRIAGE_RETURN)
        return program_
    }

    fun incrementOrDecrement(variable: String): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (randList_.randListBoolPop(randList_.listBool)) {
            program_.add(variable)
            if (randList_.randListBoolPop(randList_.listBool))
                program_.add(INCREMENT)
            else
                program_.add(DECREMENT)
        }
        else {
            if (randList_.randListBoolPop(randList_.listBool))
                program_.add(INCREMENT)
            else
                program_.add(DECREMENT)
            program_.add(variable)
        }
        return program_
    }

    fun varChange(condition: MutableList<String>, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        for (i: Int in 0..condition.size - 1) {
            if (program.getVariableBool().contains(condition[i])) { //bool variable
                program_.add(condition[i])
                program_.add(EQUALLY)
                program_.add(LOGICAL_NEGATION)
                program_.add(condition[i])
                break
            }
            if (program.getVariableInt().contains(condition[i])) { //int variable
                if (randList_.randListBoolPop(randList_.listBool)) {
                    program_.add(condition[i])
                    program_.add(ARITHMETIC_OPERATIONS[randList_.randListIntPop(randList_.operationIdList)])
                    program_.add(EQUALLY)
                    if (parameters.getArgumentsNum() > 2 && randList_.randListBoolPop(randList_.listBool))
                        program_.addAll(InitModOperation(parameters.getArgumentsNum(), visibleVar))
                    else {
                        program_.addAll(addFloatOrInt(visibleVar))
                        if (parameters.getArgumentsNum() > 1 && randList_.randListBoolPop(randList_.listBool))
                            program_.addAll(InitAddition(parameters.getArgumentsNum(), visibleVar))
                    }
                }
                else {
                    program_.add(INT)
                    program_.addAll(incrementOrDecrement(condition[i]))
                }
                break
            }
            if (program.getVariableFloat().contains(condition[i])) { //float variable
                if (randList_.randListBoolPop(randList_.listBool)) {
                    program_.add(condition[i])
                    program_.add(ARITHMETIC_OPERATIONS[randList_.randListIntPop(randList_.operationIdList)])
                    program_.add(EQUALLY)
                    if (parameters.getArgumentsNum() > 2 && randList_.randListBoolPop(randList_.listBool))
                        program_.addAll(InitModOperation(parameters.getArgumentsNum(), visibleVar))
                    else {
                        program_.addAll(addFloatOrInt(visibleVar))
                        if (parameters.getArgumentsNum() > 1 && randList_.randListBoolPop(randList_.listBool))
                            program_.addAll(InitAddition(parameters.getArgumentsNum(), visibleVar))
                    }
                }
                else {
                    program_.add(FLOAT)
                    program_.addAll(incrementOrDecrement(condition[i]))
                }
                break
            }
        }
        program_.add(END_OF_LINE)
        return program_
    }

    fun While(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (parameters.getVariablesNum() - program.getCounterVariables() > 0 )
            program_.addAll(Init(prog))

        program_.add(WHILE)
        program_.add(ROUND_BRACKET)

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)
        val visibleVar = findVisibleVar(p)

        val p_: MutableList<String> = mutableListOf() //в.п.для хранение текущего условия
        p_.addAll(Condition(visibleVar))

        program_.addAll(p_)
        program_.add(ROUND_BRACKET_)
        program_.add(BRACE)
        program_.add(CARRIAGE_RETURN)
        program.incrementCounterWhile()

        val varChange: MutableList<String> = mutableListOf()
        varChange.addAll(varChange(p_, visibleVar))
        if (varChange.contains(EQUALLY)) {
            program_.addAll(varChange)
            program_.addAll(Printf(/*parameters.getTask_()*/4))
        }
        else program_.addAll(Printf(/*parameters.getTask_()*/4, varChange))

        if (randList_.randListBoolPop(randList_.listBool))
            program_.addAll(ExitPoint())

        if (parameters.getTask_() == 4 && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterWhile() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(While(nestingLevel - 1, p))
        if (parameters.getTask_() == 7 && nestingLevel > 1)
            program_.addAll(itemSelection_(nestingLevel - 1, p))

        program_.add(BRACE_)
        program_.add(CARRIAGE_RETURN)
        return program_
    }

    fun DoWhile(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (parameters.getVariablesNum() - program.getCounterVariables() > 0 )
            program_.addAll(Init(prog))

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)
        val visibleVar = findVisibleVar(p)

        program_.add(DO)
        program_.add(BRACE)
        program_.add(CARRIAGE_RETURN)
        program.incrementCounterDoWhile()

        val p_: MutableList<String> = mutableListOf() //в.п.для хранение текущего условия
        p_.addAll(Condition(visibleVar))

        val varChange: MutableList<String> = mutableListOf()
        varChange.addAll(varChange(p_, visibleVar))
        if (varChange.contains(EQUALLY)) {
            program_.addAll(varChange)
            program_.addAll(Printf(/*parameters.getTask_()*/5))
        }
        else program_.addAll(Printf(/*parameters.getTask_()*/5, varChange))

//        program_.addAll(varChange(p_, visibleVar))
//        program_.addAll(Printf(parameters.getTask_()))

        if (randList_.randListBoolPop(randList_.listBool))
            program_.addAll(ExitPoint())

        if (parameters.getTask_() == 5 && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterDoWhile() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(DoWhile(nestingLevel - 1, p))
        if (parameters.getTask_() == 7 && nestingLevel > 1)
            program_.addAll(itemSelection_(nestingLevel - 1, p))

        program_.add(BRACE_)
        program_.add(WHILE)
        program_.add(ROUND_BRACKET)
        program_.addAll(p_)
        program_.add(ROUND_BRACKET_)
        program_.add(END_OF_LINE)

        return program_
    }

    fun ExitPoint(): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (randList_.randListBoolPop(randList_.listBool)) {
            program_.add(CONTINUE)
            program_.add(END_OF_LINE)
            return program_
        }
        if (randList_.randListBoolPop(randList_.listBool)) {
            program_.add(BREAK)
            program_.add(END_OF_LINE)
            return program_
        }
        if (randList_.randListBoolPop(randList_.listBool)) {
            program_.addAll(Return(0))
            return program_
        }
        return program_
    }

    fun Case(value: String, nestingLevel: Int, prog: MutableList<String>, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (value != DEFAULT) {
            program_.add(CASE)
            program_.add(" ")
            val value_ = value.toFloat()
            program_.add("${value_.toInt()}")
        }
        else program_.add(DEFAULT)
        program_.add(COLON)
        program_.add(BRACE)
        program_.add(CARRIAGE_RETURN)
        program.incrementCounterCase()

        if (parameters.getTask_() == 3 && !visibleVar.getVariableInt().isEmpty() && nestingLevel > 1 && parameters.getSwitchNum() > program.getCounterSwitch() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(Switch(nestingLevel - 1, prog))
        if (parameters.getTask_() == 7 && nestingLevel > 1)
            program_.addAll(itemSelection_(nestingLevel - 1, prog))

        program_.addAll(Printf(/*parameters.getTask_()*/3))
        program_.add(BREAK)
        program_.add(END_OF_LINE)
        program_.add(BRACE_)
        program_.add(CARRIAGE_RETURN)
        return program_
    }

    fun Switch(nestingLevel: Int, prog: MutableList<String>): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (parameters.getVariablesNum() - program.getCounterVariables() > 0 )
            program_.addAll(Init(prog))

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)

        var visibleVar = findVisibleVar(p)
        var variable = initVariable(p)

        if (visibleVar.getVariableInt().isEmpty()) {
            program_.add(INT)
            program_.add(" ")
            program_.add(IDENTIFIER[program.getCounterVariables()])
            program_.add(EQUALLY)
            program_.add("${randList_.randListFloatPop(randList_.listFloat)}")
            program_.add(END_OF_LINE)
            program.getVariableInt().add(IDENTIFIER[program.getCounterVariables()])
            program.incrementCounterVariables()

            p.addAll(program_)
            visibleVar = findVisibleVar(p)
            variable = initVariable(p)
        }

        var caseNum_ = parameters.getCaseNum()
        program_.add(SWITCH)
        program_.add(ROUND_BRACKET)
        program_.add(returnIntVariable(visibleVar))
        program_.add(ROUND_BRACKET_)
        program_.add(BRACE)
        program_.add(CARRIAGE_RETURN)
        program.incrementCounterSwitch()

        var value = ""
        var value_: String
        var index = randList_.randListIntPop(randList_.variableIdList)
        if (variable.size == 2) index = 0
        else
            while (index >= variable.size)
                index = randList_.randListIntPop(randList_.variableIdList)
        if (index < variable.size) {
            if (IDENTIFIER.contains(variable[index]) || index == 0)
                index++
            value = variable[index]
            value_ = variable[index - 1]
            variable.remove(value)
            variable.remove(value_)
        }
        program_.addAll(Case(value, nestingLevel, p, visibleVar))
        caseNum_--
        while (caseNum_ > 1 && variable.size != 0 && randList_.randListBoolPop(randList_.listBool)) {
            index = randList_.randListIntPop(randList_.variableIdList)
            if (variable.size == 1) index = 0
            else
                while (index >= variable.size)
                    index = randList_.randListIntPop(randList_.variableIdList)
            if (index < variable.size) {
                if (IDENTIFIER.contains(variable[index]) || index == 0)
                    index++
                value = variable[index]
                value_ = variable[index - 1]
                variable.remove(value)
                variable.remove(value_)
            }
            program_.addAll(Case(value, nestingLevel, p, visibleVar))
            caseNum_--
        }
        program_.addAll(Case(DEFAULT, nestingLevel, p, visibleVar))

        program_.add(BRACE_)
        program_.add(CARRIAGE_RETURN)
        return program_
    }

}