import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val MAX_VALUE: Int = 1000
const val MIN_VALUE: Int = -1000

val SPACE = ""
val TYPE: List<String> = listOf("void", "int", "bool", "float", "double")
val MODIFIER: List<String> = listOf("short", "long", "unsigned")
val FUNCNAME: List<String> = listOf("main", "subtraction", "addition", "multiply", "div", "mod")
val ARITHMETIC_OPERATIONS: List<String> = listOf("+", "-", "/", "%")
val LOGICAL_OPERATIONS: List<String> = listOf("!", "&&", "||")
val RELATIONAL_OPERATIONS: List<String> = listOf("<", ">", "<=", ">=", "==", "!=")
val SPECIAL_OPERATIONS: List<String> = listOf("++", "--")
val BITWISE_OPERATIONS: List<String> = listOf("<<", ">>", "|", "&")
val IDENTIFIER: List<String> = listOf("a", "b", "c")
val CARRIAGE_RETURN = "\n"
val EQUALLY = "="
val COMMA = ","
val DOT = "."
val SEMICOLON = ";"
val COLON = ":"
val LIBRARY: List<String> = listOf("stdio.h")
val INCLUDE: List<String> = listOf("#include <", ">")
val BRACKETS: List<String> = listOf("(", ")", "{", "}", "[", "]")
val TAB = "    "
val RETURN = "return"

//пофиксить повторное подключение либов
fun Include(program: MutableList<String>) : MutableList<String> {
    if ( randBool() )
        Include(program)
    program.add("${INCLUDE[0]}${LIBRARY[ rand(0, LIBRARY.size) ]}${INCLUDE[1]}$CARRIAGE_RETURN")
    return program
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

fun Code(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( randBool() )
        program_.addAll( Code(program_) )
//    program_.addAll( ArithmeticExpression(program_) )
    program_.addAll( BitwiseExpression(program_) )
    program_.add("$SEMICOLON$CARRIAGE_RETURN")
    return program_
}

fun Function(program: MutableList<String>) : MutableList<String> {
    val space: MutableList<String> = mutableListOf()
    val program_: MutableList<String> = mutableListOf()
    program_.addAll( Include(program) )
    program_.add("$CARRIAGE_RETURN${TYPE[ rand(0, TYPE.size) ]} ${FUNCNAME[ rand(1, FUNCNAME.size) ]}${BRACKETS[0]}")
    program_.addAll(Arguments(space))
    program_.add("${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    program_.addAll(Code(space))
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

fun printFun() {
    val program: MutableList<String> = mutableListOf()
    program.addAll( Function(program) )

//    val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
//    var file = File("func_$time.c")
    var file = File("func.c")
    file.writeText(program.joinToString(SPACE))

//    val funcName = funcName(c)
    println(program)
/*    val x: String = time as String
    val exec: Execute = Execute()

    exec.Execut(x)
*/
}

fun printQuest() {
    print( "your question: " )
    val question = readLine()!!.toInt()
}

fun main(args: Array<String>) {
    printFun()
//    printQuest()
}