EXE = lanternfish
SRC = lanternfish

CC = g++
CFLAGS = -std=c++20 -Wall -Wextra -pedantic -o3

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