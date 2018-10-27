#include <stdio.h>

int main() {
    unsigned int a = 1;
    unsigned int b, c, d;
    a = a | 14;
    c = 7 & (a * a);
    a = c + 9 * 16 | 10;
    b = 4 - (a - 12 & (c * c));
    a = b + c << 3;
    b = a * b;
    d = 14 >> (b & (9 | 5) | a);
    return 0;
}