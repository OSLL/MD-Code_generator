package com.example

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
val ARITHMETIC_OPERATIONS: List<String> = listOf("+", "-", "/", "%")
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
    program_.add("${INCLUDE[0]}${LIBRARY[libr_numb]}${INCLUDE[1]}$CARRIAGE_RETURN")
    return program_
}

fun Return(index: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add("$RETURN ${index}$END_OF_LINE")
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
            prog_.addAll(Printf(prog, List1, task, randSeed, id))
            prog.addAll(prog_)
            prog_.addAll(State(prog, task, operation, randSeed, List1, List2, List3, ListBool, stateNum, argNum, printfNum, redefinitonVar, id,count + 1, stateNum / printfNum))
        }
        else
            prog_.addAll(State(prog, task, operation, randSeed, List1, List2, List3, ListBool, stateNum, argNum, printfNum, redefinitonVar, id, count + 1, count_ - 1))
    }
    return prog_
}

fun firstTask(task: Int, operation: MutableList<String>, randSeed: Int, varNum: Int,
              stateNum: Int, argNum: Int, printfNum: Int, redefinitonVar: Boolean, id: Int): MutableList<String> {
    val from = 0
    val to = varNum
    val size = 70

    val List1 = randList(Random(randSeed.toLong()), from, to, size)
    val List2 = randList(Random(randSeed.toLong()), from, operation.size, size) //индексы операторов
    val List3 = randList(Random(randSeed.toLong()), from + 1, MAX_VALUE, size)
    val listBool = randList(Random(randSeed.toLong()), 0, 2, size)
    val listFloat: MutableList<Float> = mutableListOf()
    val variableList: MutableList<String> = mutableListOf()
    val printfList: MutableList<Int> = mutableListOf()

    val prog_: MutableList<String> = mutableListOf()
    prog_.addAll(Init(1, randSeed, varNum, printfNum, from, to, listBool, List2, listFloat, List3, variableList, printfList))

    val prog: MutableList<String> = mutableListOf()
    prog.addAll(prog_)

    if (printfNum != 0 && printfNum != 1) {
        prog_.addAll(State(prog, task, operation, randSeed, List1, List2, List3, listBool, stateNum, argNum, printfNum, redefinitonVar, id, 0, stateNum / printfNum))
        prog_.addAll(Printf(prog, List1, task, randSeed, id))
    }
    else {
        prog_.addAll(State(prog, task, operation, randSeed, List1, List2, List3, listBool, stateNum, argNum, printfNum, redefinitonVar, id,0, -1))
        if (printfNum == 1)
            prog_.addAll(Printf(prog, List1, task, randSeed, id))
    }

    return prog_
}

/*________________________________________________________*/

fun itemSelection(args: MutableList<String>) : MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    val program: MutableList<String> = mutableListOf()

    val task = parseInt(args[0])
    val randSeed = parseInt(args[1])
    val variablesNum = parseInt(args[2])

    val size = 70
    val randList = randList(Random(randSeed.toLong()), 0, variablesNum, size)

    when (task) {
        1 -> { //arithmetic operating block
            val statementsNum = parseInt(args[3])
            val argumentsNum = parseInt(args[4])
            val printfNum = parseInt(args[5])
            var redefinitonVar = false
            if (parseInt(args[6]) == 1)
                redefinitonVar = true
            val operationIndex = 7 //операции начинаются с args[7]

            val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
            OPERATIONS_TYPE.addAll(OperationType(args, operationIndex))

            prog_.addAll(firstTask(task, OPERATIONS_TYPE, randSeed, variablesNum, statementsNum, argumentsNum, printfNum, redefinitonVar, 1))
        }
        else -> {
            val printfNum = parseInt(args[3])
            var nestingLevel = parseInt(args[4])
            if (printfNum == 1)
                nestingLevel = 0
//            val listBool = randList(Random(randSeed.toLong()), 0, 2, (variablesNum + (nestingLevel + 4) * printfNum))
//            val listBool = randList(Random(randSeed.toLong()), 0, 2, (variablesNum + 12 + (2 * nestingLevel + 1) * printfNum))
            val listBool = randList(Random(randSeed.toLong()), 0, 2, size)
//            println(listBool)

            val listFloat = randListFloat(Random(randSeed.toLong()), 0, variablesNum * 5)
            val listInt = randList(Random(randSeed.toLong()), 1, MAX_VALUE, variablesNum * 5)

            val operationIdList = randList(Random(randSeed.toLong()), 0, OPERATIONS.size, 70) //индексы операторов
            val variableList: MutableList<String> = mutableListOf()
            val printfList: MutableList<Int> = mutableListOf()

            prog_.addAll(Init(2, randSeed, variablesNum, printfNum, 0, 0, listBool, operationIdList, listFloat, listInt, variableList, printfList))

            when (task) {
                2 -> { //if block
                    do {
                        prog_.addAll(If(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel, listBool, operationIdList, listFloat, listInt, variableList, printfList))
                    } while (printfNum - printfList.size > 0)
                }
                3 -> {
                    //switch block
                }
                4 -> { //while block
                    do {
                        prog_.addAll(While(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel, listBool, operationIdList, listFloat, listInt, variableList, printfList))
                    } while (printfNum - printfList.size > 0)
                }
                5 -> { //do while block
                    do {
                        prog_.addAll(DoWhile(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel, listBool, operationIdList, listFloat, listInt, variableList, printfList))
                    } while (printfNum - printfList.size > 0)
                }
                6 -> { //for block
                    do {
                        prog_.addAll(ForLoop(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel, listBool, 10, operationIdList, listFloat, listInt, variableList, printfList))
                    } while (printfNum - printfList.size > 0)
                }
            }
        }
    }
    return prog_
}

fun Main(args: MutableList<String>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$INT $MAIN${BRACKETS[0]}${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    prog_.addAll(itemSelection(args))
    prog_.addAll(Return(0))
    prog_.add(BRACKETS[3])
    return prog_
}

fun InitAddition(operationList: MutableList<String> , operationIdList: MutableList<Int>, listFloat: MutableList<Float>,
                 listInt: MutableList<Int>, listBool: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.addAll(Operation(operationList, operationIdList))
    prog_.add("${randListFloatPop(listFloat) + randListIntPop(listInt)}")
    if (randListBoolPop(listBool))
        prog_.addAll(InitAddition(operationList, operationIdList, listFloat, listInt, listBool))
    return prog_
}

//инициализация переменных
fun Init(index: Int, randSeed: Int, varNum: Int, printfNum: Int, from: Int, to: Int, listBool: MutableList<Int>,
         operationIdList: MutableList<Int>, listFloat: MutableList<Float>, listInt: MutableList<Int>,
         variableList: MutableList<String>, printfList: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    when (index) {
        1 -> { //*
            val initialized_args = rand(from, to - 1, randSeed)   //номера инициализированных переменных
            val uninitialized_args = varNum - 2          //номера неинициализированных переменных

            val randList4 = randList(Random(randSeed.toLong()), from, MAX_VALUE, initialized_args + 1)
            for (i in 0..initialized_args) {
                prog_.add("$UNSIGNED $INT ")
                prog_.add("${IDENTIFIER[i]} $EQUALLY")
                prog_.add(" ${randListIntPop(randList4)}")
                prog_.add(END_OF_LINE)
            }

            if (uninitialized_args > -1) {
                var i = initialized_args + 1
                prog_.add("$UNSIGNED $INT")
                for (j: Int in i..uninitialized_args) {
                    prog_.add(" ${IDENTIFIER[j]}$COMMA")
                    i++
                }
                prog_.add(" ${IDENTIFIER[i]}$END_OF_LINE")
            }
        }
        2 -> {
            //инициализация переменных
            if (varNum - variableList.size < 1)
                return prog_

            var j = varNum / (printfNum + 1)
            if (varNum % (printfNum + 1) != 0 && varNum - variableList.size != j && randListBoolPop(listBool))
                j += 1
            if (printfNum - printfList.size == 0)
                j = varNum - variableList.size
            for (i in variableList.size..variableList.size + j - 1) {
                if (randListBoolPop(listBool)) {
                    prog_.add("$BOOL ")
                    prog_.add("${IDENTIFIER[i]} $EQUALLY ${randListBoolPop(listBool)}")
                }
                else {
                    if (randListBoolPop(listBool))
                        prog_.add("$INT ")
                    else
                        prog_.add("$FLOAT ")
                    prog_.add("${IDENTIFIER[i]} $EQUALLY ${randListFloatPop(listFloat) + randListIntPop(listInt)}")
                    if (randListBoolPop(listBool))
                        prog_.addAll(InitAddition(OPERATIONS, operationIdList, listFloat, listInt, listBool))
                }
                prog_.add("$END_OF_LINE")
                variableList.add("${IDENTIFIER[i]}")
            }
        }
    }
    return prog_
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

fun Printf(program: MutableList<String>, randList: MutableList<Int>, index: Int, randSeed: Int, count: Int): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$PRINTF${BRACKETS[0]}$QUOTES${BRACKETS[4]}")
    when (index) {
        1 -> {
            while (!checkIdentifier(program, randList.first())) {
                randList.add(randList.first())
                randListIntPop(randList)
            }
            prog_.add("%i${BRACKETS[5]}${PRINT_CARRIAGE_RETURN}$QUOTES$COMMA ${IDENTIFIER[randListIntPop(randList)]}")
        }
        2 -> prog_.add("if - $count${BRACKETS[5]}$PRINT_CARRIAGE_RETURN$QUOTES")
        0 -> prog_.add("else - $count${BRACKETS[5]}$PRINT_CARRIAGE_RETURN$QUOTES")
        //3 -> prog_.add("")
        4 -> prog_.add("check while loop - $count] %d$PRINT_CARRIAGE_RETURN$QUOTES$COMMA ++${IDENTIFIER[rand(0, 2, randSeed)]}")
        5 -> prog_.add("check do while loop - $count] %d$PRINT_CARRIAGE_RETURN$QUOTES$COMMA ++${IDENTIFIER[rand(0, 2, randSeed)]}")
        6 -> prog_.add("check for loop - $count] %li$PRINT_CARRIAGE_RETURN$QUOTES$COMMA i")
    }
    prog_.add("${BRACKETS[1]}$END_OF_LINE")
    return prog_
}

fun If(program: MutableList<String>, randList: MutableList<Int>, task: Int, randSeed: Int, variablesNum: Int,
       printfNum: Int, nestingLevel: Int, ListBool: MutableList<Int>, operationIdList: MutableList<Int>,
       listFloat: MutableList<Float>, listInt: MutableList<Int>, variableList: MutableList<String>,
       printfList: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$IF ${BRACKETS[0]}${IDENTIFIER[rand(0, 2, randSeed)]}${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    printfList.add(1)
    prog_.addAll(Init(2, randSeed, variablesNum, printfNum, 0, 0, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.addAll(Printf(program, randList, task, randSeed, printfList.size))

    if (randListBoolPop(ListBool) && printfNum - printfList.size > 0 && nestingLevel > 0)
        prog_.addAll(If(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel - 1, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.add("${BRACKETS[3]}$CARRIAGE_RETURN")

    if (randListBoolPop(ListBool))
        prog_.addAll(Else(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    return prog_
}

fun Else(program: MutableList<String>, randList: MutableList<Int>, task: Int, randSeed: Int, variablesNum: Int,
         printfNum: Int, nestingLevel: Int, ListBool: MutableList<Int>, operationIdList: MutableList<Int>,
         listFloat: MutableList<Float>, listInt: MutableList<Int>, variableList: MutableList<String>,
         printfList: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$ELSE ${BRACKETS[2]}$CARRIAGE_RETURN")
    prog_.addAll(Init(2, randSeed, variablesNum, printfNum, 0, 0, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.addAll(Printf(program, randList, 0, randSeed, printfList.size))
    if (randListBoolPop(ListBool) && printfNum - printfList.size > 0 && nestingLevel > 0)
        prog_.addAll(If(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel - 1, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.add("${BRACKETS[3]}$CARRIAGE_RETURN")
    return prog_
}

fun While(program: MutableList<String>, randList: MutableList<Int>, task: Int, randSeed: Int, variablesNum: Int,
          printfNum: Int, nestingLevel: Int, ListBool: MutableList<Int>, operationIdList: MutableList<Int>,
          listFloat: MutableList<Float>, listInt: MutableList<Int>, variableList: MutableList<String>,
          printfList: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$WHILE ${BRACKETS[0]}${IDENTIFIER[rand(0, 2, randSeed)]} % 4${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    printfList.add(1)
    prog_.addAll(Init(2, randSeed, variablesNum, printfNum, 0, 0, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.addAll(Printf(program, randList, task, randSeed, printfList.size))
    if (randListBoolPop(ListBool))
        prog_.addAll(ExitPoint(ListBool))

    if (randListBoolPop(ListBool) && printfNum - printfList.size > 0 && nestingLevel > 0)
        prog_.addAll(While(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel - 1, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.add("${BRACKETS[3]}$CARRIAGE_RETURN")

    return prog_
}

fun DoWhile(program: MutableList<String>, randList: MutableList<Int>, task: Int, randSeed: Int, variablesNum: Int,
            printfNum: Int, nestingLevel: Int, ListBool: MutableList<Int>, operationIdList: MutableList<Int>,
            listFloat: MutableList<Float>, listInt: MutableList<Int>, variableList: MutableList<String>,
            printfList: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$DO ${BRACKETS[2]}$CARRIAGE_RETURN")
    printfList.add(1)
    prog_.addAll(Init(2, randSeed, variablesNum, printfNum, 0, 0, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.addAll(Printf(program, randList, task, randSeed, printfList.size))
    if (randListBoolPop(ListBool))
        prog_.addAll(ExitPoint(ListBool))

    if (randListBoolPop(ListBool) && printfNum - printfList.size > 0 && nestingLevel > 0)
        prog_.addAll(DoWhile(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel - 1, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.add("${BRACKETS[3]} $WHILE ${BRACKETS[0]}${IDENTIFIER[rand(0, 2, randSeed)]} % 5${BRACKETS[1]}$END_OF_LINE")

    return prog_
}

fun ForLoop(program: MutableList<String>, randList: MutableList<Int>, task: Int, randSeed: Int, variablesNum: Int,
            printfNum: Int, nestingLevel: Int, ListBool: MutableList<Int>, size: Int, operationIdList: MutableList<Int>,
            listFloat: MutableList<Float>, listInt: MutableList<Int>, variableList: MutableList<String>,
            printfList: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.add("$FOR ${BRACKETS[0]}$SIZE_T i $EQUALLY 0$SEMICOLON i < $size$SEMICOLON i++${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    printfList.add(1)
    prog_.addAll(Init(2, randSeed, variablesNum, printfNum, 0, 0, ListBool, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.addAll(Printf(program, randList, task, randSeed, printfList.size))
    if (randListBoolPop(ListBool))
        prog_.addAll(ExitPoint(ListBool))

    if (randListBoolPop(ListBool) && printfNum - printfList.size > 0 && nestingLevel > 0)
        prog_.addAll(ForLoop(program, randList, task, randSeed, variablesNum, printfNum, nestingLevel - 1, ListBool, size, operationIdList, listFloat, listInt, variableList, printfList))
    prog_.add("${BRACKETS[3]}$CARRIAGE_RETURN")
    return prog_
}

fun ExitPoint(ListBool: MutableList<Int>): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    if (randListBoolPop(ListBool)) {
        prog_.add("$CONTINUE$END_OF_LINE")
        return prog_
    }
    if (randListBoolPop(ListBool)) {
        prog_.add("$BREAK$END_OF_LINE")
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
fun randListFloat(random: Random, from: Int, size: Int): MutableList<Float> = MutableList(size) {
    random.nextFloat() + from
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

fun programGenerate(args: MutableList<String>) : MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    prog_.addAll(Include(0))
    if (parseInt(args[0]) != 1)
        prog_.addAll(Include(1))
    prog_.add("$CARRIAGE_RETURN")
    prog_.addAll(Main(args))
    return prog_
}

fun checkMask(args: MutableList<String>) : Boolean {
    return true
}

fun printFun(args: MutableList<String>): MutableList<String> {
    val program: MutableList<String> = mutableListOf()
    if (checkMask(args)) {
        program.addAll(programGenerate(args))
//    val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
//    var file = File("func_$time.c")

        var file = File("func.c")
        file.writeText(program.joinToString(""))
    }
    return program
}

fun main() {
    val server: Server = Server()
    server.main()
}