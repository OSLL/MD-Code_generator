import java.util.Random
const val MAX_VALUE: Int = 1000
const val MIN_VALUE: Int = -1000

fun rand(from: Int, to: Int) : Int {
    val random = Random()
    return random.nextInt(to - from) + from
}

fun operator(): Char {
    val c = rand(3, 5)
    var r = (' ')
    if (c.equals(0)) {
        r = '-'
        r
    }
    if (c.equals(1)) {
        r = '+'
        r
    }
    if (c.equals(2)) {
        r = '*'
        r
    }
    if (c.equals(3)) {
        r = '/'
        r
    }
    if (c.equals(4)) {
        r = '%'
        r
    }
    return r
}

fun funcName(r: Char): String {
    var q = ""
    if ( r == '-' ) {
        q = "dif"
        q
    }
    if ( r == '+' ) {
        q = "sum"
        q
    }
    if ( r == '*' ) {
        q = "mult"
        q
    }
    if ( r == '/' ) {
        q = "div"
        q
    }
    if ( r == '%' ) {
        q = "mod"
        q
    }
    return q
}

fun printFun(a: Int, b: Int, c: Char) {
    val funcName = funcName(c)
    println("Какое значение вернёт функция $funcName, если a = $a, b = $b?\n")
    println("int $funcName(int a, int b) {")
    println("   return a $c b;")
    println("}")
}

//поправить %
fun calc(a: Int, b: Int, c: Char): Int {
    var q = a - b
    if ( c == '-' ) {
        q = a - b
        q
    }
    if ( c == '+' ) {
        q = a + b
        q
    }
    if ( c == '*' ) {
        q = a * b
        q
    }
    if ( c == '/' ) {
        q = a / b
        q
    }
    if ( c == '%' ) {
        q = a % b
        q
    }
    return q
}

fun printQuest(a: Int, b: Int, c: Char) {
    val question = readLine()!!.toInt()
    if (question != null) {
        val quest = calc(a, b, c)
        //println(quest)
        when ( question ) {
            quest -> println("+1")
            else -> println("0")
        }
    }
}

fun main(args: Array<String>) {
    val a = rand(MIN_VALUE, MAX_VALUE)
    val b = rand(MIN_VALUE, MAX_VALUE)
    val c = operator()
    printFun(a, b, c)
    printQuest(a, b, c)
}