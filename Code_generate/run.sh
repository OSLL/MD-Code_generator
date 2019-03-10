#!/bin/bash
kotlinc src/generate.kt -include-runtime -d generate.jar
java -jar generate.jar '1 42 1 1 2 1 1 +'
gcc func.c -o func
./func