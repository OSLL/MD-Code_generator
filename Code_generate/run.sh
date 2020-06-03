#!/bin/bash
set -e
clang-format -i program.c
gcc program.c -o program
timeout 0.1s ./program > program_result.txt
