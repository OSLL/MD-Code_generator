import com.example.Server
import com.example.Generator
import com.example.ProgramParameters
import java.lang.Integer.parseInt

val ERROR_TEXT = "Ошибка ввода. Попробуйте снова.\n\n"

fun main(args: Array<String>) {
    val server = Server()

//    main_(args)
}

fun main_(args: Array<String>) {
    val args_: MutableList<String> = mutableListOf()
    args_.addAll(args[0].split(' '))
    if (!args_.isEmpty()) {
        if (checkData(args_)) {
            val parameters = ProgramParameters(args_)
            var generator = Generator(parameters)
            generator.programGenerate()
        }
        else println(ERROR_TEXT)
    }
    else println(ERROR_TEXT)
}

fun checkData(args_: MutableList<String>): Boolean {
    when (parseInt(args_[0])) {
        1 ->                if (args_.size >= 8) return true
        3 ->                if (args_.size == 7) return true
        2, 4, 5, 6, 8 ->    if (args_.size == 6) return true
        7 ->                if (args_.size == 11) return true
        9 ->                if (args_.size == 6) return true
    }
    return false
}