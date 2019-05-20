#!/bin/bash
kotlinc src/generate.kt -include-runtime -d generate.jar
#java -jar generate.jar '1 19 1 4 3 2 1 << >> | & * +'
#java -jar generate.jar '2 48 2 4 1' #if
#java -jar generate.jar '3 48 3 4 0' #switch
#java -jar generate.jar '4 15 3 4 1' #while
#java -jar generate.jar '5 15 3 4 1' #dowhile
java -jar generate.jar '6 5 3 2 1' #for
gcc func.c -o func
./func