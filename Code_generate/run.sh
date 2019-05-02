#!/bin/bash
kotlinc src/generate.kt -include-runtime -d generate.jar
#java -jar generate.jar '1 19 1 6 5 4 1 << >> | & * +'
#java -jar generate.jar '2 48'
java -jar generate.jar '3 47'
#java -jar generate.jar '4 5'
gcc func.c -o func
./func