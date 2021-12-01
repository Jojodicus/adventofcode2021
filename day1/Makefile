EXE = sonarsweep
SUBS = 1 2
INPUT = input

.PHONY: all clean $(SUBS)

CC = gcc
CFLAGS = -std=c99 -Wall -Wextra -pedantic -o3

SRCS = sonarsweep.c
OBJS = $(SRCS:.c=.o)

all: $(EXE)

clean:
	rm -f $(OBJS) $(EXE)

$(SUBS): all
	./$(EXE) $@ < $(INPUT)

$(EXE): $(OBJS)
	$(CC) $(CFLAGS) $(OBJS) -o $(EXE)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@
