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

fun Identifier(index: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(IDENTIFIER[index])
    return program_
}

fun checkIdentifier(program: MutableList<String>, index: Int) : Boolean {
    val value = "${IDENTIFIER[index]} $EQUALLY"
    for (i: Int in 0..(program.size - 1))
        if (program[i] == value)
            for (j: Int in (i + 1)..(program.size - 1))
                if (program[j] == END_OF_LINE)
                    return true
    return false
}

fun Identifier(program: MutableList<String>, args: Array<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    val index = rand(0, parseInt(args[1]))
    val value = "${IDENTIFIER[index]} $EQUALLY"
    for (i: Int in 0..(program.size - 1))
        if (program[i] == value)
            for (j: Int in (i + 1)..(program.size - 1))
                if (program[j] == END_OF_LINE) {
                    program_.addAll(Identifier(index))
                    return program_
                }
    return Identifier(program, args)
}

fun printfStamp(program: MutableList<String>, args: Array<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    var index = rand(0, parseInt(args[1]))
    while ( !checkIdentifier(program, index) )
        index = rand(0, parseInt(args[1]))
    program_.add("$TAB${SERVICE_WORDS[0]}${BRACKETS[0]}${QUOTES}%i${CARRIAGE_RETURN_}${QUOTES}${COMMA} ${IDENTIFIER[index]}${BRACKETS[1]}")
    program_.add(END_OF_LINE)
    return program_
}

fun OperationType(args: Array<String>) : MutableList<String> {
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    for ( i: Int in 5..(args.size - 1) )
        OPERATIONS_TYPE.add( args[i] )
    return OPERATIONS_TYPE
}

fun Operation(args: Array<String>) : MutableList<String> {
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    OPERATIONS_TYPE.addAll( OperationType(args) )

    val program_: MutableList<String> = mutableListOf()
    program_.add(" ${OPERATIONS_TYPE[rand(0, OPERATIONS_TYPE.size)]} ")
    return program_
}

fun check(count: Int, index: Int, args: Array<String>) : Boolean {
    if ( count < parseInt(args[index]) )
        return true
    return false
}

fun Include(libr_numb: Int) : MutableList<String> {
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

fun Brackets(program: MutableList<String>, args: Array<String>, count: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(BRACKETS[0])
    program_.addAll(Expression(program, args, count))
    program_.add(BRACKETS[1])
    return program_
}

fun Atom(program: MutableList<String>, args: Array<String>, count: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( check(count, 3, args) )
        if ( randBool() && check(count + 2, 3, args))
            program_.addAll(Brackets(program, args, count))
        else
            if ( randBool() )
                program_.addAll(Identifier(program, args))
            else
                program_.add("${rand(1, MAX_VALUE)}")
    return program_
}

fun ExpressionAddition(program: MutableList<String>, args: Array<String>, count: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( check(count, 3, args) ) {
        program_.addAll(Operation(args))
        program.addAll(program_)
        program_.addAll(Atom(program, args, count))
        val count_ = count + 1
        if ( randBool() )
            program_.addAll(ExpressionAddition(program, args, count_ + 1))
    }
    return program_
}

fun Expression(program: MutableList<String>, args: Array<String>, count: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( count.equals(0) || check(count, 3, args) ) {
        if ( randBool() )
            program_.addAll(Identifier(program, args))
        else
            program_.add("${rand(1, MAX_VALUE)}")
        program.addAll(program_)
        program_.addAll( ExpressionAddition(program, args, count + 1) )
    }
    return program_
}

fun Statement(program: MutableList<String>, args: Array<String>, count: Int, count_: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( check(count, 2, args) ) {
        program_.add(TAB)
        program_.add("${IDENTIFIER[rand(0, parseInt(args[1]))]} ${EQUALLY}")
        program_.add(" ")
        program.addAll(program_)
        program_.addAll( Expression(program, args, 0) )
        program_.add(END_OF_LINE)
        program.addAll(program_)
        //if ( (count + 1) % (parseInt(args[4]) - 1) == 0 && count_ < (parseInt(args[4]) - 1) ) {
        if ( count_ == 0 ) {
            program_.addAll(printfStamp(program, args))
            program.addAll(program_)
            program_.addAll( Statement(program, args, count + 1, parseInt(args[2]) / parseInt(args[4])) )
        }
        else
            program_.addAll( Statement(program, args, count + 1, count_ - 1) )
    }
    return program_
}

fun firstTask(args: Array<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.addAll(Include(0))
    program_.add("$CARRIAGE_RETURN${TYPE[1]} ${FUNCNAME[0]}${BRACKETS[0]}${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    val initialized_args = rand(0, parseInt(args[1]) - 1)   //номера инициализированных переменных
    val uninitialized_args = parseInt(args[1]) - 2          //номера неинициализированных переменных

    for ( i in 0..initialized_args ) {
        program_.add("${TAB}${MODIFIER[2]} ${TYPE[1]} ")
        program_.add("${IDENTIFIER[i]} ${EQUALLY}")
        program_.add(" ${rand(0, MAX_VALUE)}")
        program_.add(END_OF_LINE)
    }

    var i = initialized_args + 1
    program_.add("${TAB}${MODIFIER[2]} ${TYPE[1]}")
    for ( j: Int in i..uninitialized_args ) {
        program_.add(" ${IDENTIFIER[j]}${COMMA}")
        i++
    }
    program_.add(" ${IDENTIFIER[i]}${END_OF_LINE}")
    val program: MutableList<String> = mutableListOf()
    program.addAll(program_)
    if ( parseInt(args[4]) == 0 )
        program_.addAll( Statement(program, args, 0, -1) )
    if ( parseInt(args[4]) == 1 ) {
        program_.addAll( Statement(program, args, 0, -1) )
        program_.addAll( printfStamp(program, args) )
    }
    if ( parseInt(args[4]) != 0 && parseInt(args[4]) != 1 ) {
        program_.addAll(Statement(program, args, 0, parseInt(args[2]) / parseInt(args[4])))
        program_.addAll( printfStamp(program, args) )
    }
    program_.add("${TAB}${RETURN} 0${END_OF_LINE}")
    program_.add(BRACKETS[3])
    return program_
}

fun rand(from: Int, to: Int) : Int {
    val random = Random()
    return random.nextInt(to - from) + from
}

fun randBool() : Boolean {
    if ( rand(0, 2).equals(1) )
        return true
    return false
}

fun printFun(args: Array<String>) {
    val program: MutableList<String> = mutableListOf()
    if ( parseInt(args[0]).equals(1) ) {
        program.addAll( firstTask(args) )

//        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
//        var file = File("func_$time.c")

        var file = File("func.c")
        file.writeText(program.joinToString(SPACE))
        println(program.joinToString(SPACE))
    }
}

//temporary function
fun taskNumb() : String {
    print( "task number: " )
    val task_numb = readLine()!!
    return task_numb
}

//temporary function
fun argsNumb() : String {
    print( "number of arguments: " )
    val args_numb = readLine()!!
    return args_numb
}

//temporary function
fun linesNumb() : String {
    print( "number of lines: " )
    val lines_numb = readLine()!!
    return lines_numb
}

//temporary function
fun operationsNumb() : String {
    print( "number of operations: " )
    val operations_numb = readLine()!!
    return operations_numb
}

//temporary function
fun printfNumb() : String {
    print( "number printf: " )
    val printf_numb = readLine()!!
    return printf_numb
}

//temporary function
fun operation() : String {
    print( "math operation: " )
    val operation = readLine()!!
    return operation
}

fun main(args: Array<String>) {
    /*var args_: MutableList<String> = mutableListOf()
    args_.add(taskNumb())
    args_.add(argsNumb())
    args_.add(linesNumb())
    args_.add(operationsNumb())
    args_.add(printfNumb())

    var op = operation()
    while (op != ".") {
        args_.add(op)
        op = operation()
    }

    printFun(args_.toTypedArray())*/
    printFun(args)
}
