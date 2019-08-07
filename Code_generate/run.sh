#!/bin/bash
kotlinc src/ProgramParameters.kt src/Program.kt src/generate.kt -include-runtime -d generate.jar
#java -jar generate.jar '1 17 6 4 3 2 1 << >> | & * +'
#java -jar generate.jar '2 2 10 15 5 3' #if
#java -jar generate.jar '2 0 7 2 5 5' #if
#java -jar generate.jar '2 1 4 4 3 0' #if
#java -jar generate.jar '2 0 2 2 2 0' #if
#java -jar generate.jar '2 0 4 4 4 1' #if
#java -jar generate.jar '2 7 8 7 7 6' #if
#java -jar generate.jar '' #switch
#java -jar generate.jar '4 0 7 5 3 1' #while
#java -jar generate.jar '4 0 4 4 2 0' #while
java -jar generate.jar '4 36 20 8 7 6' #while
#java -jar generate.jar '5 0 4 3 4 3' #dowhile
#java -jar generate.jar '5 1 4 4 2 1' #dowhile
#java -jar generate.jar '5 2 8 7 6 5' #dowhile
#java -jar generate.jar '6 0 1 3 2 0 3' #for
#java -jar generate.jar '4 13 8 8 7 6' #while
#java -jar generate.jar '5 5 2 2 2 0' #dowhile
#java -jar generate.jar '5 14 4 4 4 2' #dowhile
clang-format -i func.c
gcc func.c -o func
#./func