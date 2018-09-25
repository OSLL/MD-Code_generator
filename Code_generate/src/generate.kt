import java.util.Random
import java.io.File
const val MAX_VALUE: Int = 1000
const val MIN_VALUE: Int = -1000

const val SPACE: String = ""
const val SUBTRACTION: String = "-"
const val ADDITION: String = "+"
const val MULTIPLICATION: String = "*"
const val DIV: String = "/"
const val MOD: String = "%"

const val SUBTRACTION_FUNC_NAME: String = "subtraction"
const val ADDITION_FUNC_NAME: String = "addition"
const val MULTIPLICATION_FUNC_NAME: String = "multiply"
const val DIV_FUNC_NAME: String = "div"
const val MOD_FUNC_NAME: String = "mod"

fun rand(from: Int, to: Int) : Int {
    val random = Random()
    return random.nextInt(to - from) + from
}

fun operator(): String {
    val c = rand(0, 5)
    var r = (SPACE)
    if (c.equals(0)) {
        r = SUBTRACTION
        r
    }
    if (c.equals(1)) {
        r = ADDITION
        r
    }
    if (c.equals(2)) {
        r = MULTIPLICATION
        r
    }
    if (c.equals(3)) {
        r = DIV
        r
    }
    if (c.equals(4)) {
        r = MOD
        r
    }
    return r
}

fun funcName(r: String): String {
    var q = SPACE
    if ( r == SUBTRACTION ) {
        q = SUBTRACTION_FUNC_NAME
        q
    }
    if ( r == ADDITION ) {
        q = ADDITION_FUNC_NAME
        q
    }
    if ( r == MULTIPLICATION ) {
        q = MULTIPLICATION_FUNC_NAME
        q
    }
    if ( r == DIV ) {
        q = DIV_FUNC_NAME
        q
    }
    if ( r == MOD ) {
        q = MOD_FUNC_NAME
        q
    }
    return q
}

fun printFun(a: Int, b: Int, c: String) {
    val funcName = funcName(c)
    println("Какое значение вернёт функция $funcName, если a = $a, b = $b?\n")
    println("int $funcName(int a, int b) {")
    println("   return a $c b;")
    println("}")

    val fileName = "data.c"
    var file = File(fileName)
    // create a new file
    file.writeText("Какое значение вернёт функция $funcName, если a = $a, b = $b?\n\nint $funcName(int a, int b) {\n    return a $c b;\n}\n")
}

//поправить %
fun calc(a: Int, b: Int, c: String): Int {
    var q = a - b
    if ( c == SUBTRACTION ) {
        q = a - b
        q
    }
    if ( c == ADDITION ) {
        q = a + b
        q
    }
    if ( c == MULTIPLICATION ) {
        q = a * b
        q
    }
    if ( c == DIV ) {
        q = a / b
        q
    }
    if ( c == MOD ) {
        q = a % b
        q
    }
    return q
}

fun printQuest(a: Int, b: Int, c: String) {
    print( "your question: " )
    val question = readLine()!!.toInt()
    if (question != null) {
        val quest = calc(a, b, c)
        println(quest)
        println( question.equals(quest) )
    }
}
fun main(args: Array<String>) {
    val a = rand(MIN_VALUE, MAX_VALUE)
    val b = rand(MIN_VALUE, MAX_VALUE)
    val c = operator()
    printFun(a, b, c)
    printQuest(a, b, c)
}
