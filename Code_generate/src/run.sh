#!/bin/bash
#kotlinc src/generate.kt -include-runtime -d generate.jar
#java -jar generate.jar '1 17 6 4 3 2 1 << >> | & * +'
#java -jar generate.jar '2 1 4 6 4' #if
#java -jar generate.jar '3 48 3 4 0' #switch
#java -jar generate.jar '4 13 6 7 3' #while
#java -jar generate.jar '5 4 4 4 3' #dowhile
#java -jar generate.jar '6 18 6 4 3 5' #for
clang-format -i func.c
func.c
#gcc func.c -o func
#./func