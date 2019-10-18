//import com.example.Server
import java.io.File

import com.example.Generator
import com.example.ProgramParameters
import java.lang.Integer.parseInt

val ERROR_TEXT = "Ошибка ввода. Попробуйте снова.\n\n"

fun main(args: Array<String>) {
//    val server = Server()

//    main_(args)

    main__()
}

fun readFile(fileName: String) = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)

fun main_(args: Array<String>) {
    val args_: MutableList<String> = mutableListOf()
    args_.addAll(args[0].split(' '))
    if (!args_.isEmpty()) {
        if ((parseInt(args_[0]) == 1 && args_.size >= 8) || (parseInt(args_[0]) == 3 && args_.size == 7)
            || ((parseInt(args_[0]) == 2 || parseInt(args_[0]) == 4 || parseInt(args_[0]) == 5 || parseInt(args_[0]) == 6) && args_.size == 6)
            || (parseInt(args_[0]) == 7 && args_.size == 11) || (parseInt(args_[0]) == 8 && args_.size == 6)) {
//            print(args_[1])
//            print("   ")
            args_[1] = "${args_[1].hashCode()}"
            val parameters = ProgramParameters(args_)
            var generator = Generator(parameters)

            generator.programGenerate()
//            while (!generator.runtime()) {
//                args_[1] = "${parseInt(args_[1]) + 1}"
//                val parameters_ = ProgramParameters(args_)
//                generator = Generator(parameters_)
//                generator.programGenerate()
//            }
//            println(args_.joinToString(" "))

//            generator.runtime()
            print(generator.runtime())
//            println("   ")
//            if (generator.runtime()) {
//                println(args_)
//                println(readFile("program.c"))
//                println(readFile("program_result.txt"))
//            }
        }
        else println(ERROR_TEXT)
    }
    else println(ERROR_TEXT)
}

fun main__() {
//    var args1 = arrayOf("5 variant_65 18 7 16 5")
//    val args_: MutableList<String> = mutableListOf()
//    args_.addAll(args1[0].split(' '))
//    args_[1] = "${args_[1].hashCode()}"
//    val parameters = ProgramParameters(args_)
//    var generator = Generator(parameters)
//    generator.programGenerate()
//    generator.runtime()

    val image = Image(readFile("program.c"))
}