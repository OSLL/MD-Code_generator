#include <stdio.h>

int main() {
    unsigned int a = 5;
    unsigned int b, c, d;
    d = 5 * (a - (a - a)) - a;
    c = a >> 18 >> d;
    d = a << (10 * 4 | 2);
    printf("%i\n", d);
    b = 6 * 10;
    d = c << 13 >> b;
    b = a + (d << (d << 3) << 14) * a;
    printf("%i\n", a);
    b = 19 & (8 * (3 & c) | 15) & c;
    a = a * (18 << (a & 14));
    c = b & (a + (12 * b) | 13) + 18;
    printf("%i\n", c);
    b = d | 18;
    printf("%i\n", d);
    return 0;
}