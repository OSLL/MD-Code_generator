#!/bin/bash
clang-format -i program.c
gcc program.c -o program
timeout 0.1s ./program > program_result.txt