#!/bin/bash
kotlinc src/generate.kt -include-runtime -d generate.jar
#java -jar generate.jar '1 19 1 10 3 4 1 << >> | & * +'
#java -jar generate.jar '2 48'
java -jar generate.jar '3 15'
#java -jar generate.jar '4 5'
gcc func.c -o func
./func