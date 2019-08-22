package com.example

import java.io.File

class Generator {
    var program: Program = Program()
    var parameters: ProgramParameters = ProgramParameters()
    var randList_: RandList = RandList()

    constructor() { }

    constructor(param: ProgramParameters) {
        parameters = param
        if (parameters.getTask_() == 1)
            randList_ = RandList(parameters.getRandSeed(), 50, parameters.getVariablesNum(), parameters.getOperationType().size)
        else
            randList_ = RandList(parameters.getRandSeed(), 60, parameters.getVariablesNum())
    }

    fun Include(index: Int): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        program_.add("${INCLUDE[0]}")
        program_.add("${LIBRARY[index]}")
        program_.add("${INCLUDE[1]}")
        program_.add("$CARRIAGE_RETURN")
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
        program_.add("$RETURN")
        program_.add(" ")
        program_.add("${index}")
        program_.add("$END_OF_LINE")
        return program_
    }

    fun programGenerate(): MutableList<String> {
        program.getProgram().addAll(Include(0))
        if (parameters.getTask_() != 1)
            program.getProgram().addAll(Include(1))
        program.getProgram().add("$CARRIAGE_RETURN")
        program.getProgram().addAll(Main())

        var file = File("func.c")
        file.writeText(program.getProgram().joinToString(""))
        Runtime.getRuntime().exec("clang-format -i func.c")
        return program.getProgram()
    }

    fun TermAddition(argNum: Int, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (argNum > 1) {
            val operation = parameters.OPERATIONS_TYPE[randList_.randListIntPop(randList_.operationIdList)]
            if (operation != BITWISE_OPERATIONS[0] && operation != BITWISE_OPERATIONS[1]) {
                program_.add(operation)

                /*if (argNum > 2 && randList_.randListBoolPop(randList_.listBool)) {
                    program_.add(ROUND_BRACKET)
                    if (visibleVar.getVariableUnsInt().size != 0 && randList_.randListBoolPop(randList_.listBool)) {
                        var index = randList_.randListIntPop(randList_.variableIdList)
                        while (index > visibleVar.getVariableUnsInt().size - 1)
                            index = randList_.randListIntPop(randList_.variableIdList)
                        program_.add("${visibleVar.getVariableUnsIntIndex(index)}")
                    }
                    else {
                        program_.add("${randList_.randListIntPop(randList_.listInt)}")
                    }
                    program_.addAll(TermAddition(argNum - 1, visibleVar))
                    program_.add(ROUND_BRACKET_)
                }
                else {*/
                val flag = randList_.randListBoolPop(randList_.listBool)
                if (flag) program_.add(ROUND_BRACKET)
                if (visibleVar.getVariableUnsInt().size != 0 && randList_.randListBoolPop(randList_.listBool)) {
                    var index = randList_.randListIntPop(randList_.variableIdList)
                    while (index > visibleVar.getVariableUnsInt().size - 1)
                        index = randList_.randListIntPop(randList_.variableIdList)
                    program_.add("${visibleVar.getVariableUnsIntIndex(index)}")
                }
                else {
                    program_.add("${randList_.randListIntPop(randList_.listInt)}")
                }
                if (argNum - 1 > 1 && randList_.randListBoolPop(randList_.listBool))
                    program_.addAll(TermAddition(argNum - 1, visibleVar))
                //}
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

        if (!parameters.getRedefinitionVar()) {
            var variable = IDENTIFIER[randList_.randListIntPop(randList_.variableIdList)]
            while (visibleVar.getVariableUnsInt().contains(variable))
                variable = IDENTIFIER[randList_.randListIntPop(randList_.variableIdList)]
            program_.add(variable)
        }
        else program_.add(IDENTIFIER[randList_.randListIntPop(randList_.variableIdList)])
        program_.add(EQUALLY)

        val flag = randList_.randListBoolPop(randList_.listBool)
        if (flag) program_.add(ROUND_BRACKET)

        if (visibleVar.getVariableUnsInt().size != 0 && randList_.randListBoolPop(randList_.listBool)) {
            var index = randList_.randListIntPop(randList_.variableIdList)
            while (index > visibleVar.getVariableUnsInt().size - 1)
                index = randList_.randListIntPop(randList_.variableIdList)
            program_.add("${visibleVar.getVariableUnsIntIndex(index)}")
        }
        else program_.add("${randList_.randListIntPop(randList_.listInt)}")

        val operation = parameters.OPERATIONS_TYPE[randList_.randListIntPop(randList_.operationIdList)]
        if (operation != BITWISE_OPERATIONS[0] && operation != BITWISE_OPERATIONS[1]) {
            program_.add(operation)
            if (visibleVar.getVariableUnsInt().size != 0 && randList_.randListBoolPop(randList_.listBool)) {
                var index = randList_.randListIntPop(randList_.variableIdList)
                while (index > visibleVar.getVariableUnsInt().size - 1)
                    index = randList_.randListIntPop(randList_.variableIdList)
                program_.add("${visibleVar.getVariableUnsIntIndex(index)}")
            }
            else program_.add("${randList_.randListIntPop(randList_.listInt)}")
            if (parameters.getArgumentsNum() > 2 && randList_.randListBoolPop(randList_.listBool))
                program_.addAll(TermAddition(parameters.getArgumentsNum(), visibleVar))
        }
        else {
            program_.add(operation)
            program_.add("${randList_.randListIntPop(randList_.listInt)}")
        }

        if (flag) program_.add(ROUND_BRACKET_)
        program_.add(END_OF_LINE)
        program.incrementCounterTerm()

        if (parameters.getPrintfNum() == 2 && program.getCounterTerm() % (parameters.getStatementsNum() / (parameters.getPrintfNum())) == 0
            || parameters.getStatementsNum() - program.getCounterTerm() == 0
            || program.getCounterTerm() % (parameters.getStatementsNum() / (parameters.getPrintfNum() - 1)) == 0)
            program_.addAll(Printf(visibleVar))

        return program_
    }

    fun itemSelection() : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        when (parameters.getTask_()) {
            1 -> { //arithmetic operating block
                program_.addAll(Init())
                do {
                    val visibleVar = findVisibleVar(program_)
                    program_.addAll(Term(visibleVar))
                } while (parameters.getStatementsNum() - program.getCounterTerm() > 0)
            }
            2 -> { //if block
                do {
                    program_.addAll(If(parameters.getNestingLevel(), program_))
                } while (parameters.getPrintfNum() - program.getCounterIf() > 0)
            }
/*
                    3 -> {
                        //switch block
                    }
*/
            4 -> { //while block
                do {
                    program_.addAll(While(parameters.getNestingLevel(), program_))
                } while (parameters.getPrintfNum() - program.getCounterWhile() > 0)
            }
            5 -> { //do while block
                do {
                    program_.addAll(DoWhile(parameters.getNestingLevel(), program_))
                } while (parameters.getPrintfNum() - program.getCounterDoWhile() > 0)
            }
            6 -> { //for block
                do {
                    program_.addAll(ForLoop(parameters.getNestingLevel(), program_))
                } while (parameters.getPrintfNum() - program.getCounterFor() > 0)
            }

        }
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

    fun InitModOperation(argNum: Int, visibleVar: Program): MutableList<String> {
            val program_: MutableList<String> = mutableListOf()

            if (argNum > 2) {
                var a : String
                if (visibleVar.getVariableInt().size != 0 && randList_.randListBoolPop(randList_.listBool)) {
                    var index = randList_.randListIntPop(randList_.variableIdList)
                    while (index > visibleVar.getVariableInt().size - 1)
                        index = randList_.randListIntPop(randList_.variableIdList)
                    a = visibleVar.getVariableIntIndex(index)
                }
                else
                    a = "${randList_.randListIntPop(randList_.listInt)}"
                var b = randList_.randListIntPop(randList_.listInt)
                while (b == 0)
                    b = randList_.randListIntPop(randList_.listInt)
                program_.add("$a$MOD$b")

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

    fun AddFloatOrInt(visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (randList_.randListBoolPop(randList_.listBool)) { //тогда выбираем перем/число типа float
            if (visibleVar.getVariableFloat().size != 0 && randList_.randListBoolPop(randList_.listBool)) {
//                program_.add("${visibleVar.getVariableFloatIndex(randList_.rand(0, visibleVar.getVariableFloat().size, parameters.getRandSeed()))}")
                var index = randList_.randListIntPop(randList_.variableIdList)
                while (index > visibleVar.getVariableFloat().size - 1)
                    index = randList_.randListIntPop(randList_.variableIdList)
                program_.add(visibleVar.getVariableFloatIndex(index))
            }
            else
                program_.add("${randList_.randListFloatPop(randList_.listFloat)}")
        }
        else { //иначе выбираем перем/число типа int
            if (visibleVar.getVariableInt().size != 0 && randList_.randListBoolPop(randList_.listBool)) {
//                program_.add("${visibleVar.getVariableIntIndex(randList_.rand(0, visibleVar.getVariableInt().size, parameters.getRandSeed()))}")
                var index = randList_.randListIntPop(randList_.variableIdList)
                while (index > visibleVar.getVariableInt().size - 1)
                    index = randList_.randListIntPop(randList_.variableIdList)
                program_.add(visibleVar.getVariableIntIndex(index))
            }
            else
                program_.add("${randList_.randListIntPop(randList_.listInt)}")
        }
        return program_
    }

    fun InitAddition(argNum: Int, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (argNum > 1) {
            program_.add("${ARITHMETIC_OPERATIONS[randList_.randListIntPop(randList_.operationIdList)]}")
            program_.addAll(AddFloatOrInt(visibleVar))

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
    fun Init(): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        when (parameters.getTask_()) {
            1 -> {
                //номера инициализированных переменных
                val initialized_args = randList_.rand(0, parameters.getVariablesNum() - 1, parameters.getRandSeed())

                for (i in 0..initialized_args) {
                    program_.add(UNSIGNED_INT)
                    program_.add(" ")
                    program_.add("${IDENTIFIER[i]}")
                    program_.add(EQUALLY)
                    program_.add("${randList_.randListIntPop(randList_.listInt)}")
                    program_.add(END_OF_LINE)
                    program.getVariableUnsInt().add("${IDENTIFIER[i]}")
                    program.incrementCounterVariables()
                }

                if (!parameters.redefiniton_var)
                    parameters.statements_num = (parameters.getVariablesNum()) - (initialized_args + 1)

                program_.add(UNSIGNED_INT)
                for (i: Int in initialized_args + 1..parameters.getVariablesNum() - 2) {
                    program_.add(" ")
                    program_.add("${IDENTIFIER[i]}")
                    program_.add(COMMA)
                }
                program_.add(" ")
                program_.add("${IDENTIFIER[parameters.getVariablesNum() - 1]}")
                program_.add(END_OF_LINE)

                return program_
            }
            else -> {
                //инициализация переменных
                var j = parameters.getVariablesNum() / parameters.getPrintfNum()
                if (j == 0)
                    j = 1
                if (parameters.getVariablesNum() % parameters.getPrintfNum() != 0 && parameters.getVariablesNum() - program.getCounterVariables() != j && randList_.randListBoolPop(randList_.listBool))
                    j += 1

                if (parameters.getPrintfNum() - program.getCounterPrintf() == 0)
                    j = parameters.getVariablesNum() - program.getCounterVariables()

                for (i in program.getCounterVariables()..(program.getCounterVariables() + j - 1)) {
                    val index_ = randList_.randListIntPop(randList_.listCondition)
                    val visibleVar = findVisibleVar(program_)
                    when(index_) {
                        0 -> {
                            program_.add("$BOOL")
                            program_.add(" ")
                            program_.add("${IDENTIFIER[i]}")
                            program_.add("$EQUALLY")
                            if (visibleVar.getVariableBool().size != 0 && randList_.randListBoolPop(randList_.listBool)) {
                                if (randList_.randListBoolPop(randList_.listBool))
                                    program_.add("$LOGICAL_NEGATION")
//                                program_.add("${visibleVar.getVariableBoolIndex(randList_.rand(0, visibleVar.getVariableBool().size, parameters.getRandSeed()))}")

                                    var index = randList_.randListIntPop(randList_.variableIdList)
                                    while (index > visibleVar.getVariableBool().size - 1)
                                        index = randList_.randListIntPop(randList_.variableIdList)
                                    program_.add("${visibleVar.getVariableBoolIndex(index)}")

                            }
                            else
                                program_.add("${randList_.randListBoolPop(randList_.listBool)}")
                            program.getVariableBool().add("${IDENTIFIER[i]}")
                        }
                        else -> {
                            when (index_) {
                                1 -> {
                                    program_.add("$INT")
                                    program.getVariableInt().add("${IDENTIFIER[i]}")
                                }
                                2 -> {
                                    program_.add("$FLOAT")
                                    program.getVariableFloat().add("${IDENTIFIER[i]}")
                                }
                            }
                            program_.add(" ")
                            program_.add("${IDENTIFIER[i]}")
                            program_.add("$EQUALLY")

                            if (parameters.getArgumentsNum() > 2 && randList_.randListBoolPop(randList_.listBool))
                                program_.addAll(InitModOperation(parameters.getArgumentsNum(), visibleVar))
                            else {
                                program_.addAll(AddFloatOrInt(visibleVar))
                                if (parameters.getArgumentsNum() > 1 && randList_.randListBoolPop(randList_.listBool))
                                    program_.addAll(InitAddition(parameters.getArgumentsNum(), visibleVar))
                            }
                        }
                    }
                    program_.add("$END_OF_LINE")
                    program.incrementCounterVariables()
                }
            }
        }
        return program_
    }

    fun Printf(visibleVar: Program): MutableList<String> {
        val prog_: MutableList<String> = mutableListOf()
        /*while (!checkIdentifier(program_, randList.first())) {
            randList.add(randList.first())
            randListIntPop(randList)
        }*/
        if (visibleVar.getVariableUnsInt().size != 0) {
            var index = randList_.randListIntPop(randList_.variableIdList)
            while (index > visibleVar.getVariableUnsInt().size - 1)
                index = randList_.randListIntPop(randList_.variableIdList)
            prog_.add("$PRINTF$ROUND_BRACKET$QUOTES$SQUARE_BRACKET")
            prog_.add("%i$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES$COMMA ${visibleVar.getVariableUnsIntIndex(index)}")
            program.incrementCounterPrintf()
            prog_.add("$ROUND_BRACKET_$END_OF_LINE")
        }
        return prog_
    }

    fun Printf(task: Int): MutableList<String> {
        val prog_: MutableList<String> = mutableListOf()
        prog_.add("$PRINTF$ROUND_BRACKET$QUOTES$SQUARE_BRACKET")
        when (task) {
            2 -> prog_.add("$IF $DASH ${program.getCounterIf()}$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES")
            0 -> prog_.add("$ELSE $DASH ${program.getCounterElse()}$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES")
            //3 -> prog_.add("")
            4 -> prog_.add("$WHILE $DASH ${program.getCounterWhile()}$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES")
            5 -> prog_.add("$DO $WHILE $DASH ${program.getCounterDoWhile()}$SQUARE_BRACKET_$PRINT_CARRIAGE_RETURN$QUOTES")
            6 -> prog_.add("$FOR $DASH ${program.getCounterFor()}] %li$PRINT_CARRIAGE_RETURN$QUOTES$COMMA i")
        }
        program.incrementCounterPrintf()
        prog_.add("$ROUND_BRACKET_$END_OF_LINE")
        return prog_
    }

    fun returnIntVariable(visibleVar: Program): MutableList<String> {
        val variable: MutableList<String> = mutableListOf()
        if (visibleVar.getVariableInt().size == 1)
            variable.add(visibleVar.getVariableIntIndex(0))
        else {
            var index = randList_.randListIntPop(randList_.variableIdList)
            while (index > visibleVar.getVariableInt().size - 1)
                index = randList_.randListIntPop(randList_.variableIdList)
            variable.add(visibleVar.getVariableIntIndex(index))
        }
        return variable
    }

    fun returnFloatVariable(visibleVar: Program): MutableList<String> {
        val variable: MutableList<String> = mutableListOf()
        if (visibleVar.getVariableFloat().size == 1)
            variable.add(visibleVar.getVariableFloatIndex(0))
        else {
            var index = randList_.randListIntPop(randList_.variableIdList)
            while (index > visibleVar.getVariableFloat().size - 1)
                index = randList_.randListIntPop(randList_.variableIdList)
            variable.add(visibleVar.getVariableFloatIndex(index))
        }
        return variable
    }

    fun returnVariable(visibleVar: Program): MutableList<String> {
        val variable: MutableList<String> = mutableListOf()
        if (randList_.randListBoolPop(randList_.listBool))
            variable.addAll(returnIntVariable(visibleVar))
        else
            variable.addAll(returnFloatVariable(visibleVar))
        return variable
    }

    fun findClosingBracket(program_: MutableList<String>, i: Int) : Int {
        var j = i + 1
        while (j < program_.size - 1) {
            if (program_[j] == BRACE)
                j = findClosingBracket(program_, j)
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
            if (program_[i] == BRACE /*&& count_brace != 0*/) {
                val k = findClosingBracket(program_, i)
                if (k != 0)
                    i = k
            }
            if (program_[i] == UNSIGNED_INT && program_[i + 3] == EQUALLY) {
                program.getVariableUnsInt().add("${program_[i + 2]}")
                program.incrementCounterVariables()
            }
            if (program_[i] == INT) {
                program.getVariableInt().add("${program_[i + 2]}")
                program.incrementCounterVariables()
            }
            if (program_[i] == BOOL) {
                program.getVariableBool().add("${program_[i + 2]}")
                program.incrementCounterVariables()
            }
            if (program_[i] == FLOAT) {
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

    fun logicalExpr(a: String, b: String): MutableList<String> {
        val prog_: MutableList<String> = mutableListOf()

        prog_.add("$a")
        if (randList_.randListBoolPop(randList_.listBool))
            prog_.add("$LOGICAL_AND")
        else
            prog_.add("$LOGICAL_OR")
        prog_.add("$b")

        return prog_
    }

    fun Condition(prog: MutableList<String>): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        val visibleVar = findVisibleVar(prog)
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
                program_.add("$LOGICAL_NEGATION")
            program_.add(visibleVar.getVariableBoolIndex(0))
            return program_
        }

        var index = 0
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
                    var i = 0
                    var i_ = 1
                    if (visibleVar.getVariableBool().size > 2) {
                        i = randList_.randListIntPop(randList_.variableIdList)
                        while (i > visibleVar.getVariableBool().size - 1)
                            i = randList_.randListIntPop(randList_.variableIdList)

                        i_ = randList_.randListIntPop(randList_.variableIdList)
                        while (i_ > visibleVar.getVariableBool().size - 1)
                            i_ = randList_.randListIntPop(randList_.variableIdList)
                    }

                    when (randList_.randListIntPop(randList_.listCondition)) {
                        0 -> program_.addAll(logicalExpr(visibleVar.getVariableBoolIndex(i), visibleVar.getVariableBoolIndex(i_)))
                        1 -> program_.addAll(logicalExpr("$LOGICAL_NEGATION${visibleVar.getVariableBoolIndex(i)}", visibleVar.getVariableBoolIndex(i_)))
                        2 -> program_.addAll(logicalExpr(visibleVar.getVariableBoolIndex(i), "$LOGICAL_NEGATION${visibleVar.getVariableBoolIndex(i_)}"))
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
                    a = returnVariable(visibleVar).joinToString("")
                    b = returnVariable(visibleVar).joinToString("")

                    while (a == b)
                        a = returnVariable(visibleVar).joinToString("")
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
                        a = returnIntVariable(visibleVar).joinToString("")
                        b = returnIntVariable(visibleVar).joinToString("")
                        while (a == b)
                            a = returnIntVariable(visibleVar).joinToString("")
                        operation = MOD
                    }
                    else {
                        a = returnVariable(visibleVar).joinToString("")
                        b = returnVariable(visibleVar).joinToString("")
                        while (a == b)
                            a = returnVariable(visibleVar).joinToString("")
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
            program_.addAll(Init())

        program_.add("$IF")
        program_.add("$ROUND_BRACKET")

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)

        val p_: MutableList<String> = mutableListOf() //в.п.для хранение текущего условия
        p_.addAll(Condition(p))

        program_.addAll(p_)
        program_.add("$ROUND_BRACKET_")
        program_.add("$BRACE")
        program_.add("$CARRIAGE_RETURN")
        program.incrementCounterIf()
        program_.addAll(Printf(parameters.getTask_()))

        if (nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterIf() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(If(nestingLevel - 1, p))

        program_.add("$BRACE_")
        program_.add("$CARRIAGE_RETURN")

        if (randList_.randListBoolPop(randList_.listBool))
            program_.addAll(Else(nestingLevel - 1, p))

        return program_
    }

    fun Else(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        program_.add("$ELSE")
        program_.add("$BRACE")

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)

        program_.add("$CARRIAGE_RETURN")
        program.incrementCounterElse()
        program_.addAll(Printf(parameters.getTask_() - 2))

        if (nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterIf() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(If(nestingLevel - 1, p))

        program_.add("$BRACE_")
        program_.add("$CARRIAGE_RETURN")
        return program_
    }

    fun ForLoop(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (parameters.getVariablesNum() - program.getCounterVariables() > 0 )
            program_.addAll(Init())

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)

        program_.add("$FOR")
        program_.add("$ROUND_BRACKET")
        program_.add("$SIZE_T")
        program_.add(" ")
        program_.add("i")
        program_.add("$EQUALLY")
        program_.add("0")
        program_.add("$SEMICOLON")
        program_.add("i")
        program_.add("<")
        program_.add("${parameters.getSize()}")
        program_.add("$SEMICOLON")
        program_.add("i")
        program_.add("++")

        program_.add("$ROUND_BRACKET_")
        program_.add("$BRACE")
        program_.add("$CARRIAGE_RETURN")
        program.incrementCounterFor()
        program_.addAll(Printf(parameters.getTask_()))

        if (randList_.randListBoolPop(randList_.listBool))
            program_.addAll(ExitPoint())

        if (nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterFor() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(ForLoop(nestingLevel - 1, p))

        program_.add("$BRACE_")
        program_.add("$CARRIAGE_RETURN")
        return program_
    }

    fun varChange(condition: MutableList<String>): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        for (i: Int in 0..condition.size - 1) {
            for (j: Int in 0..program.getVariableBool().size - 1) { //bool variable
                if (condition[i] == program.getVariableBoolIndex(j)) {
                    program_.add("${condition[i]}")
                    program_.add("$EQUALLY")
                    program_.add("$LOGICAL_NEGATION")
                    program_.add("${condition[i]}")
                    program_.add("$END_OF_LINE")
                    return program_
                }
            }
            for (j: Int in 0..program.getVariableInt().size - 1) { //int variable
                if (condition[i] == program.getVariableIntIndex(j)) {
                    program_.add("${condition[i]}")
                    if (randList_.randListBoolPop(randList_.listBool))
                        program_.add("${SPECIAL_OPERATIONS[0]}")
                    else
                        program_.add("${SPECIAL_OPERATIONS[1]}")
                    program_.add("$END_OF_LINE")
                    return program_
                }
            }
            for (j: Int in 0..program.getVariableFloat().size - 1) { //float variable
                if (condition[i] == program.getVariableFloatIndex(j)) {
                    program_.add("${condition[i]}")
                    if (randList_.randListBoolPop(randList_.listBool))
                        program_.add("${SPECIAL_OPERATIONS[0]}")
                    else
                        program_.add("${SPECIAL_OPERATIONS[1]}")
                    program_.add("$END_OF_LINE")
                    return program_
                }
            }
        }
        return program_
    }

    fun While(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (parameters.getVariablesNum() - program.getCounterVariables() > 0 )
            program_.addAll(Init())

        program_.add("$WHILE")
        program_.add("$ROUND_BRACKET")

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)

        val p_: MutableList<String> = mutableListOf() //в.п.для хранение текущего условия
        p_.addAll(Condition(p))

        program_.addAll(p_)
        program_.add("$ROUND_BRACKET_")
        program_.add("$BRACE")
        program_.add("$CARRIAGE_RETURN")
        program_.addAll(varChange(p_))
        program.incrementCounterWhile()
        program_.addAll(Printf(parameters.getTask_()))

        if (randList_.randListBoolPop(randList_.listBool))
            program_.addAll(ExitPoint())

        if (nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterWhile() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(While(nestingLevel - 1, p))

        program_.add("$BRACE_")
        program_.add("$CARRIAGE_RETURN")
        return program_
    }

    fun DoWhile(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (parameters.getVariablesNum() - program.getCounterVariables() > 0 )
            program_.addAll(Init())

        val p: MutableList<String> = mutableListOf()
        p.addAll(prog)
        p.addAll(program_)

        program_.add("$DO")
        program_.add("$BRACE")
        program_.add("$CARRIAGE_RETURN")
        program.incrementCounterDoWhile()
        val p_: MutableList<String> = mutableListOf() //в.п.для хранение текущего условия
        p_.addAll(Condition(p))
        program_.addAll(varChange(p_))
        program_.addAll(Printf(parameters.getTask_()))

        if (randList_.randListBoolPop(randList_.listBool))
            program_.addAll(ExitPoint())

        if (nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterDoWhile() && randList_.randListBoolPop(randList_.listBool))
            program_.addAll(DoWhile(nestingLevel - 1, p))

        program_.add("$BRACE_")
        program_.add("$WHILE")
        program_.add("$ROUND_BRACKET")
        program_.addAll(p_)
        program_.add("$ROUND_BRACKET_")
        program_.add("$END_OF_LINE")

        return program_
    }

    fun ExitPoint(): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (randList_.randListBoolPop(randList_.listBool)) {
            program_.add("$CONTINUE")
            program_.add("$END_OF_LINE")
            return program_
        }
        if (randList_.randListBoolPop(randList_.listBool)) {
            program_.add("$BREAK")
            program_.add("$END_OF_LINE")
            return program_
        }
        if (randList_.randListBoolPop(randList_.listBool)) {
            program_.addAll(Return(0))
            return program_
        }
        return program_
    }

}