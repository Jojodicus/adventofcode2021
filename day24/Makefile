EXE = arithmeticlogicunit
SRC = arithmeticlogicunit

CC = gcc
CFLAGS = -std=c11 -Wall -Wextra -pedantic -o3

all: $(EXE)

clean:
	rm -f $(EXE) *.o

run: all
	./$(EXE)

$(EXE): $(SRC).o
	$(CC) $(CFLAGS) -o $@ $<

%.o: %.c
	$(CC) $(CFLAGS) -o $@ -c $<

.PHONY: all, clean, run