import java.io.File
import java.lang.Integer.parseInt
import java.util.*
/*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
*/
const val MAX_VALUE: Int = 7
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
fun Identifier(program: MutableList<String>, randList: MutableList<Int>): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    val index = randListPop(randList)
    val value = "${IDENTIFIER[index]} $EQUALLY"
    for (i: Int in 0..(program.size - 1))
        if (program[i] == value)
            for (j: Int in (i + 1)..(program.size - 1))
                if (program[j] == END_OF_LINE) {
                    program_.addAll(Identifier(index))
                    return program_
                }
    return Identifier(program, randList)
}

//печатает инструкцию вывода
fun printfStamp(program: MutableList<String>, randList: MutableList<Int>): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    while (!checkIdentifier(program, randList.first()))
        randList.remove(randList.first())
    program_.add("$TAB${SERVICE_WORDS[0]}${BRACKETS[0]}${QUOTES}%i${CARRIAGE_RETURN_}${QUOTES}${COMMA} ${IDENTIFIER[randListPop(randList)]}${BRACKETS[1]}")
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
fun Operation(operation: MutableList<String>, randList: MutableList<Int>): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(" ${operation[randListPop(randList)]} ")
    return program_
}

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
                program_.add("${randListPop(randList3)}")
    return program_
}

//добавляет мат оператор и аргумент в строку-выражение
fun ExpressionAddition(program: MutableList<String>, operation: MutableList<String>, randList1: MutableList<Int>, randList2: MutableList<Int>,
                       randList3: MutableList<Int>, randListBool: MutableList<Int>, argNum: Int, count: Int): MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (count < argNum) {
        program_.addAll(Operation(operation, randList2))
        program.addAll(program_)
        if (checkOperator(program)) { //проверяет наличие операторов сдвига (<<, >>) в контейнере
            program_.add("${randListPop(randList3)}")
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
            program_.add("${randListPop(randList3)}")
        else {
            if (randListBoolPop(randListBool))
                program_.addAll(Identifier(program, randList1))
            else
                program_.add("${randListPop(randList3)}")
            program.addAll(program_)
            program_.addAll(ExpressionAddition(program, operation, randList1, randList2, randList3, randListBool, argNum, count + 1))
        }
    }
    return program_
}

//добавляет строку-выражение в контейнер
fun State(prog: MutableList<String>, operation: MutableList<String>, List1: MutableList<Int>, List2: MutableList<Int>, List3: MutableList<Int>,
          ListBool: MutableList<Int>, varNum: Int, stateNum: Int, argNum: Int, printfNum: Int, redefinitonVar: Int, count: Int, count_: Int): MutableList<String> {
    val prog_: MutableList<String> = mutableListOf()
    if (count < stateNum) {
        prog_.add(TAB)
        val index = randListPop(List1)
        prog_.add("${IDENTIFIER[index]} ${EQUALLY}")
        prog_.add(" ")
        prog.addAll(prog_)
        if (checkIdentifier(prog, index) && redefinitonVar == 0) { //выключено переопределение = 0
            prog_.addAll(Identifier(index))
            prog.addAll(prog_)
            prog_.addAll(ExpressionAddition(prog, operation, List1, List2, List3, ListBool, argNum, 1))
        } else
            prog_.addAll(Expression(prog, operation, List1, List2, List3, ListBool, argNum, 0))
        prog_.add(END_OF_LINE)

        prog.addAll(prog_)
        if (count_ == 0) {
            prog_.addAll(printfStamp(prog, List1))
            prog.addAll(prog_)
            prog_.addAll(State(prog, operation, List1, List2, List3, ListBool, varNum, stateNum, argNum, printfNum, redefinitonVar, count + 1, stateNum / printfNum))
        }
        else
            prog_.addAll(State(prog, operation, List1, List2, List3, ListBool, varNum, stateNum, argNum, printfNum, redefinitonVar, count + 1, count_ - 1))
    }
    return prog_
}

fun firstTask(operation: MutableList<String>, randSeed: Int, varNum: Int, stateNum: Int, argNum: Int, printfNum: Int, redefinitonVar: Int): MutableList<String> {
    val from = 0
    val to = varNum
    val size = 200

    val List1 = randList(Random(randSeed.toLong()), from, to, size)
    val List2 = randList(Random(randSeed.toLong()), from, operation.size, size)
    val List3 = randList(Random(randSeed.toLong()), from + 1, MAX_VALUE, size)
    val ListBool = randList(Random(randSeed.toLong()), 0, 2, size)

    val prog_: MutableList<String> = mutableListOf()
    prog_.addAll(Include(0))
    prog_.add("$CARRIAGE_RETURN${TYPE[1]} ${FUNCNAME[0]}${BRACKETS[0]}${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    val initialized_args = rand(from, to - 1, randSeed)   //номера инициализированных переменных
    val uninitialized_args = varNum - 2          //номера неинициализированных переменных

    val randList4 = randList(Random(randSeed.toLong()), from, MAX_VALUE, initialized_args + 1)
    //инициализация переменных
    for (i in 0..initialized_args) {
        prog_.add("${TAB}${MODIFIER[2]} ${TYPE[1]} ")
        prog_.add("${IDENTIFIER[i]} ${EQUALLY}")
        prog_.add(" ${randListPop(randList4)}")
        prog_.add(END_OF_LINE)
    }

    if (uninitialized_args > -1) {
        var i = initialized_args + 1
        prog_.add("${TAB}${MODIFIER[2]} ${TYPE[1]}")
        for (j: Int in i..uninitialized_args) {
            prog_.add(" ${IDENTIFIER[j]}${COMMA}")
            i++
        }
        prog_.add(" ${IDENTIFIER[i]}${END_OF_LINE}")
    }

    val prog: MutableList<String> = mutableListOf()
    prog.addAll(prog_)

    if (printfNum != 0 && printfNum != 1) {
        prog_.addAll(State(prog, operation, List1, List2, List3, ListBool, varNum, stateNum, argNum, printfNum, redefinitonVar, 0, stateNum / printfNum))
        prog_.addAll(printfStamp(prog, List1))
    }
    else {
        prog_.addAll(State(prog, operation, List1, List2, List3, ListBool, varNum, stateNum, argNum, printfNum, redefinitonVar,0, -1))
        if (printfNum == 1)
            prog_.addAll(printfStamp(prog, List1))
    }

    prog_.add("${TAB}${RETURN} 0${END_OF_LINE}")
    prog_.add(BRACKETS[3])
    return prog_
}

//генерирует число в диапазоне [from; to] с зерном randSeed
fun rand(from: Int, to: Int, randSeed: Int): Int {
    val random = Random(randSeed.toLong())
    if (from == to)
        return from
    return random.nextInt(to - from) + from
}

//генерирует последовательность в диапазоне [from; to] с зерном randSeed
fun randList(random: Random, from: Int, to: Int, size: Int): MutableList<Int> = MutableList(size) {
    random.nextInt(to - from) + from
}

//возвращает первое число из списка и удаляет его Int
fun randListPop(randList: MutableList<Int>) : Int {
    val index = randList.first()
    randList.remove(index)
    return index
}

//возвращает первое число из списка и удаляет его Boolean
fun randListBoolPop(randListBool: MutableList<Int>) : Boolean {
    val flag = randListBool.first()
    randListBool.remove(randListBool.first())
    if (flag == 1)
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
/*    val server = embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }
        }
    }
    server.start(wait = true)*/
}