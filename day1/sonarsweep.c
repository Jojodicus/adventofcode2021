#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>

// print out usage and exit
void usage(char *name) {
    fprintf(stderr, "usage: %s 1|2\n", name);
    exit(EXIT_FAILURE);
}

// sum of array with length 3
int32_t sum3(int32_t *arr) {
    int32_t sum = 0;
    int i;
    for (i = 0; i < 3; ++i) {
        sum += arr[i];
    }
    return sum;
}

int main(int argc, char **argv) {
    // usage check
    if (2 != argc) {
        usage(*argv);
    }
    char puzzle = *argv[1];
    if ('1' != puzzle && '2' != puzzle) {
        usage(*argv);
    }

    // counter for both
    u_int32_t counter = 0;

    if ('1' == puzzle) {    // puzzle 1
        int32_t current;
        int32_t drag = -1;

        // loop over input
        while (!feof(stdin)) {
            scanf("%d", &current);

            // check values and update drag
            if (-1 != drag && current > drag) {
               ++counter;
            }
            drag = current;
        }
    } else {                // puzzle 2
        int32_t previous;
        int32_t drag[3];
        u_int8_t dragPos = 0;

        // first 3 elements
        int i;
        for (i = 0; i < 3 || feof(stdin); ++i) {
            scanf("%d", &drag[i]);
        }

        // rest of input
        while (!feof(stdin)) {
            previous = sum3(drag);

            scanf("%d", &drag[dragPos]);

            if (previous < sum3(drag)) {
                ++counter;
            }

            dragPos = (dragPos + 1) % 3;
        }
    }

    // output
    printf("increments: %d\n", counter);
    exit(EXIT_SUCCESS);
}