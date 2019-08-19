package com.example

import java.io.File

class Generator {
    var program: Program = Program()
    var parameters: ProgramParameters = ProgramParameters()
    var randList_: RandList = RandList()

    constructor() { }

    constructor(param: ProgramParameters) {
        parameters = param
        if (parameters.getRandSeed() == 1)
            randList_ = RandList(parameters.getRandSeed(), 150, parameters.getVariablesNum())
        else
            randList_ = RandList(parameters.getRandSeed(), 150, parameters.getVariablesNum(), parameters.getArgumentsNum())
    }

    fun Include(libr_numb: Int): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        program_.add("${INCLUDE[0]}")
        program_.add("${LIBRARY[libr_numb]}")
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

    fun itemSelection() : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        when (parameters.getTask_()) {
            1 -> { //arithmetic operating block
//                program_.addAll(firstTask(parameters, 1))
            }
            else -> {
/*
                val numList = randList(Random(parameters.getRandSeed().toLong()), 1, MAX_VALUE, parameters.getVariablesNum() * parameters.getArgumentsNum())
                val listBool = randList(Random(parameters.getRandSeed().toLong()), 0, 2, size)
                val listInt = randList(Random(parameters.getRandSeed().toLong()), 0, parameters.getVariablesNum(), parameters.getVariablesNum() * 10)
                val listFloat = randListFloat(Random(parameters.getRandSeed().toLong()), 0, 9, parameters.getVariablesNum() * 5)
                val operationIdList = randList(Random(parameters.getRandSeed().toLong()), 0, OPERATIONS.size, 70) //индексы операторов
                val listCondition = randList(Random(parameters.getRandSeed().toLong()), 0, 3, size/*parameters.getPrintfNum() + parameters.getVariablesNum()*/)
*/

                when (parameters.getTask_()) {
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
            }
        }
        return program_
    }

    fun InitModOperation(argNum: Int, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (argNum > 2) {
            var a : String
            if (randList_.randListBoolPop(randList_.listBool) && visibleVar.getVariableInt().size != 0)
                a = visibleVar.getVariableIntIndex(randList_.rand(0, visibleVar.getVariableInt().size, parameters.getRandSeed()))
            else
                a = "${randList_.randListIntPop(randList_.numList)}"
            var b = randList_.randListIntPop(randList_.numList)
            while (b == 0)
                b = randList_.randListIntPop(randList_.numList)
            program_.add("$a$MOD$b")

            if (argNum - 2 > 1 && randList_.randListBoolPop(randList_.listBool))
                program_.addAll(InitAddition(argNum - 2, visibleVar))
            else {
                if (argNum - 2 > 2 && randList_.randListBoolPop(randList_.listBool)) {
                    var i = randList_.randListIntPop(randList_.operationIdList)
                    while (ARITHMETIC_OPERATIONS[i] == MOD || ARITHMETIC_OPERATIONS[i] == DIV || ARITHMETIC_OPERATIONS[i] == MULTIPLICATION)
                        i = randList_.randListIntPop(randList_.operationIdList)
                    program_.add("${ARITHMETIC_OPERATIONS[i]}")
                    program_.addAll(InitModOperation(argNum - 2, visibleVar))
                }
            }
        }
        return program_
    }

    fun InitAddition(argNum: Int, visibleVar: Program): MutableList<String> {
        val program_: MutableList<String> = mutableListOf()

        if (argNum > 1) {
            var i = randList_.randListIntPop(randList_.operationIdList)
            while (ARITHMETIC_OPERATIONS[i] == MOD)
                i = randList_.randListIntPop(randList_.operationIdList)
            program_.add("${ARITHMETIC_OPERATIONS[i]}")
            if (randList_.randListBoolPop(randList_.listBool)) {
                if (randList_.randListBoolPop(randList_.listBool) && visibleVar.getVariableFloat().size != 0)
                    program_.add("${visibleVar.getVariableFloatIndex(randList_.rand(0, visibleVar.getVariableFloat().size, parameters.getRandSeed()))}")
                else
                    program_.add("${randList_.randListFloatPop(randList_.listFloat)}")
            }
            else {
                if (randList_.randListBoolPop(randList_.listBool) && visibleVar.getVariableInt().size != 0)
                    program_.add("${visibleVar.getVariableIntIndex(randList_.rand(0, visibleVar.getVariableInt().size, parameters.getRandSeed()))}")
                else
                    program_.add("${randList_.randListIntPop(randList_.numList)}")
            }

            if (argNum - 1 > 1 && randList_.randListBoolPop(randList_.listBool))
                program_.addAll(InitAddition(argNum - 1, visibleVar))
            else {
                if (argNum - 1 > 2 && randList_.randListBoolPop(randList_.listBool)) {
                    i = randList_.randListIntPop(randList_.operationIdList)
                    while (ARITHMETIC_OPERATIONS[i] == MOD || ARITHMETIC_OPERATIONS[i] == DIV || ARITHMETIC_OPERATIONS[i] == MULTIPLICATION)
                        i = randList_.randListIntPop(randList_.operationIdList)
                    program_.add("${ARITHMETIC_OPERATIONS[i]}")
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
            1 -> { //*
/*
                val initialized_args = rand(from, to - 1, parameters.getRandSeed())   //номера инициализированных переменных
                val uninitialized_args = parameters.getVariablesNum() - 2          //номера неинициализированных переменных

                val randList4 = randList(Random(parameters.getRandSeed().toLong()), from, MAX_VALUE, initialized_args + 1)
                for (i in 0..initialized_args) {
                    program_.add("$UNSIGNED $INT ")
                    program_.add("${IDENTIFIER[i]} $EQUALLY")
                    program_.add(" ${randListIntPop(randList4)}")
                    program_.add(END_OF_LINE)
                }

                if (uninitialized_args > -1) {
                    var i = initialized_args + 1
                    program_.add("$UNSIGNED $INT")
                    for (j: Int in i..uninitialized_args) {
                        program_.add(" ${IDENTIFIER[j]}$COMMA")
                        i++
                    }
                    program_.add(" ${IDENTIFIER[i]}$END_OF_LINE")
                }
                return program_
*/
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
                    var index = randList_.randListIntPop(randList_.listCondition)
                    val visibleVar = findVisibleVar(program_)
                    when(index) {
                        0 -> {
                            program_.add("$BOOL")
                            program_.add(" ")
                            program_.add("${IDENTIFIER[i]}")
                            program_.add("$EQUALLY")
                            if (randList_.randListBoolPop(randList_.listBool) && visibleVar.getVariableBool().size != 0) {
                                if (randList_.randListBoolPop(randList_.listBool))
                                    program_.add("$LOGICAL_NEGATION")
                                program_.add("${visibleVar.getVariableBoolIndex(randList_.rand(0, visibleVar.getVariableBool().size, parameters.getRandSeed()))}")
                            }
                            else
                                program_.add("${randList_.randListBoolPop(randList_.listBool)}")
                            program.getVariableBool().add("${IDENTIFIER[i]}")
                        }
                        else -> {
                            when (index) {
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
                                program_.addAll(InitModOperation(parameters.getArgumentsNum(), /*OPERATIONS,*/ visibleVar))
                            else {
                                if (randList_.randListBoolPop(randList_.listBool)) {
                                    if (randList_.randListBoolPop(randList_.listBool) && visibleVar.getVariableFloat().size != 0)
                                        program_.add("${visibleVar.getVariableFloatIndex(randList_.rand(0, visibleVar.getVariableFloat().size, parameters.getRandSeed()))}")
                                    else
                                        program_.add("${randList_.randListFloatPop(randList_.listFloat)}")
                                }
                                else {
                                    if (randList_.randListBoolPop(randList_.listBool) && visibleVar.getVariableInt().size != 0)
                                        program_.add("${visibleVar.getVariableIntIndex(randList_.rand(0, visibleVar.getVariableInt().size, parameters.getRandSeed()))}")
                                    else
                                        program_.add("${randList_.randListIntPop(randList_.numList)}")
                                }
                                if (parameters.getArgumentsNum() > 1 && randList_.randListBoolPop(randList_.listBool))
                                    program_.addAll(InitAddition(parameters.getArgumentsNum(), /*OPERATIONS*/ visibleVar))
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

    fun Printf(task: Int): MutableList<String> {
        val prog_: MutableList<String> = mutableListOf()
        prog_.add("$PRINTF$ROUND_BRACKET$QUOTES$SQUARE_BRACKET")
        when (task) {
            /*1 -> {
                while (!checkIdentifier(program_, randList.first())) {
                    randList.add(randList.first())
                    randListIntPop(randList)
                }
                prog_.add("%i${BRACKETS[5]}$PRINT_CARRIAGE_RETURN$QUOTES$COMMA ${IDENTIFIER[randListIntPop(randList)]}")
            }*/
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
        var index = randList_.randListIntPop(randList_.listInt)
        while (!(index > -1 && index < visibleVar.getVariableInt().size))
            index = randList_.randListIntPop(randList_.listInt)
        variable.add("${visibleVar.getVariableIntIndex(index)}")
        return variable
    }

    fun returnIdentif(visibleVar: Program): MutableList<String> {
        val variable: MutableList<String> = mutableListOf()
        if (visibleVar.getVariableInt().size > 0 && visibleVar.getVariableFloat().size > 0) {
            if (randList_.randListBoolPop(randList_.listBool)) { // int variable
                var index = randList_.randListIntPop(randList_.listInt)
                while (!(index > -1 && index < visibleVar.getVariableInt().size))
                    index = randList_.randListIntPop(randList_.listInt)
                variable.add("${visibleVar.getVariableIntIndex(index)}")
                return variable
            }
            else { // float variable
                var index = randList_.randListIntPop(randList_.listInt)
                while (!(index > -1 && index < visibleVar.getVariableFloat().size))
                    index = randList_.randListIntPop(randList_.listInt)
                variable.add("${visibleVar.getVariableFloatIndex(index)}")
                return variable
            }
        }
        else {
            if (visibleVar.getVariableInt().size == 0) {
                var index = randList_.randListIntPop(randList_.listInt)
                while (!(index > -1 && index < visibleVar.getVariableFloat().size))
                    index = randList_.randListIntPop(randList_.listInt)
                variable.add("${visibleVar.getVariableFloatIndex(index)}")
                return variable
            }
            if (visibleVar.getVariableFloat().size == 0) {
                var index = randList_.randListIntPop(randList_.listInt)
                while (!(index > -1 && index < visibleVar.getVariableInt().size))
                    index = randList_.randListIntPop(randList_.listInt)
                variable.add("${visibleVar.getVariableIntIndex(index)}")
                return variable
            }
        }

        if (variable.isEmpty())
            returnIdentif(visibleVar)

        return variable
    }

    fun arithmeticCondition(visibleVar: Program): MutableList<String> {
        val condition: MutableList<String> = mutableListOf()
        var i = randList_.randListIntPop(randList_.listInt)
        while (!(i > -1 && i < ARITHMETIC_OPERATIONS.size))
            i = randList_.randListIntPop(randList_.listInt)

        if (ARITHMETIC_OPERATIONS[i] == MOD) {
            if (visibleVar.getVariableInt().size > 1) {
                var a = returnIntVariable(visibleVar).joinToString("")
                val b = returnIntVariable(visibleVar).joinToString("")
                while (a == b)
                    a = returnIntVariable(visibleVar).joinToString("")
                condition.add("$a")
                condition.add("${ARITHMETIC_OPERATIONS[i]}")
                condition.add("$b")
            } else condition.addAll(arithmeticCondition(visibleVar))
        }
        if (ARITHMETIC_OPERATIONS[i] != MOD) {
            if (visibleVar.getVariableInt().size == 0 && visibleVar.getVariableFloat().size == 2) {
                var a = visibleVar.getVariableFloatIndex(0)
                var b = visibleVar.getVariableFloatIndex(1)
                condition.add("$a")
                condition.add("${ARITHMETIC_OPERATIONS[i]}")
                condition.add("$b")
                return condition
            }
            if (visibleVar.getVariableInt().size == 2 && visibleVar.getVariableFloat().size == 0) {
                var a = visibleVar.getVariableIntIndex(0)
                var b = visibleVar.getVariableIntIndex(1)
                condition.add("$a")
                condition.add("${ARITHMETIC_OPERATIONS[i]}")
                condition.add("$b")
                return condition
            }
            var a = returnIdentif(visibleVar).joinToString("")
            val b = returnIdentif(visibleVar).joinToString("")
            while (a == b)
                a = returnIdentif(visibleVar).joinToString("")

            condition.add("$a")
            condition.add("${ARITHMETIC_OPERATIONS[i]}")
            condition.add("$b")
        }
        return condition
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
            program_.add("${visibleVar.getVariableIntIndex(0)}")
            return program_
        }
        if (visibleVar.getVariableBool().size == 0 && visibleVar.getVariableFloat().size == 1 && visibleVar.getVariableInt().size == 0) {
            program_.add("${visibleVar.getVariableFloatIndex(0)}")
            return program_
        }
        var index = randList_.randListIntPop(randList_.listCondition)
        if ((index == 1 || index == 2))
            if ((visibleVar.getVariableFloat().size == 0 && visibleVar.getVariableInt().size == 0)
                || (visibleVar.getVariableFloat().size == 0 && visibleVar.getVariableInt().size == 1)
                || (visibleVar.getVariableFloat().size == 1 && visibleVar.getVariableInt().size == 0)) {
                index = 0
            }
        while (visibleVar.getVariableBool().size == 0 && index == 0)
            index = randList_.randListIntPop(randList_.listCondition)

        when (index) {
            0 -> { //bool variable
                if (visibleVar.getVariableBool().size > 0) {
                    if (visibleVar.getVariableBool().size == 1) {
                        var i = randList_.randListIntPop(randList_.listInt)
                        while ( !(i > -1 && i < visibleVar.getVariableBool().size) )
                            i = randList_.randListIntPop(randList_.listInt)

                        if (!randList_.randListBoolPop(randList_.listBool))
                            program_.add("$LOGICAL_NEGATION")
                        program_.add("${visibleVar.getVariableBoolIndex(i)}")
                        return program_
                    }
                    if (visibleVar.getVariableBool().size > 1) {
                        var i = randList_.randListIntPop(randList_.listInt)
                        var i_ = randList_.randListIntPop(randList_.listInt)
                        while (!(i > -1 && i < visibleVar.getVariableBool().size))
                            i = randList_.randListIntPop(randList_.listInt)

                        while (!(i_ > -1 && i_ < visibleVar.getVariableBool().size))
                            i_ = randList_.randListIntPop(randList_.listInt)

                        index = randList_.randListIntPop(randList_.listCondition)
                        when (index) {
                            0 -> {
                                program_.addAll(logicalExpr(visibleVar.getVariableBoolIndex(i), visibleVar.getVariableBoolIndex(i_)))
                                /*
                                prog_.add("${prog.getVariableBoolIndex(i)}")
                                if (randListBoolPop(ListBool))
                                    prog_.add("$LOGICAL_AND")
                                else
                                    prog_.add("$LOGICAL_OR")
                                prog_.add("${prog.getVariableBoolIndex(i_)}")
                                */
                            }
                            1 -> {
                                program_.addAll(logicalExpr("$LOGICAL_NEGATION${visibleVar.getVariableBoolIndex(i)}", visibleVar.getVariableBoolIndex(i_)))
                                /*
                                prog_.add("$LOGICAL_NEGATION")
                                prog_.add("${prog.getVariableBoolIndex(i)}")
                                if (randListBoolPop(ListBool))
                                    prog_.add("$LOGICAL_AND")
                                else
                                    prog_.add("$LOGICAL_OR")
                                prog_.add("${prog.getVariableBoolIndex(i_)}")
                                */
                            }
                            2 -> {
                                program_.addAll(logicalExpr(visibleVar.getVariableBoolIndex(i), "$LOGICAL_NEGATION${visibleVar.getVariableBoolIndex(i_)}"))
                                /*
                                prog_.add("${prog.getVariableBoolIndex(i)}")
                                if (randListBoolPop(ListBool))
                                    prog_.add("$LOGICAL_AND")
                                else
                                    prog_.add("$LOGICAL_OR")
                                prog_.add("$LOGICAL_NEGATION")
                                prog_.add("${prog.getVariableBoolIndex(i_)}")
                                */
                            }
                        }
                        return program_
                    }
                }
            }
            1 -> { //int or float variable - comparison
                var i = randList_.randListIntPop(randList_.listInt)
                while (!(i > -1 && i < RELATIONAL_OPERATIONS.size))
                    i = randList_.randListIntPop(randList_.listInt)

                var a = returnIdentif(visibleVar).joinToString("")
                var b = returnIdentif(visibleVar).joinToString("")

                while (a == b)
                    a = returnIdentif(visibleVar).joinToString("")

                program_.add("$a")
                program_.add("${RELATIONAL_OPERATIONS[i]}")
                program_.add("$b")
                return program_
            }
            2 -> { //int or float variable - arithmetic operation
                program_.addAll(arithmeticCondition(visibleVar))
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

        if (randList_.randListBoolPop(randList_.listBool) && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterIf())
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

        if (randList_.randListBoolPop(randList_.listBool) && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterIf())
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

        if (randList_.randListBoolPop(randList_.listBool) && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterFor())
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
                    //if (randListBoolPop(listBool)) {
                    program_.add("${condition[i]}")
                    program_.add("$EQUALLY")
                    program_.add("$LOGICAL_NEGATION")
                    program_.add("${condition[i]}")
                    program_.add("$END_OF_LINE")
                    /*}
                    else {

                    }*/

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

        if (randList_.randListBoolPop(randList_.listBool) && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterWhile())
            program_.addAll(While(nestingLevel - 1, p))

        program_.add("$BRACE_")
        program_.add("$CARRIAGE_RETURN")
        return program_
    }

    fun DoWhile(nestingLevel: Int, prog: MutableList<String>) : MutableList<String> {
        val program_: MutableList<String> = mutableListOf()
        if (parameters.getVariablesNum() - program.getCounterVariables() > 0 ) {
            program_.addAll(Init())
        }
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

        if (randList_.randListBoolPop(randList_.listBool) && nestingLevel > 1 && parameters.getPrintfNum() > program.getCounterDoWhile())
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