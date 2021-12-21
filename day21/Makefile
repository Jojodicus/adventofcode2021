EXE = diracDice
SRC = diracDice

CC = g++
CFLAGS = -std=c++17 -Wall -Wextra -pedantic -o3

all: $(EXE)

clean:
	rm -f $(EXE) *.o

run: all
	./$(EXE)

$(EXE): $(SRC).o
	$(CC) $(CFLAGS) -o $@ $<

%.o: %.cpp
	$(CC) $(CFLAGS) -o $@ -c $<

.PHONY: all, clean, run