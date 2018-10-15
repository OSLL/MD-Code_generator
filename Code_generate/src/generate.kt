import java.io.File
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
val EQUALLY = "="
val COMMA = ","
val DOT = "."
val SEMICOLON = ";"
val COLON = ":"
val END_OF_LINE = "${SEMICOLON}${CARRIAGE_RETURN}"
val LIBRARY: List<String> = listOf("stdio.h")
val INCLUDE: List<String> = listOf("#include <", ">")
val BRACKETS: List<String> = listOf("(", ")", "{", "}", "[", "]")
val TAB = "    "
val RETURN = "return"

//пофиксить повторное подключение либов
fun Include(program: MutableList<String>, libr_numb: Int) : MutableList<String> {
//    if ( randBool() )
//        Include(program)
    val program_: MutableList<String> = mutableListOf()
    program_.add("${INCLUDE[0]}${LIBRARY[libr_numb]}${INCLUDE[1]}$CARRIAGE_RETURN")
    return program_
}

fun Arguments(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (randBool()) {
        program_.addAll(Arguments(program_))
        program_.add("$COMMA ")
    }
    program_.add("${TYPE[ rand(1, TYPE.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    return program_
}

fun ArithmeticExceptionAddition(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( randBool() )
        program_.addAll( ArithmeticExceptionAddition(program_) )
    program_.add(" ${ARITHMETIC_OPERATIONS[ rand(0, ARITHMETIC_OPERATIONS.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    return program_
}

fun ArithmeticExpression(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add("$TAB${IDENTIFIER[ rand(0, IDENTIFIER.size) ]} $EQUALLY ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    if ( randBool() )
        program_.addAll( ArithmeticExceptionAddition(program_) )
    return program_
}

fun BitwiseExceptionAddition(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( randBool() )
        program_.addAll( BitwiseExceptionAddition(program_) )
    program_.add(" ${BITWISE_OPERATIONS[ rand(0, BITWISE_OPERATIONS.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    return program_
}

fun BitwiseExpression(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add("$TAB${IDENTIFIER[ rand(0, IDENTIFIER.size) ]} $EQUALLY ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    if ( randBool() )
        program_.addAll( BitwiseExceptionAddition(program_) )
    return program_
}

//пофиксить использование неинициализированных переменных
//пофиксить способ выбора кол-ва аргументов
fun ExceptionAddition(program: MutableList<String>, args_numb: Int, operations_numb: Int, OPERATIONS_TYPE: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    var i = 0
    val count = rand(1, operations_numb)
//    if ( randBool() )
    while ( i < count ) {
//        program_.addAll( ExceptionAddition(program_, args_numb, operations_numb, OPERATIONS_TYPE) )
        program_.add(" ${OPERATIONS_TYPE[rand(0, OPERATIONS_TYPE.size)]} ")
        if ( randBool() )
            program_.add("${IDENTIFIER[ rand(0, args_numb) ]}")
        else
            program_.add("${rand(0, MAX_VALUE)}")
        i++
    }
    return program_
}

fun Expression(program: MutableList<String>, args_numb: Int, operations_numb: Int, OPERATIONS_TYPE: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    var i = 0
    while ( i < args_numb ) {
        program_.add("$TAB${IDENTIFIER[rand(0, args_numb)]} $EQUALLY ${IDENTIFIER[i]}")
//        if (randBool())
            program_.addAll(ExceptionAddition(program_, args_numb, operations_numb, OPERATIONS_TYPE))
        program_.add("$END_OF_LINE")
        i++
    }
    return program_
}

fun Code(program: MutableList<String>, args_numb: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    val lines_numb = linesNumb()
    val operations_numb = operationsNumb()
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    OPERATIONS_TYPE.addAll( operationsType() )
    println(OPERATIONS_TYPE.size)
//    if ( randBool() )
//        program_.addAll( Code(program_) )
//    program_.addAll( ArithmeticExpression(program_) )
    program_.addAll( Expression(program_, args_numb, operations_numb, OPERATIONS_TYPE) )
    return program_
}

fun Function(program: MutableList<String>) : MutableList<String> {
    val space: MutableList<String> = mutableListOf()
    val program_: MutableList<String> = mutableListOf()
    program_.addAll( Include(program, 0) )
    program_.add("$CARRIAGE_RETURN${TYPE[ rand(0, TYPE.size) ]} ${FUNCNAME[ rand(1, FUNCNAME.size) ]}${BRACKETS[0]}")
    program_.addAll(Arguments(space))
    program_.add("${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
//    program_.addAll(Code(space))
    program_.add("${BRACKETS[3]}")
    return program_
}

fun OneTask(program: MutableList<String>) : MutableList<String> {
    val space: MutableList<String> = mutableListOf()
    val program_: MutableList<String> = mutableListOf()
    program_.addAll( Include(program, 0) )
    program_.add("$CARRIAGE_RETURN${TYPE[1]} ${FUNCNAME[0]}${BRACKETS[0]}${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    var i = 0
    val args_numb = argsNumb()
    val initialized_args = rand(1, args_numb)
    val uninitialized_args = args_numb - initialized_args
    while ( i < initialized_args ) {
        program_.add("${TAB}${MODIFIER[2]} ${TYPE[1]} ${IDENTIFIER[i]} ${EQUALLY} ${rand(0, MAX_VALUE)}${END_OF_LINE}")
        i++
    }
    var j = 0
    program_.add("${TAB}${MODIFIER[2]} ${TYPE[1]}")
    while ( j < uninitialized_args - 1 ) {
        program_.add(" ${IDENTIFIER[i]}${COMMA}")
        j++
        i++
    }
    program_.add(" ${IDENTIFIER[i]}${END_OF_LINE}")
    program_.addAll(Code(space, args_numb))
    program_.add("${TAB}${RETURN} ${BRACKETS[0]}${IDENTIFIER[ rand(0, args_numb) ]}${BRACKETS[1]}${END_OF_LINE}")
    program_.add("${BRACKETS[3]}")
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

//temporary function
fun taskNumb() : Int {
    print( "task number: " )
    val task_numb = readLine()!!.toInt()
    return task_numb
}

//temporary function
fun argsNumb() : Int {
    print( "number of arguments: " )
    val args_numb = readLine()!!.toInt()
    return args_numb
}

//temporary function
fun linesNumb() : Int {
    print( "number of lines: " )
    val lines_numb = readLine()!!.toInt()
    return lines_numb
}

//temporary function
fun operationsNumb() : Int {
    print( "number of operations: " )
    val operations_numb = readLine()!!.toInt()
    return operations_numb
}

//temporary function
fun operationsType() : MutableList<String> {
    print( "types of operations: " )
    val operations_type_: MutableList<String> = mutableListOf()
    val operations_type = readLine()!!.toString()
    for ( operation in operations_type ) {
        if ( operation.toString() == "+" )
            operations_type_.add(operation.toString())
        if ( operation.toString() == "-" )
            operations_type_.add(operation.toString())
        if ( operation.toString() == "*" )
            operations_type_.add(operation.toString())
        if ( operation.toString() == "|" )
            operations_type_.add(operation.toString())
        if ( operation.toString() == "&" )
            operations_type_.add(operation.toString())
        if ( operations_type_.toString() == "<" )
            operations_type_.add("<<")
        if ( operations_type_.toString() == ">" )
            operations_type_.add(">>")
    }
    return operations_type_
}

fun printFun() {
//    val task_numb = task_numb()
//    val lines_numb = linesNumb()
//    val operations_type = operationsType()

    val program: MutableList<String> = mutableListOf()
    if ( taskNumb().equals(1) ) {
        program.addAll( OneTask(program) )

//    val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
//    var file = File("func_$time.c")
        var file = File("func.c")
        file.writeText(program.joinToString(SPACE))

//    val funcName = funcName(c)
        println(program.joinToString(SPACE))
/*    val x: String = time as String
    val exec: Execute = Execute()

    exec.Execut(x)
*/
    }
}

fun main(args: Array<String>) {
    printFun()
}