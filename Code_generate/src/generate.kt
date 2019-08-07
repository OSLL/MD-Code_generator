//package com.example

import org.jetbrains.annotations.Mutable
import java.io.File
import java.lang.Integer.parseInt
import java.util.*

const val MAX_VALUE: Int = 7
const val MIN_VALUE: Int = -MAX_VALUE

val BOOL = "bool"
val INT = "int"
val FLOAT = "float"
val SIZE_T = "size_t"
val UNSIGNED = "unsigned"
val MAIN = "main"
val ARITHMETIC_OPERATIONS: List<String> = listOf("+", "-", "*", "/", "%")
val MOD = "%"
val DIV = "/"
val MULTIPLICATION = "*"
val LOGICAL_NEGATION = "!"
val LOGICAL_AND = "&&"
val LOGICAL_OR = "||"
val LOGICAL_OPERATIONS: List<String> = listOf("!", "&&", "||")
val RELATIONAL_OPERATIONS: List<String> = listOf("<", ">", "<=", ">=", "==", "!=")
val SPECIAL_OPERATIONS: List<String> = listOf("++", "--")
val BITWISE_OPERATIONS: List<String> = listOf("<<", ">>", "|", "&")
val OPERATIONS: MutableList<String> = mutableListOf("+", "-", "/", "%")
val IDENTIFIER: List<String> = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
val CARRIAGE_RETURN = "\n"
val PRINT_CARRIAGE_RETURN = "\\n"
val EQUALLY = "="
val COMMA = ","
val DOT = "."
val DASH = "-"
val SEMICOLON = ";"
val COLON = ":"
val QUOTES = "\""
val END_OF_LINE = "$SEMICOLON$CARRIAGE_RETURN"
val LIBRARY: List<String> = listOf("stdio.h", "stdbool.h")
val INCLUDE: List<String> = listOf("#include <", ">")
val ROUND_BRACKET = "("
val ROUND_BRACKET_ = ")"
val BRACE = "{"
val BRACE_ = "}"
val SQUARE_BRACKET = "["
val SQUARE_BRACKET_ = "]"
val BRACKETS: List<String> = listOf("(", ")", "{", "}", "[", "]")
val TAB = "\t"
val BREAK = "break"
val CONTINUE = "continue"
val RETURN = "return"
val PRINTF = "printf"
val IF = "if"
val ELSE = "else"
val SWITCH = "switch"
val CASE = "case"
val DEFAULT = "default"
val WHILE = "while"
val DO = "do"
val FOR = "for"

//добавляет в контейнер переменную по индексу
fun Identifier(index: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(IDENTIFIER[index])
    return program_
}

//проверяет наличие выбранной переменной в контейнере
fun checkIdentifier(program: MutableList<String>, index: Int): Boolean {
    val value = "${IDENTIFIER[index]} $EQUALLY"
    for (i: Int in 0..(program.size - 1))
        if (program[i] == value)
            for (j: Int in (i + 1)..(program.size - 1))
                if (program[j] == END_OF_LINE)
                    return true
    return false
}

//проверяет наличие операторов сдвига (<<, >>) в контейнере
fun checkOperator(program: MutableList<String>): Boolean {
    val value_0 = " ${BITWISE_OPERATIONS[0]} "
    val value_1 = " ${BITWISE_OPERATIONS[1]} "
    if (program[program.size - 1] == value_0 || program[program.size - 1] == value_1)
        return true
    return false
}

//добавляет ранее инициализированную переменную в контейнер
fun Identifier(program: MutableList<String>, randList: MutableList<Int>): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    val index = randListIntPop(randList)
    val value = "${IDENTIFIER[index]} $EQUALLY"
    for (i: Int in 0..(program.size - 1))
        if (program[i] == value)
            for (j: Int in (i + 1)..(program.size - 1)) {
                if (program[j] == END_OF_LINE) {
                    program_.addAll(Identifier(index))
                    return program_
                }
                else
                    randList.add(index)
            }
    return Identifier(program, randList)
}

//заполняет контейнер мат операторами
fun OperationType(args: MutableList<String>, operationIndex: Int): MutableList<String> {
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    for (i: Int in operationIndex..(args.size - 1))
        OPERATIONS_TYPE.add(args[i])
    return OPERATIONS_TYPE
}

//выбирает случайный мат опретаор из контейнера мат операторов
fun Operation(operation: MutableList<String>, randList: MutableList<Int>): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(" ${operation[randListIntPop(randList)]} ")
    return program_
}

//добавляет в контейнер заданную библиотеку
fun Include(libr_numb: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add("${INCLUDE[0]}")
    program_.add("${LIBRARY[libr_numb]}")
    program_.add("${INCLUDE[1]}")
    program_.add("$CARRIAGE_RETURN")
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

/*fun Arguments(program: MutableList<String>) : MutableList<String> {
val program_: MutableList<String> = mutableListOf()
if (randBool()) {
    program_.addAll(Arguments(program_))
    program_.add("$COMMA ")
}
program_.add("${TYPE[ rand(1, TYPE.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
return program_
}*/

//обрамляет выражение круглыми скобками
fun Brackets(program: MutableList<String>, operation: MutableList<String>, randList1: MutableList<Int>, randList2: MutableList<Int>,
             randList3: MutableList<Int>, randListBool: MutableList<Int>, argNum: Int, count: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(BRACKETS[0])
    program_.addAll(Expression(program, operation, randList1, randList2, randList3, randListBool, argNum, count))
    program_.add(BRACKETS[1])
    return program_
}

//добавляет либо скобки, либо переменную или число
fun Atom(program: MutableList<String>, operation: MutableList<String>, randList1: MutableList<Int>, randList2: MutableList<Int>,
         randList3: MutableList<Int>, randListBool: MutableList<Int>, argNum: Int, count: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (count < argNum)
        if (randListBoolPop(randListBool) && count + 2 < argNum)
            program_.addAll(Brackets(program, operation, randList1, randList2, randList3, randListBool, argNum, count))
        else
            if (randListBoolPop(randListBool))
                program_.addAll(Identifier(program, randList1))
            else
                program_.add("${randListIntPop(randList3)}")
    return program_
}

//добавляет мат оператор и аргумент в строку-выражение
fun ExpressionAddition(program: MutableList<String>, operation: MutableList<String>, randList1: MutableList<Int>,
                       randList2: MutableList<Int>, randList3: MutableList<Int>, randListBool: MutableList<Int>,
                       argNum: Int, count: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (count < argNum) {
        program_.addAll(Operation(operation, randList2))
        program.addAll(program_)
        if (checkOperator(program)) { //проверяет наличие операторов сдвига (<<, >>) в контейнере
            program_.add("${randListIntPop(randList3)}")
            return program_
        }
        program_.addAll(Atom(program, operation, randList1, randList2, randList3, randListBool, argNum, count))
        val count_ = count + 1
        if (randListBoolPop(randListBool))
            program_.addAll(ExpressionAddition(program, operation, randList1, randList2, randList3, randListBool, argNum, count_ + 1))
    }
    return program_
}

//добавляет аргумент в строку-выражение
fun Expression(program: MutableList<String>, operation: MutableList<String>, randList1: MutableList<Int>, randList2: MutableList<Int>,
               randList3: MutableList<Int>, randListBool: MutableList<Int>, argNum: Int, count: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (count == 0 || count < argNum) {
        if (checkOperator(program)) //проверяет наличие операторов сдвига (<<, >>) в контейнере
            program_.add("${randListIntPop(randList3)}")
        else {
            if (randListBoolPop(randListBool))
                program_.addAll(Identifier(program, randList1))
            else
                program_.add("${randListIntPop(randList3)}")
            program.addAll(program_)
            program_.addAll(ExpressionAddition(program, operation, randList1, randList2, randList3, randListBool, argNum, count + 1))
        }
    }
    return program_
}

//добавляет строку-выражение в контейнер
fun State(prog: MutableList<String>, task: Int, operation: MutableList<String>, randSeed: Int,
          List1: MutableList<Int>, List2: MutableList<Int>, List3: MutableList<Int>, ListBool: MutableList<Int>,
          stateNum: Int, argNum: Int, printfNum: Int, redefinitonVar: Boolean, id: Int, count: Int, count_: Int): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    if (count < stateNum) {
        val index = randListIntPop(List1)
        prog_.add("${IDENTIFIER[index]} $EQUALLY ")
        prog.addAll(prog_)
        if (checkIdentifier(prog, index) && !redefinitonVar) { //выключено переопределение = 0
            prog_.addAll(Identifier(index))
            prog.addAll(prog_)
            prog_.addAll(ExpressionAddition(prog, operation, List1, List2, List3, ListBool, argNum, 1))
        } else
            prog_.addAll(Expression(prog, operation, List1, List2, List3, ListBool, argNum, 0))
        prog_.add(END_OF_LINE)

        prog.addAll(prog_)
        if (count_ == 0) {
//            prog_.addAll(Printf(prog, List1, task, randSeed, id))
            prog.addAll(prog_)
            prog_.addAll(State(prog, task, operation, randSeed, List1, List2, List3, ListBool, stateNum, argNum, printfNum, redefinitonVar, id, count + 1, stateNum / printfNum))
        }
        else
            prog_.addAll(State(prog, task, operation, randSeed, List1, List2, List3, ListBool, stateNum, argNum, printfNum, redefinitonVar, id, count + 1, count_ - 1))
    }
    return prog_
}

fun firstTask(parameters: ProgramParameters, id: Int): MutableList<String> {
    val from = 0
    val to = parameters.getVariablesNum()
    val size = 70

    val List1 = randList(Random(parameters.getRandSeed().toLong()), from, to, size)
    val List2 = randList(Random(parameters.getRandSeed().toLong()), from, parameters.getOperationType().size, size) //индексы операторов
    val List3 = randList(Random(parameters.getRandSeed().toLong()), from + 1, MAX_VALUE, size)
    val listBool = randList(Random(parameters.getRandSeed().toLong()), 0, 2, size)
//    val listFloat: MutableList<Float> = mutableListOf()
//    val variableList: MutableList<String> = mutableListOf()
//    val printfList: MutableList<Int> = mutableListOf()

    val prog_: MutableList<String> = mutableListOf()
//    prog_.addAll(Init(parameters, from, to, listBool, List2, listFloat, program))

    val prog: MutableList<String> = mutableListOf()
    prog.addAll(prog_)

    if (parameters.getPrintfNum() != 0 && parameters.getPrintfNum() != 1) {
        prog_.addAll(State(prog, parameters.getTask_(), parameters.getOperationType(), parameters.getRandSeed(), List1, List2, List3, listBool, parameters.getStatementsNum(), parameters.getArgumentsNum(), parameters.getPrintfNum(), parameters.getRedefinitionVar(), id, 0, parameters.getStatementsNum() / parameters.getPrintfNum()))
//        prog_.addAll(Printf(prog, List1, parameters.getTask_(), parameters.getRandSeed(), id))
    }
    else {
        prog_.addAll(State(prog, parameters.getTask_(), parameters.getOperationType(), parameters.getRandSeed(), List1, List2, List3, listBool, parameters.getStatementsNum(), parameters.getArgumentsNum(), parameters.getPrintfNum(), parameters.getRedefinitionVar(), id, 0, -1))
//        if (parameters.getPrintfNum() == 1)
//            prog_.addAll(Printf(prog, List1, parameters.getTask_(), parameters.getRandSeed(), id))
    }

    return prog_
}

/*________________________________________________________*/

fun itemSelection(args: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()

    val parameters = ProgramParameters(args)
    var program = Program()

    val size = 150
    val randList = randList(Random(parameters.getRandSeed().toLong()), 0, parameters.getVariablesNum(), size)

    when (parameters.getTask_()) {
        1 -> { //arithmetic operating block
            program_.addAll(firstTask(parameters, 1))
        }
        else -> {
            val numList = randList(Random(parameters.getRandSeed().toLong()), 1, MAX_VALUE, parameters.getVariablesNum() * parameters.getArgumentsNum())
            val listBool = randList(Random(parameters.getRandSeed().toLong()), 0, 2, size)
            val listInt = randList(Random(parameters.getRandSeed().toLong()), 0, parameters.getVariablesNum(), parameters.getVariablesNum() * 10)
            val listFloat = randListFloat(Random(parameters.getRandSeed().toLong()), 0, 9, parameters.getVariablesNum() * 5)
            val operationIdList = randList(Random(parameters.getRandSeed().toLong()), 0, OPERATIONS.size, 70) //индексы операторов
            val listCondition = randList(Random(parameters.getRandSeed().toLong()), 0, 3, size/*parameters.getPrintfNum() + parameters.getVariablesNum()*/)

            //program.getProgram().addAll(Init(parameters, 0, 0, listBool, operationIdList, listFloat, program))

            when (parameters.getTask_()) {
                2 -> { //if block
                    do {
                        program_.addAll(If(program, parameters, listBool, operationIdList, parameters.getNestingLevel(), listFloat, listCondition, numList, listInt, program_, randList))
                    } while (parameters.getPrintfNum() - program.getCounterIf() > 0)
                }
                3 -> {
                    //switch block
                }
                4 -> { //while block
                    do {
                        program_.addAll(While(program, parameters, listBool, operationIdList, parameters.getNestingLevel(), listFloat, listCondition, numList, listInt, program_, randList))
                    } while (parameters.getPrintfNum() - program.getCounterWhile() > 0)
                }
                5 -> { //do while block
                    do {
                        program_.addAll(DoWhile(program, parameters, listBool, operationIdList, parameters.getNestingLevel(), listFloat, listCondition, numList, listInt, program_, randList))
                    } while (parameters.getPrintfNum() - program.getCounterDoWhile() > 0)
                }
                6 -> { //for block
                    do {
                        program_.addAll(ForLoop(program, parameters, listBool, operationIdList, parameters.getNestingLevel(), listFloat, listCondition, numList, listInt, program_, randList))
                    } while (parameters.getPrintfNum() - program.getCounterFor() > 0)
                }
            }
        }
    }
    return program_
}

fun InitModOperation(argNum: Int, operationList: MutableList<String> , operationIdList: MutableList<Int>, listFloat: MutableList<Float>,
                     listBool: MutableList<Int>, numList: MutableList<Int>, visibleVar: Program): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()

    if (argNum > 2) {
        var a : String
        if (randListBoolPop(listBool) && visibleVar.getVariableInt().size != 0)
            a = visibleVar.getVariableIntIndex(rand(0, visibleVar.getVariableInt().size, visibleVar.getParameters_().getRandSeed()))
        else
            a = "${randListIntPop(numList)}"
        var b = randListIntPop(numList)
        while (b == 0)
            b = randListIntPop(numList)
        prog_.add("$a$MOD$b")

        if (argNum - 2 > 1 && randListBoolPop(listBool))
            prog_.addAll(InitAddition(argNum - 2, operationList, operationIdList, listFloat, listBool, numList, visibleVar))
        else {
            if (argNum - 2 > 2 && randListBoolPop(listBool)) {
                var i = randListIntPop(operationIdList)
                while (ARITHMETIC_OPERATIONS[i] == MOD || ARITHMETIC_OPERATIONS[i] == DIV || ARITHMETIC_OPERATIONS[i] == MULTIPLICATION)
                    i = randListIntPop(operationIdList)
                prog_.add("${ARITHMETIC_OPERATIONS[i]}")
                prog_.addAll(InitModOperation(argNum - 2, operationList, operationIdList, listFloat, listBool, numList, visibleVar))
            }
        }
    }
    return prog_
}

fun InitAddition(argNum: Int, operationList: MutableList<String> , operationIdList: MutableList<Int>, listFloat: MutableList<Float>,
                 listBool: MutableList<Int>, numList: MutableList<Int>, visibleVar: Program): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()

    if (argNum > 1) {
        var i = randListIntPop(operationIdList)
        while (ARITHMETIC_OPERATIONS[i] == MOD)
            i = randListIntPop(operationIdList)
        prog_.add("${ARITHMETIC_OPERATIONS[i]}")
        if (randListBoolPop(listBool)) {
            if (randListBoolPop(listBool) && visibleVar.getVariableFloat().size != 0)
                prog_.add("${visibleVar.getVariableFloatIndex(rand(0, visibleVar.getVariableFloat().size, visibleVar.getParameters_().getRandSeed()))}")
            else
                prog_.add("${randListFloatPop(listFloat)}")
        }
        else {
            if (randListBoolPop(listBool) && visibleVar.getVariableInt().size != 0)
                prog_.add("${visibleVar.getVariableIntIndex(rand(0, visibleVar.getVariableInt().size, visibleVar.getParameters_().getRandSeed()))}")
            else
                prog_.add("${randListIntPop(numList)}")
        }

        if (argNum - 1 > 1 && randListBoolPop(listBool))
            prog_.addAll(InitAddition(argNum - 1, operationList, operationIdList, listFloat, listBool, numList, visibleVar))
        else {
            if (argNum - 1 > 2 && randListBoolPop(listBool)) {
                i = randListIntPop(operationIdList)
                while (ARITHMETIC_OPERATIONS[i] == MOD || ARITHMETIC_OPERATIONS[i] == DIV || ARITHMETIC_OPERATIONS[i] == MULTIPLICATION)
                    i = randListIntPop(operationIdList)
                prog_.add("${ARITHMETIC_OPERATIONS[i]}")
                prog_.addAll(InitModOperation(argNum - 1, operationList, operationIdList, listFloat, listBool, numList, visibleVar))
            }
        }
    }
    return prog_
}

//инициализация переменных
fun Init(parameters: ProgramParameters, from: Int, to: Int, listBool: MutableList<Int>,
         operationIdList: MutableList<Int>, listFloat: MutableList<Float>, program: Program,
         listCondition: MutableList<Int>, numList: MutableList<Int>): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    when (parameters.getTask_()) {
        1 -> { //*
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
        }
        else -> {
            //инициализация переменных
            var j = parameters.getVariablesNum() / parameters.getPrintfNum()
            if (j == 0)
                j = 1
            if (parameters.getVariablesNum() % parameters.getPrintfNum() != 0 && parameters.getVariablesNum() - program.getCounterVariables() != j && randListBoolPop(listBool))
                j += 1

            if (parameters.getPrintfNum() - program.getCounterPrintf() == 0)
                j = parameters.getVariablesNum() - program.getCounterVariables()

            for (i in program.getCounterVariables()..(program.getCounterVariables() + j - 1)) {
                var index = randListIntPop(listCondition)
                val visibleVar = findVisibleVar(program_)
                when(index) {
                    0 -> {
                        program_.add("$BOOL")
                        program_.add(" ")
                        program_.add("${IDENTIFIER[i]}")
                        program_.add("$EQUALLY")
                        if (randListBoolPop(listBool) && visibleVar.getVariableBool().size != 0) {
                            if (randListBoolPop(listBool))
                                program_.add("$LOGICAL_NEGATION")
                            program_.add("${visibleVar.getVariableBoolIndex(rand(0, visibleVar.getVariableBool().size, parameters.getRandSeed()))}")
                        }
                        else
                            program_.add("${randListBoolPop(listBool)}")
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

                        if (parameters.getArgumentsNum() > 2 && randListBoolPop(listBool))
                            program_.addAll(InitModOperation(parameters.getArgumentsNum(), OPERATIONS, operationIdList, listFloat, listBool, numList, visibleVar))
                        else {
                            if (randListBoolPop(listBool)) {
                                if (randListBoolPop(listBool) && visibleVar.getVariableFloat().size != 0)
                                    program_.add("${visibleVar.getVariableFloatIndex(rand(0, visibleVar.getVariableFloat().size, visibleVar.getParameters_().getRandSeed()))}")
                                else
                                    program_.add("${randListFloatPop(listFloat)}")
                            }
                            else {
                                if (randListBoolPop(listBool) && visibleVar.getVariableInt().size != 0)
                                    program_.add("${visibleVar.getVariableIntIndex(rand(0, visibleVar.getVariableInt().size, visibleVar.getParameters_().getRandSeed()))}")
                                else
                                    program_.add("${randListIntPop(numList)}")
                            }
                            if (parameters.getArgumentsNum() > 1 && randListBoolPop(listBool))
                                program_.addAll(InitAddition(parameters.getArgumentsNum(), OPERATIONS, operationIdList, listFloat, listBool, numList, visibleVar))
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

//печатает инструкцию вывода
/*fun printfStamp(program: MutableList<String>, randList: MutableList<Int>): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    while (!checkIdentifier(program, randList.first())) {
        randList.add(randList.first())
        randListIntPop(randList)
    }
    program_.add("$TAB$PRINTF${BRACKETS[0]}$QUOTES%i${CARRIAGE_RETURN_}$QUOTES$COMMA ${IDENTIFIER[randListIntPop(randList)]}${BRACKETS[1]}$END_OF_LINE")
    return program_
}*/

fun Printf(parameters: ProgramParameters, program: Program, task: Int, program_: MutableList<String>, randList: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$PRINTF$ROUND_BRACKET$QUOTES$SQUARE_BRACKET")
    when (task) {
        1 -> {
            while (!checkIdentifier(program_, randList.first())) {
                randList.add(randList.first())
                randListIntPop(randList)
            }
            prog_.add("%i${BRACKETS[5]}$PRINT_CARRIAGE_RETURN$QUOTES$COMMA ${IDENTIFIER[randListIntPop(randList)]}")
        }
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

fun returnIntVariable(listInt: MutableList<Int>, program: Program): MutableList<String> {
    val variable: MutableList<String> = mutableListOf()
    var index = randListIntPop(listInt)
    while (!(index > -1 && index < program.getVariableInt().size))
        index = randListIntPop(listInt)
    variable.add("${program.getVariableIntIndex(index)}")
    return variable
}

fun returnIdentif(ListBool: MutableList<Int>, listInt: MutableList<Int>, program: Program): MutableList<String> {
    val variable: MutableList<String> = mutableListOf()
    if (program.getVariableInt().size > 0 && program.getVariableFloat().size > 0) {
        if (randListBoolPop(ListBool)) { // int variable
            var index = randListIntPop(listInt)
            while (!(index > -1 && index < program.getVariableInt().size))
                index = randListIntPop(listInt)
            variable.add("${program.getVariableIntIndex(index)}")
            return variable
        }
        else { // float variable
            var index = randListIntPop(listInt)
            while (!(index > -1 && index < program.getVariableFloat().size))
                index = randListIntPop(listInt)
            variable.add("${program.getVariableFloatIndex(index)}")
            return variable
        }
    }
    else {
        if (program.getVariableInt().size == 0) {
            var index = randListIntPop(listInt)
            while (!(index > -1 && index < program.getVariableFloat().size))
                index = randListIntPop(listInt)
            variable.add("${program.getVariableFloatIndex(index)}")
            return variable
        }
        if (program.getVariableFloat().size == 0) {
            var index = randListIntPop(listInt)
            while (!(index > -1 && index < program.getVariableInt().size))
                index = randListIntPop(listInt)
            variable.add("${program.getVariableIntIndex(index)}")
            return variable
        }
    }

    if (variable.isEmpty())
        returnIdentif(ListBool, listInt, program)

    return variable
}

fun arithmeticCondition(prog: Program, listInt: MutableList<Int>, ListBool: MutableList<Int>): MutableList<String> {
    val condition: MutableList<String> = mutableListOf()
    var i = randListIntPop(listInt)
    while (!(i > -1 && i < ARITHMETIC_OPERATIONS.size))
        i = randListIntPop(listInt)

    if (ARITHMETIC_OPERATIONS[i] == MOD) {
        if (prog.getVariableInt().size > 1) {
            var a = returnIntVariable(listInt, prog).joinToString("")
            val b = returnIntVariable(listInt, prog).joinToString("")
            while (a == b)
                a = returnIntVariable(listInt, prog).joinToString("")
            condition.add("$a")
            condition.add("${ARITHMETIC_OPERATIONS[i]}")
            condition.add("$b")
        } else condition.addAll(arithmeticCondition(prog, listInt, ListBool))
    }
    if (ARITHMETIC_OPERATIONS[i] != MOD) {
        if (prog.getVariableInt().size == 0 && prog.getVariableFloat().size == 2) {
            var a = prog.getVariableFloatIndex(0)
            var b = prog.getVariableFloatIndex(1)
            condition.add("$a")
            condition.add("${ARITHMETIC_OPERATIONS[i]}")
            condition.add("$b")
            return condition
        }
        if (prog.getVariableInt().size == 2 && prog.getVariableFloat().size == 0) {
            var a = prog.getVariableIntIndex(0)
            var b = prog.getVariableIntIndex(1)
            condition.add("$a")
            condition.add("${ARITHMETIC_OPERATIONS[i]}")
            condition.add("$b")
            return condition
        }
        var a = returnIdentif(ListBool, listInt, prog).joinToString("")
        val b = returnIdentif(ListBool, listInt, prog).joinToString("")
        while (a == b)
            a = returnIdentif(ListBool, listInt, prog).joinToString("")

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

/*
//проверяет наличие выбранной переменной в контейнере
fun checkIdentifier(program: MutableList<String>, index: Int): Boolean {
    val value = "${IDENTIFIER[index]} $EQUALLY"
    for (i: Int in 0..(program.size - 1))
/        if (program[i] == value)
            for (j: Int in (i + 1)..(program.size - 1))
                if (program[j] == END_OF_LINE)
                    return true
    return false
}
*/

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
    print("find visible variable: ")
    for (i: Int in 0..program.getVariableBool().size - 1)
        print(program.getVariableBoolIndex(i) + ", ")
    for (i: Int in 0..program.getVariableFloat().size - 1)
        print(program.getVariableFloatIndex(i) + ", ")
    for (i: Int in 0..program.getVariableInt().size - 1)
        print(program.getVariableIntIndex(i) + ", ")
    println(".")

    return program
}

fun logicalExpr(a: String, b: String, ListBool: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()

    prog_.add("$a")
    if (randListBoolPop(ListBool))
        prog_.add("$LOGICAL_AND")
    else
        prog_.add("$LOGICAL_OR")
    prog_.add("$b")

    return prog_
}

//возвращает строку с переменными для условия, напр: a + f
fun Condition(program_: MutableList<String>, /*prog: Program,*/ ListBool: MutableList<Int>, listInt: MutableList<Int>, listCondition: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    val prog = findVisibleVar(program_)

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

    if (prog.getVariableBool().size == 0 && prog.getVariableFloat().size == 0 && prog.getVariableInt().size == 1) {
        prog_.add("${prog.getVariableIntIndex(0)}")
        return prog_
    }
    if (prog.getVariableBool().size == 0 && prog.getVariableFloat().size == 1 && prog.getVariableInt().size == 0) {
        prog_.add("${prog.getVariableFloatIndex(0)}")
        return prog_
    }
    var index = randListIntPop(listCondition)
    if ((index == 1 || index == 2))
        if ((prog.getVariableFloat().size == 0 && prog.getVariableInt().size == 0)
                || (prog.getVariableFloat().size == 0 && prog.getVariableInt().size == 1)
                || (prog.getVariableFloat().size == 1 && prog.getVariableInt().size == 0)) {
            index = 0
        }
    while (prog.getVariableBool().size == 0 && index == 0)
        index = randListIntPop(listCondition)

    when (index) {
        0 -> { //bool variable
            if (prog.getVariableBool().size > 0) {
                if (prog.getVariableBool().size == 1) {
                    var i = randListIntPop(listInt)
                    while ( !(i > -1 && i < prog.getVariableBool().size) )
                        i = randListIntPop(listInt)

                    if (!randListBoolPop(ListBool))
                        prog_.add("$LOGICAL_NEGATION")
                    prog_.add("${prog.getVariableBoolIndex(i)}")
                    return prog_
                }
                if (prog.getVariableBool().size > 1) {
                    var i = randListIntPop(listInt)
                    var i_ = randListIntPop(listInt)
                    while (!(i > -1 && i < prog.getVariableBool().size))
                        i = randListIntPop(listInt)

                    while (!(i_ > -1 && i_ < prog.getVariableBool().size))
                        i_ = randListIntPop(listInt)

                    index = randListIntPop(listCondition)
                    when (index) {
                        0 -> {
                            prog_.addAll(logicalExpr(prog.getVariableBoolIndex(i), prog.getVariableBoolIndex(i_), ListBool))
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
                            prog_.addAll(logicalExpr("$LOGICAL_NEGATION${prog.getVariableBoolIndex(i)}", prog.getVariableBoolIndex(i_), ListBool))
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
                            prog_.addAll(logicalExpr(prog.getVariableBoolIndex(i), "$LOGICAL_NEGATION${prog.getVariableBoolIndex(i_)}", ListBool))
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
                    return prog_
                }
            }
        }
        1 -> { //int or float variable - comparison
            var i = randListIntPop(listInt)
            while (!(i > -1 && i < RELATIONAL_OPERATIONS.size))
                i = randListIntPop(listInt)

            var a = returnIdentif(ListBool, listInt, prog).joinToString("")
            var b = returnIdentif(ListBool, listInt, prog).joinToString("")

            while (a == b)
                a = returnIdentif(ListBool, listInt, prog).joinToString("")

            prog_.add("$a")
            prog_.add("${RELATIONAL_OPERATIONS[i]}")
            prog_.add("$b")
            return prog_
        }
        2 -> { //int or float variable - arithmetic operation
            prog_.addAll(arithmeticCondition(prog, listInt, ListBool))
            return prog_
        }
    }
    return prog_
}

fun If(program: Program, param: ProgramParameters, listBool: MutableList<Int>, operationIdList: MutableList<Int>,
          nestingLevel: Int, listFloat: MutableList<Float>, listCondition: MutableList<Int>, numList: MutableList<Int>,
          listInt: MutableList<Int>, program_: MutableList<String>, randList: MutableList<Int>) : MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    if (param.getVariablesNum() - program.getCounterVariables() > 0 )
        prog_.addAll(Init(param, 0, 0, listBool, operationIdList, listFloat, program, listCondition, numList))

    prog_.add("$IF")
    prog_.add("$ROUND_BRACKET")

    val p: MutableList<String> = mutableListOf()
    p.addAll(program_)
    p.addAll(prog_)

    val p_: MutableList<String> = mutableListOf() //в.п.для хранение текущего условия
    p_.addAll(Condition(p, listBool, listInt, listCondition))

    prog_.addAll(p_)
    prog_.add("$ROUND_BRACKET_")
    prog_.add("$BRACE")
    prog_.add("$CARRIAGE_RETURN")
    program.incrementCounterIf()
    prog_.addAll(Printf(param, program, param.getTask_(), program_, randList))

    if (randListBoolPop(listBool) && nestingLevel > 1 && param.getPrintfNum() > program.getCounterIf())
        prog_.addAll(If(program, param, listBool, operationIdList, nestingLevel - 1, listFloat, listCondition, numList, listInt, p, randList))

    prog_.add("$BRACE_")
    prog_.add("$CARRIAGE_RETURN")

    if (randListBoolPop(listBool))
        prog_.addAll(Else(program, param, listBool, operationIdList, nestingLevel - 1, listFloat, listCondition, numList, listInt, p, randList))

    return prog_
}

fun Else(program: Program, param: ProgramParameters, listBool: MutableList<Int>, operationIdList: MutableList<Int>,
          nestingLevel: Int, listFloat: MutableList<Float>, listCondition: MutableList<Int>, numList: MutableList<Int>,
          listInt: MutableList<Int>, program_: MutableList<String>, randList: MutableList<Int>) : MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$ELSE")
    prog_.add("$BRACE")

    val p: MutableList<String> = mutableListOf()
    p.addAll(program_)
    p.addAll(prog_)

    prog_.add("$CARRIAGE_RETURN")
    program.incrementCounterElse()
    prog_.addAll(Printf(param, program, param.getTask_() - 2, program_, randList))

    if (randListBoolPop(listBool) && nestingLevel > 1 && param.getPrintfNum() > program.getCounterIf())
        prog_.addAll(If(program, param, listBool, operationIdList, nestingLevel - 1, listFloat, listCondition, numList, listInt, p, randList))

    prog_.add("$BRACE_")
    prog_.add("$CARRIAGE_RETURN")
    return prog_
}

fun varChange(program: Program, condition: MutableList<String>, listBool: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    for (i: Int in 0..condition.size - 1) {
        for (j: Int in 0..program.getVariableBool().size - 1) { //bool variable
            if (condition[i] == program.getVariableBoolIndex(j)) {
                //if (randListBoolPop(listBool)) {
                    prog_.add("${condition[i]}")
                    prog_.add("$EQUALLY")
                    prog_.add("$LOGICAL_NEGATION")
                    prog_.add("${condition[i]}")
                    prog_.add("$END_OF_LINE")
                /*}
                else {

                }*/

                return prog_
            }
        }
        for (j: Int in 0..program.getVariableInt().size - 1) { //int variable
            if (condition[i] == program.getVariableIntIndex(j)) {
                prog_.add("${condition[i]}")
                if (randListBoolPop(listBool))
                    prog_.add("${SPECIAL_OPERATIONS[0]}")
                else
                    prog_.add("${SPECIAL_OPERATIONS[1]}")
                prog_.add("$END_OF_LINE")
                return prog_
            }
        }
        for (j: Int in 0..program.getVariableFloat().size - 1) { //float variable
            if (condition[i] == program.getVariableFloatIndex(j)) {
                prog_.add("${condition[i]}")
                if (randListBoolPop(listBool))
                    prog_.add("${SPECIAL_OPERATIONS[0]}")
                else
                    prog_.add("${SPECIAL_OPERATIONS[1]}")
                prog_.add("$END_OF_LINE")
                return prog_
            }
        }
    }
    return prog_
}

fun While(program: Program, param: ProgramParameters, listBool: MutableList<Int>, operationIdList: MutableList<Int>,
          nestingLevel: Int, listFloat: MutableList<Float>, listCondition: MutableList<Int>, numList: MutableList<Int>,
          listInt: MutableList<Int>, program_: MutableList<String>, randList: MutableList<Int>) : MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    if (param.getVariablesNum() - program.getCounterVariables() > 0 )
        prog_.addAll(Init(param, 0, 0, listBool, operationIdList, listFloat, program, listCondition, numList))

    prog_.add("$WHILE")
    prog_.add("$ROUND_BRACKET")

    val p: MutableList<String> = mutableListOf()
    p.addAll(program_)
    p.addAll(prog_)

    val p_: MutableList<String> = mutableListOf() //в.п.для хранение текущего условия
    p_.addAll(Condition(p, listBool, listInt, listCondition))

    prog_.addAll(p_)
    prog_.add("$ROUND_BRACKET_")
    prog_.add("$BRACE")
    prog_.add("$CARRIAGE_RETURN")
    prog_.addAll(varChange(program, p_, listBool))
    program.incrementCounterWhile()
    prog_.addAll(Printf(param, program, param.getTask_(), program_, randList))

    if (randListBoolPop(listBool))
        prog_.addAll(ExitPoint(listBool))

    if (randListBoolPop(listBool) && nestingLevel > 1 && param.getPrintfNum() > program.getCounterWhile())
        prog_.addAll(While(program, param, listBool, operationIdList, nestingLevel - 1, listFloat, listCondition, numList, listInt, p, randList))

    prog_.add("$BRACE_")
    prog_.add("$CARRIAGE_RETURN")
    return prog_
}

fun DoWhile(program: Program, param: ProgramParameters, listBool: MutableList<Int>, operationIdList: MutableList<Int>,  nestingLevel: Int,
            listFloat: MutableList<Float>, listCondition: MutableList<Int>, numList: MutableList<Int>, listInt: MutableList<Int>,
            program_: MutableList<String>, randList: MutableList<Int>) : MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    if (param.getVariablesNum() - program.getCounterVariables() > 0 ) {
        prog_.addAll(Init(param, 0, 0, listBool, operationIdList, listFloat, program, listCondition, numList))
    }
    val p: MutableList<String> = mutableListOf()
    p.addAll(program_)
    p.addAll(prog_)

    prog_.add("$DO")
    prog_.add("$BRACE")
    prog_.add("$CARRIAGE_RETURN")
    program.incrementCounterDoWhile()
    val p_: MutableList<String> = mutableListOf() //в.п.для хранение текущего условия
    p_.addAll(Condition(p, listBool, listInt, listCondition))
    prog_.addAll(varChange(program, p_, listBool))
    prog_.addAll(Printf(param, program, param.getTask_(), program_, randList))

    if (randListBoolPop(listBool))
        prog_.addAll(ExitPoint(listBool))

    if (randListBoolPop(listBool) && nestingLevel > 1 && param.getPrintfNum() > program.getCounterDoWhile())
        prog_.addAll(DoWhile(program, param, listBool, operationIdList, nestingLevel - 1, listFloat, listCondition, numList, listInt, p, randList))

    prog_.add("$BRACE_")
    prog_.add("$WHILE")
    prog_.add("$ROUND_BRACKET")
    prog_.addAll(p_)
    prog_.add("$ROUND_BRACKET_")
    prog_.add("$END_OF_LINE")

    return prog_
}

fun ForLoop(program: Program, param: ProgramParameters, listBool: MutableList<Int>, operationIdList: MutableList<Int>,
          nestingLevel: Int, listFloat: MutableList<Float>, listCondition: MutableList<Int>, numList: MutableList<Int>,
          listInt: MutableList<Int>, program_: MutableList<String>, randList: MutableList<Int>) : MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    if (param.getVariablesNum() - program.getCounterVariables() > 0 )
        prog_.addAll(Init(param, 0, 0, listBool, operationIdList, listFloat, program, listCondition, numList))

    val p: MutableList<String> = mutableListOf()
    p.addAll(program_)
    p.addAll(prog_)

    prog_.add("$FOR")
    prog_.add("$ROUND_BRACKET")
    prog_.add("$SIZE_T")
    prog_.add(" ")
    prog_.add("i")
    prog_.add("$EQUALLY")
    prog_.add("0")
    prog_.add("$SEMICOLON")
    prog_.add("i")
    prog_.add("<")
    prog_.add("${param.getSize()}")
    prog_.add("$SEMICOLON")
    prog_.add("i")
    prog_.add("++")

    prog_.add("$ROUND_BRACKET_")
    prog_.add("$BRACE")
    prog_.add("$CARRIAGE_RETURN")
    program.incrementCounterFor()
    prog_.addAll(Printf(param, program, param.getTask_(), program_, randList))

    if (randListBoolPop(listBool))
        prog_.addAll(ExitPoint(listBool))

    if (randListBoolPop(listBool) && nestingLevel > 1 && param.getPrintfNum() > program.getCounterFor())
        prog_.addAll(ForLoop(program, param, listBool, operationIdList, nestingLevel - 1, listFloat, listCondition, numList, listInt, p, randList))

    prog_.add("$BRACE_")
    prog_.add("$CARRIAGE_RETURN")
    return prog_
}

fun ExitPoint(ListBool: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    if (randListBoolPop(ListBool)) {
        prog_.add("$CONTINUE")
        prog_.add("$END_OF_LINE")
        return prog_
    }
    if (randListBoolPop(ListBool)) {
        prog_.add("$BREAK")
        prog_.add("$END_OF_LINE")
        return prog_
    }
    if (randListBoolPop(ListBool)) {
        prog_.addAll(Return(0))
        return prog_
    }
    return prog_
}

//генерирует число в диапазоне [from; to] с зерном randSeed
fun rand(from: Int, to: Int, randSeed: Int): Int {
    val random = Random(randSeed.toLong())
    if (from == to)
        return from
    return random.nextInt(to - from) + from
}

//генерирует последовательность int в диапазоне [from; to] с зерном randSeed
fun randList(random: Random, from: Int, to: Int, size: Int): MutableList<Int> = MutableList(size) {
    random.nextInt(to - from) + from
}

//генерирует последовательность float в диапазоне [from; to] с зерном randSeed
fun randListFloat(random: Random, from: Int, to: Int, size: Int): MutableList<Float> = MutableList(size) {
    (random.nextInt(to - from) + from + (random.nextInt(to - from) + from) * 0.1).toFloat()
}

//возвращает первое число из списка int и удаляет его Int
fun randListIntPop(randList: MutableList<Int>) : Int {
    if (!randList.isEmpty()) {
        val index = randList.first()
        randList.remove(index)
        return index
    }
    return 0
}

//возвращает первое число из списка float и удаляет его
fun randListFloatPop(randList: MutableList<Float>) : Float {
    if (!randList.isEmpty()) {
        val value = randList.first()
        randList.remove(value)
        return value
    }
    return 0F
}

//возвращает первое число из списка и удаляет его Boolean
fun randListBoolPop(randListBool: MutableList<Int>) : Boolean {
    if (!randListBool.isEmpty()) {
        val flag = randListBool.first()
        randListBool.remove(randListBool.first())
        if (flag == 1)
            return true
        return false
    }
    return true
}

fun Main(args: MutableList<String>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$INT")
    prog_.add(" ")
    prog_.add("$MAIN")
    prog_.add("$ROUND_BRACKET")
    prog_.add("$ROUND_BRACKET_")

    prog_.add("$BRACE")
    prog_.add("$CARRIAGE_RETURN")
    prog_.addAll(itemSelection(args))
    prog_.addAll(Return(0))
    prog_.add("$BRACE_")
    return prog_
}

fun programGenerate(args: MutableList<String>) : MutableList<String> {
    val program = Program()
    program.getProgram().addAll(Include(0))
    if (parseInt(args[0]) != 1)
        program.getProgram().addAll(Include(1))
    program.getProgram().add("$CARRIAGE_RETURN")
    program.getProgram().addAll(Main(args))
    return program.getProgram()
}

fun checkMask(args: MutableList<String>) : Boolean {
    if (args.size == 0)
        return false
    if (parseInt(args[0]) == 1 && args.size < 8)
        return false
    if (parseInt(args[0]) > 1 && parseInt(args[0]) < 7) {
        if (parseInt(args[0]) == 6 && args.size != 7)
            return false
        if (parseInt(args[0]) != 6 && args.size != 6)
            return false
    }
    return true
}

fun printFun(args: MutableList<String>): MutableList<String> {
    val program: Program = Program()
    if (checkMask(args)) {
        program.getProgram().addAll(programGenerate(args))
//    val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
//    var file = File("func_$time.c")

        var file = File("func.c")
        file.writeText(program.getProgram().joinToString(""))
//        file.writeText(program.getProgram().joinToString())
    }
    return program.getProgram()
}

fun main(args: Array<String>) {
    val args_: MutableList<String> = mutableListOf()
//    args_.addAll(args)
    args_.addAll(args[0].split(' '))
    printFun(args_)
}