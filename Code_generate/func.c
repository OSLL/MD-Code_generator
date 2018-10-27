#include <stdio.h>

int main() {
    unsigned int a = 16;
    unsigned int b = 2;
    unsigned int c = 3;
    unsigned int d;
    d = a - 15;
    c = d & 13;
    a = 4 * c;
    a = b >> (17 + (b & a));
    c = b | 7 - c;
    return 0;
}