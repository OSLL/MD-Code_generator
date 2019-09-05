#!/bin/bash
clang-format -i func.c
gcc func.c -o func
./func > 1.txt