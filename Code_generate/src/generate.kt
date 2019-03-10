import java.io.File
import java.lang.Integer.parseInt
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val MAX_VALUE: Int = 20
const val MIN_VALUE: Int = -MAX_VALUE

val SPACE = ""
val TYPE: List<String> = listOf("void", "int", "bool", "float", "double")
val MODIFIER: List<String> = listOf("short", "long", "unsigned")
val FUNCNAME: List<String> = listOf("main", "subtraction", "addition", "multiply", "div", "mod")
val ARITHMETIC_OPERATIONS: List<String> = listOf("+", "-", "/", "%")
val LOGICAL_OPERATIONS: List<String> = listOf("!", "&&", "||")
val RELATIONAL_OPERATIONS: List<String> = listOf("<", ">", "<=", ">=", "==", "!=")
val SPECIAL_OPERATIONS: List<String> = listOf("++", "--")
val BITWISE_OPERATIONS: List<String> = listOf("<<", ">>", "|", "&")
val IDENTIFIER: List<String> = listOf("a", "b", "c", "d", "e", "f", "g")
val CARRIAGE_RETURN = "\n"
val CARRIAGE_RETURN_ = "\\n"
val EQUALLY = "="
val COMMA = ","
val DOT = "."
val SEMICOLON = ";"
val COLON = ":"
val QUOTES = "\""
val END_OF_LINE = "${SEMICOLON}${CARRIAGE_RETURN}"
val LIBRARY: List<String> = listOf("stdio.h")
val INCLUDE: List<String> = listOf("#include <", ">")
val BRACKETS: List<String> = listOf("(", ")", "{", "}", "[", "]")
val TAB = "    "
val RETURN = "return"
val SERVICE_WORDS: List<String> = listOf("printf")

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
fun Identifier(program: MutableList<String>, from: Int, to: Int, randSeed: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    val index = rand(from, to, randSeed)
    val value = "${IDENTIFIER[index]} $EQUALLY"
    for (i: Int in 0..(program.size - 1))
        if (program[i] == value)
            for (j: Int in (i + 1)..(program.size - 1))
                if (program[j] == END_OF_LINE) {
                    program_.addAll(Identifier(index))
                    return program_
                }
    return Identifier(program, from, to, randSeed)
}

//печатает инструкцию вывода
fun printfStamp(program: MutableList<String>, from: Int, to: Int, randSeed: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    var index = rand(from, to, randSeed)
    while (!checkIdentifier(program, index))
        index = rand(from, to, randSeed)
    program_.add("$TAB${SERVICE_WORDS[0]}${BRACKETS[0]}${QUOTES}%i${CARRIAGE_RETURN_}${QUOTES}${COMMA} ${IDENTIFIER[index]}${BRACKETS[1]}")
    program_.add(END_OF_LINE)
    return program_
}

//заполняет контейнер мат операторами
fun OperationType(args: MutableList<String>, operationIndex: Int): MutableList<String> {
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    for (i: Int in operationIndex..(args.size - 1))
        OPERATIONS_TYPE.add(args[i])
    return OPERATIONS_TYPE
}

//выбирает случайный мат опретаор из контейнера мат операторов
fun Operation(operation: MutableList<String>, from: Int, randSeed: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(" ${operation[rand(from, operation.size, randSeed)]} ")
    return program_
}

//проверяет не превышает ли счётчик заданный аргумент
/*fun check(count: Int, index: Int, args: MutableList<String>): Boolean {
    if (count < parseInt(args[index]))
        return true
    return false
}*/

//добавляет в контейнер заданную библиотеку
fun Include(libr_numb: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add("${INCLUDE[0]}${LIBRARY[libr_numb]}${INCLUDE[1]}$CARRIAGE_RETURN")
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
fun Brackets(program: MutableList<String>, operation: MutableList<String>, from: Int, to: Int,
             randSeed: Int, argumentsNum: Int, count: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(BRACKETS[0])
    program_.addAll(Expression(program, operation, from, to, randSeed, argumentsNum, count))
    program_.add(BRACKETS[1])
    return program_
}

//добавляет либо скобки, либо переменную или число
fun Atom(program: MutableList<String>, operation: MutableList<String>, from: Int, to: Int,
         randSeed: Int, argumentsNum: Int, count: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (count < argumentsNum)
        if (randBool(randSeed) && count + 2 < argumentsNum)
            program_.addAll(Brackets(program, operation, from, to, randSeed, argumentsNum, count))
        else
            if (randBool(randSeed))
                program_.addAll(Identifier(program, from, to, randSeed))
            else
                program_.add("${rand(1, MAX_VALUE, randSeed)}")
    return program_
}

//добавляет мат оператор и аргумент в строку-выражение
fun ExpressionAddition(program: MutableList<String>, operation: MutableList<String>, from: Int, to: Int,
                       randSeed: Int, argumentsNum: Int, count: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (count < argumentsNum) {
        program_.addAll(Operation(operation, from, randSeed))
        program.addAll(program_)
        if (checkOperator(program)) { //проверяет наличие операторов сдвига (<<, >>) в контейнере
            program_.add("${rand(1, MAX_VALUE, randSeed)}")
            return program_
        }
        program_.addAll(Atom(program, operation, from, to, randSeed, argumentsNum, count))
        val count_ = count + 1
        if (randBool(randSeed))
            program_.addAll(ExpressionAddition(program, operation, from, to, randSeed, argumentsNum, count_ + 1))
    }
    return program_
}

//добавляет аргумент в строку-выражение
fun Expression(program: MutableList<String>, operation: MutableList<String>, from: Int, to: Int,
               randSeed: Int, argumentsNum: Int, count: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (count == 0 || count < argumentsNum) {
        if (checkOperator(program)) //проверяет наличие операторов сдвига (<<, >>) в контейнере
            program_.add("${rand(1, MAX_VALUE, randSeed)}")
        else {
            if (randBool(randSeed))
                program_.addAll(Identifier(program, from, to, randSeed))
            else
                program_.add("${rand(1, MAX_VALUE, randSeed)}")
            program.addAll(program_)
            program_.addAll(ExpressionAddition(program, operation, from, to, randSeed, argumentsNum, count + 1))
        }
    }
    return program_
}

//добавляет строку-выражение в контейнер
fun Statement(program: MutableList<String>, operation: MutableList<String>, from: Int, to: Int, randSeed: Int, variablesNum: Int,
              statementsNum: Int, argumentsNum: Int, printfNum: Int, redefinitonVar: Int, count: Int, count_: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (count < statementsNum) {
        program_.add(TAB)
        val index = rand(0, variablesNum, randSeed)
        program_.add("${IDENTIFIER[index]} ${EQUALLY}")
        program_.add(" ")
        program.addAll(program_)
        if (checkIdentifier(program, index) && redefinitonVar == 0) { //выключено переопределение = 0
            program_.addAll(Identifier(index))
            program.addAll(program_)
            program_.addAll(ExpressionAddition(program, operation, from, to, randSeed, argumentsNum, 1))
        } else
            program_.addAll(Expression(program, operation, from, to, randSeed, argumentsNum, 0))
        program_.add(END_OF_LINE)

        program.addAll(program_)
        if (count_ == 0) {
            program_.addAll(printfStamp(program, from, to, randSeed))
            program.addAll(program_)
            program_.addAll(Statement(program, operation, from, to, randSeed, variablesNum, statementsNum, argumentsNum, printfNum, redefinitonVar, count + 1, statementsNum / printfNum))
        }
        else
            program_.addAll(Statement(program, operation, from, to, randSeed, variablesNum, statementsNum, argumentsNum, printfNum, redefinitonVar, count + 1, count_ - 1))
    }
    return program_
}

fun firstTask(operation: MutableList<String>, randSeed: Int, variablesNum: Int,
              statementsNum: Int, argumentsNum: Int, printfNum: Int, redefinitonVar: Int): MutableList<String> {
    val from = 0
    val to = variablesNum
    val program_: MutableList<String> = mutableListOf()
    program_.addAll(Include(0))
    program_.add("$CARRIAGE_RETURN${TYPE[1]} ${FUNCNAME[0]}${BRACKETS[0]}${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    val initialized_args = rand(0, variablesNum - 1, randSeed)   //номера инициализированных переменных
    val uninitialized_args = variablesNum - 2          //номера неинициализированных переменных

    //инициализация переменных
    for (i in 0..initialized_args) {
        program_.add("${TAB}${MODIFIER[2]} ${TYPE[1]} ")
        program_.add("${IDENTIFIER[i]} ${EQUALLY}")
        program_.add(" ${rand(0, MAX_VALUE, randSeed)}")
        program_.add(END_OF_LINE)
    }

    if (uninitialized_args > -1) {
        var i = initialized_args + 1
        program_.add("${TAB}${MODIFIER[2]} ${TYPE[1]}")
        for (j: Int in i..uninitialized_args) {
            program_.add(" ${IDENTIFIER[j]}${COMMA}")
            i++
        }
        program_.add(" ${IDENTIFIER[i]}${END_OF_LINE}")
    }

    val program: MutableList<String> = mutableListOf()
    program.addAll(program_)

    if (printfNum != 0 && printfNum != 1) {
        program_.addAll(Statement(program, operation, from, to, randSeed, variablesNum, statementsNum, argumentsNum, printfNum, redefinitonVar, 0, statementsNum / printfNum))
        program_.addAll(printfStamp(program, from, to, randSeed))
    }
    else {
        program_.addAll(Statement(program, operation, from, to, randSeed, variablesNum, statementsNum, argumentsNum, printfNum, redefinitonVar,0, -1))
        if (printfNum == 1)
            program_.addAll(printfStamp(program, from, to, randSeed))
    }

    program_.add("${TAB}${RETURN} 0${END_OF_LINE}")
    program_.add(BRACKETS[3])
    return program_
}

//генерирует число в диапазоне [from; to] с зерном randSeed
fun rand(from: Int, to: Int, randSeed: Int): Int {
    val random = Random(randSeed.toLong())
    if (from == to)
        return from
    return random.nextInt(to - from) + from
}

//генерирует true или false
fun randBool(randSeed: Int): Boolean {
    if (rand(0, 2, randSeed).equals(1))
        return true
    return false
}

fun printFun(args: MutableList<String>) {
    val program: MutableList<String> = mutableListOf()
    if (parseInt(args[0]) == 1) {
        val randSeed = parseInt(args[1])
        val variablesNum = parseInt(args[2])
        val statementsNum = parseInt(args[3])
        val argumentsNum = parseInt(args[4])
        val printfNum = parseInt(args[5])
        val redefinitonVar = parseInt(args[6])
        val operationIndex = 7

        val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
        OPERATIONS_TYPE.addAll(OperationType(args, operationIndex))
        program.addAll(firstTask(OPERATIONS_TYPE, randSeed, variablesNum, statementsNum, argumentsNum, printfNum, redefinitonVar))

//        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
//        var file = File("func_$time.c")

        var file = File("func.c")
        file.writeText(program.joinToString(SPACE))
        println(program.joinToString(SPACE))
    }
}

fun main(args: Array<String>) {
    val args_: MutableList<String> = mutableListOf()
    args_.addAll(args[0].split(' '))
    printFun(args_)
}