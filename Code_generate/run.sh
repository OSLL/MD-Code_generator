#!/bin/bash
clang-format -i program.c
gcc program.c -o program
./program > program_result.txt