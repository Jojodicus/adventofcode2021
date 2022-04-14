#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <stdbool.h>

typedef enum {
    INP, // inp a   -> *a = read()
    ADD, // add a b -> *a = *a + (*)b
    MUL, // mul a b -> *a = *a * (*)b
    DIV, // div a b -> *a = *a / (*)b
    MOD, // mod a b -> *a = *a % (*)b
    EQL, // eql a b -> *a = *a == (*)b ? 1 : 0
} operation;

typedef struct {
    operation op;               // operation type of current line
    uint_fast8_t varA;          // register number of a [w:0, x:1, y:2, z:3]
    bool hasReg;                // true if b is a register, false if b is a number
    union {
        uint_fast8_t regB;      // register number of b
        uint_fast8_t numB;      // value of number b
    } varB;
    struct instruction *next;   // next instruction in list
} instruction;

typedef struct {
    uint_fast64_t regs[4];      // register of machine
    instruction *program;       // first instruction
} program;

static program *alu = NULL;

int main() {
    // TODO
    exit(EXIT_SUCCESS);
}