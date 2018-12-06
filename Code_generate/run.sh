#!/bin/bash
kotlinc src/generate.kt -include-runtime -d generate.jar
java -jar generate.jar '1 4 10 5 4 1 << >> | & * - +'
gcc func.c -o func
./func